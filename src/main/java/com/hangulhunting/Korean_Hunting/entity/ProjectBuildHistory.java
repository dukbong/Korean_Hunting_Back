package com.hangulhunting.Korean_Hunting.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name="projectBuild_history_gen", sequenceName = "projectBuild_seq", initialValue = 1, allocationSize = 50)
public class ProjectBuildHistory {

	@Id
	@GeneratedValue(generator = "projectBuild_history_gen")
	private Long id;
	
	private String projectName;
	
	private LocalDateTime buildTime;
	
	private boolean status;
	
	@ManyToOne
	@JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;
	
	@Builder
	public ProjectBuildHistory(String projectName, LocalDateTime buildTime, boolean status, UserEntity userEntity) {
		this.projectName = projectName;
		this.buildTime = buildTime;
		this.status = status;
		this.userEntity = userEntity;
		if(userEntity != null) {
			userEntity.addProjectBuildHistory(this);
		}
	}
}
