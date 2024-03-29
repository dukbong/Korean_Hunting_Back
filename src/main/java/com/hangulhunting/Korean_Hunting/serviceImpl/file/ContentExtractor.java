package com.hangulhunting.Korean_Hunting.serviceImpl.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Component;

@Component
public class ContentExtractor {
	
	public String extractContent(ZipInputStream zipInputStream, byte[] buffer) throws IOException {
	    StringBuilder contentBuilder = new StringBuilder();
	    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
	        int len;
	        while ((len = zipInputStream.read(buffer)) > 0) {
	            baos.write(buffer, 0, len);
	        }
	        contentBuilder.append(new String(baos.toByteArray(), StandardCharsets.UTF_8));
	    }
	    return contentBuilder.toString();
	}
	
}
