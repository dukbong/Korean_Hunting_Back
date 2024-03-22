package com.hangulhunting.Korean_Hunting.service;

import java.util.Set;

public interface ExtractionStrategy {
	Set<String> extract(String fileContent);
}
