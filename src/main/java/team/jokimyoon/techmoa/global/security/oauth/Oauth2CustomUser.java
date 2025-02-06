package team.jokimyoon.techmoa.global.security.oauth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Builder;
import lombok.Setter;
import team.jokimyoon.techmoa.global.security.token.Token;
import team.jokimyoon.techmoa.global.security.token.TokenClaim;
import team.jokimyoon.techmoa.global.security.token.TokenProvider;

public class Oauth2CustomUser implements OAuth2User, OidcUser {

	private final String name;
	private final String uuid;
	private final HashMap<String, Object> attributes;
	private final OidcUserInfo userInfo;
	private final Collection<? extends GrantedAuthority> authorities;

	@Setter
	private OidcIdToken idToken;

	@Builder
	public Oauth2CustomUser(String name, String uuid, Collection<? extends GrantedAuthority> authorities) {

		this.name = name;
		this.uuid = uuid;
		this.attributes = new HashMap<>();
		this.attributes.put("uuid", uuid);
		this.attributes.put("authorities", authorities);
		this.authorities = new ArrayList<>(authorities);
		this.userInfo = new OidcUserInfo(attributes);
		issueToken();
	}

	private void issueToken() {
		TokenClaim tokenClaim = TokenClaim.builder()
			.uuid(uuid)
			.authorities(authorities)
			.build();

		Token token = TokenProvider.createToken(tokenClaim);

		this.idToken = new OidcIdToken(
			token.getAccessToken(),
			token.getIssuedAt(),
			token.getExpiresIn(),
			attributes);

	}

	@Override
	public Map<String, Object> getClaims() {
		return attributes;
	}

	@Override
	public OidcUserInfo getUserInfo() {
		return userInfo;
	}

	@Override
	public OidcIdToken getIdToken() {
		return this.idToken;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getName() {
		return this.name;
	}
}
