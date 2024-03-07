package com.hangulhunting.Korean_Hunting.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {
    private String grantType;
    private String accessToken;
    private long tokenExpiresIn;
}
