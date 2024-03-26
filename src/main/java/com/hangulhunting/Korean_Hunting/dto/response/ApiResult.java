package com.hangulhunting.Korean_Hunting.dto.response;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResult {

	private int count;
	private ArrayList<String> fileName;
	
	@Builder
	public ApiResult(int count, ArrayList<String> fileName) {
		super();
		this.count = count;
		this.fileName = fileName;
	}
}
