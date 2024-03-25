package com.hangulhunting.Korean_Hunting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "blacklist_seq_gen", sequenceName = "blacklist_seq", initialValue = 1, allocationSize = 50)
public class BlackList {
	
	@Id
	@GeneratedValue(generator = "blacklist_seq_gen")
	private Long Id;
	
	private String token;

	@Builder
	public BlackList(Long id, String token) {
		this.token = token;
	}
	
}
