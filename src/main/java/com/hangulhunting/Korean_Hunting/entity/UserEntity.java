package com.hangulhunting.Korean_Hunting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(name = "user_entity_seq_generator", sequenceName = "user_entity_seq")
@NoArgsConstructor
@Getter
public class UserEntity {
	
	@Id
	@GeneratedValue(generator = "user_entity_seq_generator")
	private Long id;
	@Column(unique = true)
	private String userId;
	private String userPwd;
	private String email;
	private String company;
	
	@Builder
	public UserEntity(Long id, String userId, String userPwd, String email, String company) {
		super();
		this.id = id;
		this.userId = userId;
		this.userPwd = userPwd;
		this.email = email;
		this.company = company;
	}
	
	
	
}
