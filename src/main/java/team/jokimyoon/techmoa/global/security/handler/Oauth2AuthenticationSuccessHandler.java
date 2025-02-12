package team.jokimyoon.techmoa.global.security.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import team.jokimyoon.techmoa.global.exception.BusinessException;
import team.jokimyoon.techmoa.global.security.oauth.Oauth2CustomUser;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Value("${spring.security.oauth2.client.success_redirect_url}")
	private String successRedirectUrl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		if (response.isCommitted()) {
			return;
		}

		Oauth2CustomUser loginUser = loadUserFromAuthentication(authentication);

		OidcIdToken oidcIdToken = loginUser.getIdToken();

		String targetUrl = UriComponentsBuilder
			.fromUriString(successRedirectUrl)
			.queryParam("accessToken", oidcIdToken.getTokenValue())
			.queryParam("expiresIn", oidcIdToken.getExpiresAt())
			.toUriString();

		super.getRedirectStrategy().sendRedirect(request, response, targetUrl);

	}

	private Oauth2CustomUser loadUserFromAuthentication(Authentication authentication) {
		try {
			return (Oauth2CustomUser)authentication.getPrincipal();
		} catch (ClassCastException e) {
			throw new BusinessException(e);
		}
	}
}
