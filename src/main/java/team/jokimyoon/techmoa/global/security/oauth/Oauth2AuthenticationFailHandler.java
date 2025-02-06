package team.jokimyoon.techmoa.global.security.oauth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

	@Value("${spring.security.oauth2.client.failure_redirect_url}")
	private String failureRedirectUrl;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException {

		String targetUrl = UriComponentsBuilder
			.fromUriString(failureRedirectUrl)
			.queryParam("error", exception.getMessage())
			.build()
			.toUriString();

		super.getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}
