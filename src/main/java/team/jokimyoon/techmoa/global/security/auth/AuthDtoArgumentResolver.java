package team.jokimyoon.techmoa.global.security.auth;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import team.jokimyoon.techmoa.global.security.token.TokenProvider;

@Component
@RequiredArgsConstructor
public class AuthDtoArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(Auth.class) && parameter.hasParameterAnnotation(Auth.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();

		String accessToken = TokenProvider.extractAccessTokenHeader(request);

		Jws<Claims> claims = TokenProvider.parseToken(accessToken);

		String uuid = claims.getBody().get("uuid").toString();

		return AuthDto.builder()
			.uuid(uuid)
			.build();
	}
}
