package team.jokimyoon.techmoa.global.security.oauth.service;

import lombok.Builder;
import lombok.Getter;
import team.jokimyoon.techmoa.global.security.oauth.Oauth2Provider;

@Builder
@Getter
public class Oauth2UserInfo {
	private final Oauth2Provider oauth2Provider;
	private final String oauth2Id;
	private final String profileImage;
	private final String nickname;
	private final String email;
}
