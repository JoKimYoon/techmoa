package team.jokimyoon.techmoa.domain.user.repository;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jokimyoon.techmoa.global.security.oauth.Oauth2Provider;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String uuid;

	@Column
	@Enumerated(value = EnumType.STRING)
	private Oauth2Provider oauthProvider;

	@Column
	private String oauthId;

	@Column
	private String nickname;

	@Column
	private String email;

	@Column
	private String profileImgUrl;

	@Builder
	public User(String oauthId, Oauth2Provider oauthProvider, String nickname, String email, String profileImgUrl) {
		this.uuid = UUID.randomUUID().toString();
		this.oauthId = oauthId;
		this.oauthProvider = oauthProvider;
		this.nickname = nickname;
		this.email = email;
		this.profileImgUrl = profileImgUrl;
	}

	public void changeProfileImgUrl(String profileImgUrl) {
		this.profileImgUrl = profileImgUrl;
	}

	public void changeNickname(String nickname) {
		//Todo 길이 제한
		this.nickname = nickname;
	}

	public void changeEmail(String email) {
		//Todo 길이 제한 , email validation
		this.email = email;
	}
}
