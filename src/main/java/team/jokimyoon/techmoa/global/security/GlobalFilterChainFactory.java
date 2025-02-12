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

@Component
@RequiredArgsConstructor
public class GlobalFilterChainFactory {

	private final SecurityAccessDeniedHandler securityAccessDeniedHandler;
	private final SecurityExceptionHandler securityExceptionHandler;

	public SecurityFilterChain createFilterChain(
		HttpSecurity httpSecurity,
		UrlBasedCorsConfigurationSource corsConfig) throws Exception {

		httpSecurity
			.securityMatcher("/**")
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.cors(c -> c.configurationSource(corsConfig));

		httpSecurity
			.sessionManagement(
				c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		httpSecurity
			.authorizeHttpRequests(c -> c
				.requestMatchers("/**").permitAll()
				.anyRequest().authenticated());

		httpSecurity
			.exceptionHandling(c -> c
				.accessDeniedHandler(securityAccessDeniedHandler)
				.authenticationEntryPoint(securityExceptionHandler));

		return httpSecurity.build();
	}
}
