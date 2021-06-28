package tk.monkeycode.redditclone.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tk.monkeycode.redditclone.exception.RefreshTokenNotFoundException;
import tk.monkeycode.redditclone.model.RefreshToken;
import tk.monkeycode.redditclone.repository.RefreshTokenRepository;

@Service
@AllArgsConstructor
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;
	
	@Transactional(readOnly = true)
	public RefreshToken generateRefreshToken() {
		var refreshToken = new RefreshToken();
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setCreatedAt(Instant.now());
		return refreshTokenRepository.save(refreshToken);
	}
	
	@Transactional(readOnly = true)
	public RefreshToken findRefreshToken(String token) {
		return refreshTokenRepository.findByToken(token).orElseThrow(RefreshTokenNotFoundException::new);
	}
	
	@Transactional
	public void deleteRefreshToken(String token) {
		RefreshToken refreshToken = findRefreshToken(token);
		refreshTokenRepository.delete(refreshToken);
	}
}
