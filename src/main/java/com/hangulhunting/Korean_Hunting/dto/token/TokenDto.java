package com.hangulhunting.Korean_Hunting.dto.token;

import lombok.Builder;
import lombok.Getter;


/***
 * 필요 없어 보이기 때문에 삭제 예정
 */
@Getter
@Builder
public class TokenDto {
    private String grantType;
    private String accessToken;
    private long tokenExpiresIn;
    private String refreshToken;
}
