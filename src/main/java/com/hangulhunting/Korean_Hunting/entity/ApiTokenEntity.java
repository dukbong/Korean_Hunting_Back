package com.hangulhunting.Korean_Hunting.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "api_token_seq_gen", sequenceName = "api_token_seq", initialValue = 1, allocationSize = 50)
public class ApiTokenEntity {
	
	@Id
	@GeneratedValue(generator = "api_token_seq_gen", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_Id")
	private UserEntity userEntity;
	
	private String apiToken;
	
	private LocalDate issuanceTime;
	
	private String tokenExpiresIn;
	
	@Builder
	public ApiTokenEntity(Long id, UserEntity userEntity, String apiToken, LocalDate issuanceTime, String tokenExpiresIn) {
		super();
		this.id = id;
		this.userEntity = userEntity;
		this.apiToken = apiToken;
		this.issuanceTime = issuanceTime;
		this.tokenExpiresIn = tokenExpiresIn;
	}
}
