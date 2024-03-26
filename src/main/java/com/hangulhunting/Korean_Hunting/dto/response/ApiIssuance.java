package com.hangulhunting.Korean_Hunting.dto.response;

import java.time.LocalDate;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiIssuance {

	private String apiToken;
	private LocalDate issuanceTime;
	private String tokenExpiresIn;
	
}
