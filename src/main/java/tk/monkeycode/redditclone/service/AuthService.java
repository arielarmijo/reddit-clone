package tk.monkeycode.redditclone.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tk.monkeycode.redditclone.exception.RedditException;
import tk.monkeycode.redditclone.model.NotificationEmail;
import tk.monkeycode.redditclone.model.User;
import tk.monkeycode.redditclone.model.VerificationToken;
import tk.monkeycode.redditclone.model.dto.AuthenticationResponse;
import tk.monkeycode.redditclone.model.dto.LoginRequest;
import tk.monkeycode.redditclone.model.dto.RefreshTokenRequest;
import tk.monkeycode.redditclone.model.dto.RegisterRequest;
import tk.monkeycode.redditclone.repository.UserRepository;
import tk.monkeycode.redditclone.repository.VerificationTokenRepository;
import tk.monkeycode.redditclone.util.Constants;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final AuthenticationManager authenticationManager;
	private final EmailService emailService;
	private final JwtService jwtService;
	private final RefreshTokenService refreshTokenService;
	
	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setEmail(registerRequest.getEmail());
		user.setCreated(Instant.now());
		user.setEnabled(false);
		userRepository.save(user);
		String token = generateVerificationToken(user);
		NotificationEmail email = new NotificationEmail();
		email.setSubject("Activate your account");
		email.setRecipient(user.getEmail());
		email.setBody(String.format("Thank you for signing up to Reddit Clone. Please click on the link below to activate your account: %s/%s", Constants.ACTIVATION_LINK, token));
		emailService.sendMail(email);
	}
	
    public void verifyAccount(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
        									  	 .orElseThrow(() -> new RedditException("Invalid Token"));
        fetchUserAndEnable(verificationToken);
    }
    
    public AuthenticationResponse login(LoginRequest loginRequest) {
    	var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        var principal = (org.springframework.security.core.userdetails.User) authenticate.getPrincipal();
        String authenticationToken = jwtService.generateToken(principal.getUsername());
        return AuthenticationResponse.builder()
        		.authenticationToken(authenticationToken)
        		.refreshToken(refreshTokenService.generateRefreshToken().getToken())
        		.build();
    }
    
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        var principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User name %s not found", principal.getUsername())));
    }
    
    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username)
        						 	.orElseThrow(() -> new RedditException(String.format("User id %s not found", username)));
        user.setEnabled(true);
        userRepository.save(user);
    }
    
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
    	refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
    	return AuthenticationResponse.builder()
    			.authenticationToken(jwtService.generateToken(getCurrentUser().getUsername()))
    			.refreshToken(refreshTokenRequest.getRefreshToken())
    			.build();
    }
	
	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationTokenRepository.save(verificationToken);
		return token;
	}
	
}
