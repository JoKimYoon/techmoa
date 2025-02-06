package team.jokimyoon.techmoa.global.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import team.jokimyoon.techmoa.global.exception.BusinessException;
import team.jokimyoon.techmoa.global.security.oauth.Oauth2CustomUser;

@Slf4j
public class TokenFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		ServletException,
		IOException {

		try {
			String bearerToken = request.getHeader("Authorization");

			if (!StringUtils.isNotBlank(bearerToken) || !bearerToken.startsWith("Bearer ")) {
				throw new BusinessException("Not Fount Bearer Token", HttpStatus.BAD_REQUEST);
			}

			String accessToken = bearerToken.replace("Bearer", "").replace(" ", "");

			Authentication authentication = createAuthenticationFromToken(accessToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			log.info("[JwtAuthenticationFilter] function : doFilterInternal | message : 토큰 인증 완료");

		} catch (MalformedJwtException | ExpiredJwtException | NoSuchElementException e) {
			response.sendError(401, e.getMessage());
		} catch (Exception e) {
			response.sendError(500, e.getMessage());
		} finally {
			chain.doFilter(request, response);
		}
	}

	private UsernamePasswordAuthenticationToken createAuthenticationFromToken(String token) {

		//Todo 인증객체 제대로 생성 후 리턴

		Oauth2CustomUser oauth2CustomUser = null;
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

		return new UsernamePasswordAuthenticationToken(oauth2CustomUser, token, authorities);
	}

}
