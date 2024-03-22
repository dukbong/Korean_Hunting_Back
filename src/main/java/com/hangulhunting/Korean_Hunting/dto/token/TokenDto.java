package com.hangulhunting.Korean_Hunting.dto.token;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class TokenDto {
    private String grantType;
    private String accessToken;
    private long tokenExpiresIn;
    private String refreshToken;
}
