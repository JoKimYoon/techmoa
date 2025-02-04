package team.jokimyoon.techmoa.global.security.oauth;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Oauth2CustomUser implements OAuth2User, OidcUser {

	private final Oauth2Provider oauth2Provider;
	private final String oauth2Id;
	private final String profileImgUrl;
	private final String nickname;
	private final String email;

	@Override
	public Map<String, Object> getClaims() {
		return Map.of();
	}

	@Override
	public OidcUserInfo getUserInfo() {
		return null;
	}

	@Override
	public OidcIdToken getIdToken() {
		return null;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return Map.of();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getName() {
		return "";
	}
}
