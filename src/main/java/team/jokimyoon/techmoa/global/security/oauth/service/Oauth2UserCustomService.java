package team.jokimyoon.techmoa.global.security.oauth.service;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.jokimyoon.techmoa.domain.repository.User;
import team.jokimyoon.techmoa.global.exception.BusinessException;
import team.jokimyoon.techmoa.global.security.oauth.Oauth2CustomUser;
import team.jokimyoon.techmoa.global.security.oauth.Oauth2Provider;

@Slf4j
@Service
@RequiredArgsConstructor
public class Oauth2UserCustomService extends DefaultOAuth2UserService {

	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2user = super.loadUser(oAuth2UserRequest);

		oAuth2user.getAttributes()

		try {

			Oauth2Provider oauth2Provider = determineProvider(oAuth2UserRequest);
			OAuth2User Oauth2User = null;
			switch (oauth2Provider) {
				case GOOGLE -> {
					OAuth2User loadGoogleUser(oAuth2user, oauth2Provider);
				}
				case GITHUB -> {
					OAuth2User loadGithubUser(oAuth2user, oauth2Provider);
				}
				default -> throw new OAuth2AuthenticationException("Unsupported Oauth2 provider");
			}

		} catch (Exception ex) {
			log.info("[Oauth2UserCustomService] function : loadUser | error : {}", ex.getMessage());
			throw new BusinessException(ex.getMessage(), ex.getCause());
		}
	}

	private Oauth2Provider determineProvider(OAuth2UserRequest userRequest) {
		try {
			String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
			return Oauth2Provider.valueOf(provider.toUpperCase());
		} catch (Exception ex) {
			log.warn("[Oauth2UserCustomService] function : determineProvider | error : {}", ex.getMessage());
			throw new BusinessException(ex.getMessage(), ex.getCause());
		}
	}

	private Oauth2CustomUser loadGithubUser(OAuth2User oAuth2User, Oauth2Provider oauth2Provider) throws OAuth2AuthenticationException {
		Map<String, Object> userAttribute = oAuth2User.getAttributes();
		String oauth2Id = userAttribute.get("id").toString();
		String profileImageUrl = userAttribute.get("avatar_url").toString();
		String nickname = userAttribute.get("name").toString();
		String email = userAttribute.get("email").toString();

		return Oauth2CustomUser.builder()
			.oauth2Provider(oauth2Provider)
			.oauth2Id(oauth2Id)
			.profileImgUrl(profileImageUrl)
			.nickname(nickname)
			.email(email)
			.build();

	}

	private Oauth2CustomUser loadGoogleUser(OAuth2User oAuth2User, Oauth2Provider oauth2Provider) throws OAuth2AuthenticationException {
		Map<String, Object> userAttribute = oAuth2User.getAttributes();
		String profileImageUrl = userAttribute.get("picture").toString();
		String nickname = userAttribute.get("name").toString();
		String oauth2Id = userAttribute.get("sub").toString();
		String email = userAttribute.get("email").toString();

		return Oauth2CustomUser.builder()
			.oauth2Provider(oauth2Provider)
			.oauth2Id(oauth2Id)
			.profileImgUrl(profileImageUrl)
			.nickname(nickname)
			.email(email)
			.build();
	}

	private User updateUser(Oauth2CustomUser oauth2CustomUser) {




		userRepository.save(newUser);

		log.info("[OAuth2UserServiceCustom] function : registerUser | message : 회원 가입 완료");

		return newUser;
	}
}
