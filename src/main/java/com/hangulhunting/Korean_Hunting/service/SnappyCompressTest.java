package com.hangulhunting.Korean_Hunting.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.xerial.snappy.Snappy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SnappyCompressTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
		new SnappyCompressTest().test();
	}

	private void test() throws UnsupportedEncodingException {
		String str = "I misread ReadMe and tried to use it as a zip file compression library, but it failed. It's my mistake.\r\n"
					 + "From next time, make it a habit to read Read Me carefully.\r\n"
					 + "근데 이거 압축률 장난아니게 낮구나.... 한글을 안되는거 같군요?";
		
		snappyTest(str);
	}

	public void snappyTest(String text) throws UnsupportedEncodingException {
		log.info("원본 : {}", text);
		log.info("원본 byte의 길이 : {}", text.getBytes("UTF-8").length);
		byte[] compressData = inputFileCompress(text);
		inputFileUnCompress(compressData);
	}

	// 압축해제
	private void inputFileUnCompress(byte[] data) {
		String unCompressText = null;
		try {
			byte[] unCompressData = Snappy.uncompress(data);
			unCompressText = new String(unCompressData, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("해제 : {}", unCompressText);
	}

	// 압축
	private byte[] inputFileCompress(String text) {
		byte[] textCompress = null;
		String compressText = null;
		try {
			textCompress = Snappy.compress(text.getBytes("UTF-8"));
			compressText = new String(textCompress, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("압축 : {}", compressText);
		log.info("압축 byte의 길이 : {}", textCompress.length);
		return textCompress;
	}
}
