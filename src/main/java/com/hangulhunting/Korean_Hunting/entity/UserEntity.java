package com.hangulhunting.Korean_Hunting.entity;

import java.util.ArrayList;
import java.util.List;

import com.hangulhunting.Korean_Hunting.entity.enumpackage.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(name = "user_entity_seq_generator", sequenceName = "user_entity_seq", initialValue = 1, allocationSize = 50)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserEntity {

	@Id
	@GeneratedValue(generator = "user_entity_seq_generator")
	private Long id;
	@Column(unique = true, nullable = false)
	private String userId;
	private String userPwd;
	@Column(nullable = false)
	private String email;
	private String company;
	private String joinRoute;
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "userEntity")
	private RefreshToken refreshToken;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "userEntity")
	private ApiTokenEntity apiTokenEntity;

	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProjectBuildHistory> projectBuildHistories = new ArrayList<>();

	@Builder
	public UserEntity(String userId, String userPwd, String email, String company, String joinRoute, UserRole role) {
		this.userId = userId;
		this.userPwd = userPwd;
		this.email = email;
		this.company = company;
		this.role = role;
		this.joinRoute = joinRoute;
	}
	
	public void addProjectBuildHistory(ProjectBuildHistory projectBuildHistory) {
		this.projectBuildHistories.add(projectBuildHistory);
	}

}
