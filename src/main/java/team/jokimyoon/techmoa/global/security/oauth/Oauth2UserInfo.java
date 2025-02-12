package team.jokimyoon.techmoa.global.security.oauth;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Oauth2UserInfo {
	private final Oauth2Provider oauthProvider;
	private final String oauthId;
	private final String profileImage;
	private final String nickname;
	private final String email;
}
