package team.jokimyoon.techmoa.global.security.oauth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.jokimyoon.techmoa.domain.user.repository.User;
import team.jokimyoon.techmoa.domain.user.repository.UserRepository;
import team.jokimyoon.techmoa.global.exception.BusinessException;
import team.jokimyoon.techmoa.global.security.oauth.Oauth2CustomUser;
import team.jokimyoon.techmoa.global.security.oauth.Oauth2Provider;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class Oauth2UserCustomService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2user = super.loadUser(oAuth2UserRequest);

		try {

			Oauth2Provider oauth2Provider = determineProvider(oAuth2UserRequest);

			Oauth2UserInfo oAuth2User;

			switch (oauth2Provider) {
				case GOOGLE -> oAuth2User = loadGoogleUser(oAuth2user, oauth2Provider);
				case GITHUB -> oAuth2User = loadGithubUser(oAuth2user, oauth2Provider);
				default -> throw new OAuth2AuthenticationException("Unsupported Oauth2 provider");
			}

			User user = updateUser(oAuth2User);

			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
			SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
			authorities.add(simpleGrantedAuthority);

			return Oauth2CustomUser.builder()
				.name(user.getNickname())
				.uuid(user.getUuid())
				.authorities(authorities)
				.build();

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

	private Oauth2UserInfo loadGithubUser(OAuth2User oAuth2User, Oauth2Provider oauth2Provider) throws
		OAuth2AuthenticationException {
		Map<String, Object> userAttribute = oAuth2User.getAttributes();
		String oauth2Id = userAttribute.get("id").toString();
		String profileImageUrl = userAttribute.get("avatar_url").toString();
		String nickname = userAttribute.get("name").toString();
		String email = userAttribute.get("email").toString();

		return Oauth2UserInfo.builder()
			.oauth2Provider(oauth2Provider)
			.oauth2Id(oauth2Id)
			.profileImgUrl(profileImageUrl)
			.nickname(nickname)
			.email(email)
			.build();

	}

	private Oauth2UserInfo loadGoogleUser(OAuth2User oAuth2User, Oauth2Provider oauth2Provider) throws
		OAuth2AuthenticationException {
		Map<String, Object> userAttribute = oAuth2User.getAttributes();
		String profileImageUrl = userAttribute.get("picture").toString();
		String nickname = userAttribute.get("name").toString();
		String oauth2Id = userAttribute.get("sub").toString();
		String email = userAttribute.get("email").toString();

		return Oauth2UserInfo.builder()
			.oauth2Provider(oauth2Provider)
			.oauth2Id(oauth2Id)
			.profileImgUrl(profileImageUrl)
			.nickname(nickname)
			.email(email)
			.build();
	}

	private User updateUser(Oauth2UserInfo oauth2UserInfo) {

		User user = userRepository.findBy(oauth2UserInfo.getOauth2Id(), oauth2UserInfo.getOauth2Provider())
			.orElse(User.builder()
				.oauthId(oauth2UserInfo.getOauth2Id())
				.oauthProvider(oauth2UserInfo.getOauth2Provider())
				.email(oauth2UserInfo.getEmail())
				.nickname(oauth2UserInfo.getNickname())
				.profileImgUrl(oauth2UserInfo.getProfileImgUrl())
				.build());

		user.changeEmail(oauth2UserInfo.getEmail());
		user.changeNickname(oauth2UserInfo.getNickname());
		user.changeProfileImgUrl(oauth2UserInfo.getProfileImgUrl());

		userRepository.saveAndFlush(user);

		return user;
	}
}
