package team.jokimyoon.techmoa.global.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String[] whiteUrlList = {

	}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .securityMatcher("/**")
            .httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.cors(c -> c.configurationSource(customCorsConfigurationSource())); //아래 함수에서 리턴받은 cors 설정값을 적용



		httpSecurity
                .sessionManagement(
					c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


		httpSecurity
			.authorizeHttpRequests(c -> c
					.requestMatchers("/**").permitAll()
					.anyRequest().authenticated());

		httpSecurity
                .oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer
						.authorizationEndpoint(c -> c
							.baseUri("/api/oauth2/authorization"))
						.redirectionEndpoint(c -> c
							.baseUri("/oauth2/authorization/*"))
						.userInfoEndpoint(c -> c
							.userService(customOAuth2UserService))
						.successHandler(oAuth2AuthenticationSuccessHandler)
						.failureHandler(oAuth2AuthenticationFailureHandler).permitAll())
            .exceptionHandling(c -> c
					.authenticationEntryPoint(new RestAuthenticationEntryPoint())
					.accessDeniedHandler(tokenAccessDeniedHandler));


		httpSecurity
                .addFilterAfter(customFilterFactory.createJwtAuthenticationFilter(),
    UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
}
