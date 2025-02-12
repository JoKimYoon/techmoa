package team.jokimyoon.techmoa.global.security.token;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import team.jokimyoon.techmoa.global.exception.BusinessException;

@Slf4j
@Component
public class TokenProvider {

	private static Key key;
	private static final long DEFAULT_ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7L;
	private static final String TOKEN_TYPE = "Bearer";

	@Value("${spring.security.jwt.secret}")
	public void setSecret(String secret) {
		key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}

	public static Token createToken(TokenClaim tokenClaim) {

		long curTime = new Date().getTime();

		HashMap<String, Object> claims = new HashMap<>();
		claims.put("uuid", tokenClaim.getUuid());

		String accessToken = Jwts.builder()
			.setClaims(claims)
			.signWith(key, SignatureAlgorithm.HS256)
			.setIssuedAt(new Date())
			.setExpiration(new Date(curTime + DEFAULT_ACCESS_TOKEN_EXPIRE_TIME))
			.compact();

		return Token
			.builder()
			.accessToken(accessToken)
			.expiresIn(Instant.now().plusMillis(DEFAULT_ACCESS_TOKEN_EXPIRE_TIME))
			.issuedAt(Instant.now())
			.tokenType(TOKEN_TYPE)
			.build();
	}

	public static Jws<Claims> parseToken(String jwtToken) {
		try {

			JwtParser parser = Jwts
				.parserBuilder()
				.setSigningKey(key)
				.build();

			Jws<Claims> claimsJws = parser.parseClaimsJws(jwtToken);

			if (!claimsJws.getHeader().getAlgorithm().equals(SignatureAlgorithm.HS256.name())) {
				throw new InvalidAlgorithmException();
			}

			return claimsJws;

		} catch (UnsupportedJwtException
				 | InvalidAlgorithmException
				 | MalformedJwtException
				 | SignatureException
				 | IllegalArgumentException e) {
			throw new BusinessException("Invalid Token", HttpStatus.BAD_REQUEST, e);
		} catch (ExpiredJwtException e) {
			throw new BusinessException("Expired Token", HttpStatus.UNAUTHORIZED, e);
		} catch (Exception e) {
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	public static String extractAccessTokenHeader(HttpServletRequest request) {

		String bearerToken = request.getHeader("Authorization");

		if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
			throw new BusinessException(HttpStatus.UNAUTHORIZED);
		}
		return bearerToken.replaceAll("Bearer", "").replaceAll("", "");
	}

}
