package com.hangulhunting.Korean_Hunting.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResult {

	private int count;
	private List<String> fileName;
	
	@Builder
	public ApiResult(int count, List<String> fileName) {
		super();
		this.count = count;
		this.fileName = fileName;
	}
}
