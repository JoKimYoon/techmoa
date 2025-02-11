package team.jokimyoon.techmoa.domain.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.jokimyoon.techmoa.global.security.oauth.Oauth2Provider;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private String uuid;

	private Oauth2Provider oauthProvider;

	private String oauthId;

	private String nickname;

	private String email;

	private String profileImage;
}
