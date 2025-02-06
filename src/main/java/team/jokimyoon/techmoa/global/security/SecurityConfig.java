package team.jokimyoon.techmoa.global.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final Oauth2ClientFilterChainFactory oauth2ClientFilterChainFactory;
	private final GlobalFilterChainFactory globalFilterChainFactory;

	@Value("${security.mode.debug}")
	private boolean securityDebugMode;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.debug(securityDebugMode);
	}

	@Bean
	@Order(1)
	public SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return oauth2ClientFilterChainFactory.createFilterChain(httpSecurity, customCorsConfigurationSource());
	}

	@Bean
	@Order(2)
	public SecurityFilterChain globalSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return globalFilterChainFactory.createFilterChain(httpSecurity, customCorsConfigurationSource());
	}

	@Bean
	public UrlBasedCorsConfigurationSource customCorsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.addAllowedOriginPattern("http://localhst:4000");
		corsConfiguration.addAllowedOriginPattern("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}
}
