package com.hangulhunting.Korean_Hunting.dto;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZipFile {
	private byte[] content;
	private ArrayList<String> directory;
}