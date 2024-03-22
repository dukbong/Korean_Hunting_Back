package com.hangulhunting.Korean_Hunting.serviceImpl.file;

import org.springframework.stereotype.Component;

@Component
public class CommentRemover {
	/***
	 * 파일 내의 주석을 제거하는 서비스
	 * 
	 * 현재 지원되는 언어:
	 * 1. HTML
	 * 2. JSP
	 * 3. Java
	 * 4. JavaScript
	 * 5. Python
	 * 6. C#
	 * 
	 * @param fileContent 주석을 제거할 파일의 내용
	 * @param fileType 파일의 유형을 나타내는 확장자 (예: ".html", ".java", ".py" 등)
	 * @return 주석이 제거된 파일 내용
	 */
	public String removeComments(String fileContent, String fileType) {
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
