package tk.monkeycode.redditclone.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tk.monkeycode.redditclone.model.NotificationEmail;
import tk.monkeycode.redditclone.model.User;
import tk.monkeycode.redditclone.model.VerificationToken;
import tk.monkeycode.redditclone.model.dto.RegisterRequest;
import tk.monkeycode.redditclone.repository.UserRepository;
import tk.monkeycode.redditclone.repository.VerificationTokenRepository;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final EmailService emailService;
	
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
		email.setBody(String.format("Thank you for signing up to Reddit Clone. Please click on the link below to activate your account: http://localhost:8080/api/auth/accountVerification/%s", token));
		emailService.sendMail(email);
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
