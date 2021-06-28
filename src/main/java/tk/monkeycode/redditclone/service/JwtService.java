package tk.monkeycode.redditclone.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import tk.monkeycode.redditclone.exception.RedditException;

@Service
public class JwtService {
	
	private KeyStore keyStore;
	
	@Value("${jwt.expirationtime}")
	private Long jwtExpirationInMillis;
	
	@PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/redditclone.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new RedditException("Exception occurred while loading keystore", e);
        }

    }

	public String generateToken(String username) {
		Instant now = Instant.now();
		return Jwts.builder()
				   .setSubject(username)
				   .setIssuedAt(Date.from(now))
				   .setExpiration(Date.from(now.plusMillis(jwtExpirationInMillis)))
				   .signWith(getPrivateKey(), SignatureAlgorithm.RS512)
				   .compact();
	}
	
	public boolean validateToken(String jwt) {
		try {
			Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt);
			return true;			
		} catch (JwtException e) {
			throw new RedditException("Error al validar token", e);
		}
	}
	
	public String getUsernameFromJwt(String jwt) {
		try {
			Jws<Claims>jws = Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt);
			return jws.getBody().getSubject();			
		} catch (JwtException e) {
			throw new RedditException("Error al obtener nombre de usuario - ", e);
		}
	}
	
	public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
	
	private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("redditclone", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new RedditException("Exception occured while retrieving public key from keystore - ", e);
        }
    }
	
	private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("redditclone").getPublicKey();
        } catch (KeyStoreException e) {
            throw new RedditException("Exception occured while retrieving public key from keystore - ", e);
        }
    }
	
}
