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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SequenceGenerator(name = "not_delete_folder_seq_gen", sequenceName = "not_delete_folder_seq", initialValue = 1, allocationSize = 50)
public class NotDeleteFolder {

	@Id
	@GeneratedValue(generator = "not_delete_folder_seq_gen")
	private Long id;
	
	private String folderPath;
	
	@Builder
	public NotDeleteFolder(Long id, String folderPath) {
		this.id = id;
		this.folderPath = folderPath;
	}
}
