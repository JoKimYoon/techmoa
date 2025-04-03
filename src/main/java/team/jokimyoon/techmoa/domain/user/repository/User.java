package team.jokimyoon.techmoa.domain.user.repository;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jokimyoon.techmoa.global.model.BaseEntity;
import team.jokimyoon.techmoa.global.security.oauth.Oauth2Provider;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

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
	private String profileImage;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<UserFavorite> favorites;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_webhook_id")
	private UserWebHook webHook;

	@Builder
	public User(String oauthId, Oauth2Provider oauthProvider, String nickname, String email, String profileImage) {
		this.uuid = UUID.randomUUID().toString();
		this.oauthId = oauthId;
		this.oauthProvider = oauthProvider;
		this.nickname = nickname;
		this.email = email;
		this.profileImage = profileImage;
	}

	public void changeProfileImgUrl(String profileImage) {
		this.profileImage = profileImage;
	}

	public void changeNickname(String nickname) {
		//Todo 길이 제한
		this.nickname = nickname;
	}

	public void changeEmail(String email) {
		//Todo 길이 제한 , email validation
		this.email = email;
	}

	public void addDiscordWebhook(String discordWebhookUrl) {
		//Todo discord url validation

		if (this.webHook == null) {
			this.webHook = UserWebHook.builder()
				.discord(discordWebhookUrl)
				.build();
			;
		} else {
			this.webHook.setDiscord(discordWebhookUrl);
		}
	}

	public void addSlackWebhook(String slackWebhookUrl) {

		//Todo slack url validation

		if (this.webHook == null) {
			this.webHook = UserWebHook.builder()
				.slack(slackWebhookUrl)
				.build();
		} else {
			this.webHook.setSlack(slackWebhookUrl);
		}
	}
}
