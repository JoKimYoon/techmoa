package team.jokimyoon.techmoa.global.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import team.jokimyoon.techmoa.global.security.handler.SecurityAccessDeniedHandler;
import team.jokimyoon.techmoa.global.security.handler.SecurityExceptionHandler;
import team.jokimyoon.techmoa.global.security.oauth.Oauth2UserCustomService;

@Component
@RequiredArgsConstructor
public class Oauth2ClientFilterChainFactory {

	private final Oauth2UserCustomService oauth2UserCustomService;
	private final Oauth2UserCustomService.Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
	private final Oauth2UserCustomService.Oauth2AuthenticationFailHandler oauth2AuthenticationFailHandler;

	private final SecurityAccessDeniedHandler securityAccessDeniedHandler;
	private final SecurityExceptionHandler securityExceptionHandler;

	public SecurityFilterChain createFilterChain(
		HttpSecurity httpSecurity,
		UrlBasedCorsConfigurationSource corsConfig) throws Exception {
		httpSecurity
			.securityMatcher("/api/login/oauth2/**", "/login/oauth2/code/**")
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.cors(c -> c.configurationSource(corsConfig));

		httpSecurity
			.sessionManagement(
				c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		httpSecurity
			.authorizeHttpRequests(c -> c.anyRequest().permitAll());

		httpSecurity
			.oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer
				.authorizationEndpoint(c -> c
					.baseUri("/api/login/oauth2"))
				.userInfoEndpoint(c -> c
					.userService(oauth2UserCustomService))
				.successHandler(oauth2AuthenticationSuccessHandler)
				.failureHandler(oauth2AuthenticationFailHandler))
			.exceptionHandling(c -> c
				.accessDeniedHandler(securityAccessDeniedHandler)
				.authenticationEntryPoint(securityExceptionHandler));

		return httpSecurity.build();
	}
}
