package team.jokimyoon.techmoa.global.security.token;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Token {
	private final String accessToken;
	private final Instant expiresIn;
	private final Instant issuedAt;
	private final String tokenType;
}
