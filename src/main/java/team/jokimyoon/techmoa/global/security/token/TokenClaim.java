package team.jokimyoon.techmoa.global.security.token;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenClaim {
	private String uuid;
	private Collection<? extends GrantedAuthority> authorities;
}

