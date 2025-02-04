package team.jokimyoon.techmoa.global.security.token;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Token {
    private String accessToken;
    private Long accessTokenExpiresIn;
}
