package com.hangulhunting.Korean_Hunting.service;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class FileStructureMethod {
	
	/***
	 * 문서에서 한글만 찾는 서비스
	 * @param fileContent
	 * @param fileType
	 * @return
	 */
	public Set<String> extractKoreanWords(String fileContent, String fileType) {
		Set<String> words = new TreeSet<>();
		String removeCommentsContent = removeComments(fileContent, fileType);
		String[] arr = removeCommentsContent.split("\n");
		for (int i = 0; i < arr.length; i++) {
			Pattern pattern = Pattern.compile("[가-힣\\s]+");
			Matcher matcher = pattern.matcher(arr[i]);
			while (matcher.find()) {
				String koreanWord = matcher.group().trim();
				if (!koreanWord.isEmpty()) {
					words.add(koreanWord);
				}
			}
		}
		return words;
	}

	/***
	 * 문서에서 태그안에 있는 글자를 찾는 서비스
	 * @param fileContent
	 * @param fileType
	 * @return
	 */
	public Set<String> extractTagText(String fileContent, String fileType) {
		Set<String> words = new TreeSet<>();
		String removeCommentsContent = removeComments(fileContent, fileType);
		String[] arr = removeCommentsContent.split("\n");
		for (int i = 0; i < arr.length; i++) {
			Pattern pattern = Pattern.compile("(?s)>(.*?)<");
			Matcher matcher = pattern.matcher(arr[i]);
			while (matcher.find()) {
				String tagText = matcher.group().trim();
				if (!tagText.isEmpty()) {
					words.add(tagText);
				}
			}
		}
		return words;
	}
	
	/***
	 * 주석을 제거하는 서비스
	 * 현재 가능한 언어
	 * 1. HTML
	 * 2. JSP
	 * 3. Java
	 * 4. JavaScript
	 * 5. Python
	 * 6. C#
	 * @param fileContent
	 * @param fileType
	 * @return
	 */
	private String removeComments(String fileContent, String fileType) {
		switch (fileType) {
		case ".html":
			fileContent = fileContent.replaceAll("(?s)<!--.*?-->", ""); // html한줄 && 여러줄 처리
			break;
		case ".jsp":
			fileContent = fileContent.replaceAll("(?s)<!--.*?-->", ""); // html한줄 && 여러줄 처리
			fileContent = fileContent.replaceAll("(?s)<%--.*?--%>", ""); // jsp 한줄 && 여러줄 처리
			break;
		case ".js":
		case ".jsx":
		case ".java":
		case ".cs":
			fileContent = fileContent.replaceAll("//.*", ""); // 일반적인 한줄 주석 처리
			fileContent = fileContent.replaceAll("(?s)/\\*.*?\\*/", ""); // /* */ 여러줄 주석 처리
			break;
		case ".py":
			fileContent = fileContent.replaceAll("#.*", ""); // python 한줄 주석 처리
			fileContent = fileContent.replaceAll("(?s)\"\"\".*?\"\"\"", ""); // python 여러줄 주석 처리
			fileContent = fileContent.replaceAll("(?s)'''.*?'''", ""); // python 여러줄 주석 처리
			break;
		}
		return fileContent;
	}
	
}
