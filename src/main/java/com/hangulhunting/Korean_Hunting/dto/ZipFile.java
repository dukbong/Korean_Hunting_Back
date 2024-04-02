package com.hangulhunting.Korean_Hunting.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZipFile {
	private byte[] content;
	private List<String> directory;
}