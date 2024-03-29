package com.hangulhunting.Korean_Hunting.serviceImpl.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommentRemover {

	private final Map<String, Pattern> commentPattern = new HashMap<>();
	
	public CommentRemover() {
		commentPattern.put(".html", Pattern.compile("//.*|(?s)/\\*.*?\\*/|(?s)<!--.*?-->"));
		commentPattern.put(".jsp", Pattern.compile("(?s)<!--.*?-->|(?s)<%--.*?--%>|//.*|(?s)/\\*.*?\\*/"));
        commentPattern.put(".js", Pattern.compile("//.*|(?s)/\\*.*?\\*/"));
        commentPattern.put(".jsx", Pattern.compile("//.*|(?s)/\\*.*?\\*/"));
        commentPattern.put(".java", Pattern.compile("//.*|(?s)/\\*.*?\\*/"));
        commentPattern.put(".cs", Pattern.compile("//.*|(?s)/\\*.*?\\*/"));
        commentPattern.put(".py", Pattern.compile("#.*|(?s)\"\"\".*?\"\"\"|(?s)'''.*?'''"));
        commentPattern.put(".css", Pattern.compile("(?s)/\\*.*?\\*/"));
        
	}

	/***
	 * 파일 내의 주석을 제거하는 서비스
	 * 
	 * 현재 지원되는 언어: 1. HTML 2. JSP 3. Java 4. JavaScript 5. Python 6. C#
	 * 
	 * @param fileContent 주석을 제거할 파일의 내용
	 * @param fileType    파일의 유형을 나타내는 확장자 (예: ".html", ".java", ".py" 등)
	 * @return 주석이 제거된 파일 내용
	 */
//	public String removeComments(String fileContent, String fileType) {
//		switch (fileType) {
//		case ".html":
//			fileContent = fileContent.replaceAll("(?s)<!--.*?-->", ""); // html한줄 && 여러줄 처리
//			break;
//		case ".jsp":
//			fileContent = fileContent.replaceAll("(?s)<!--.*?-->", ""); // html한줄 && 여러줄 처리
//			fileContent = fileContent.replaceAll("(?s)<%--.*?--%>", ""); // jsp 한줄 && 여러줄 처리
//			break;
//		case ".js":
//		case ".jsx":
//		case ".java":
//		case ".cs":
//			fileContent = fileContent.replaceAll("//.*", ""); // 일반적인 한줄 주석 처리
//			fileContent = fileContent.replaceAll("(?s)/\\*.*?\\*/", ""); // /* */ 여러줄 주석 처리
//			break;
//		case ".py":
//			fileContent = fileContent.replaceAll("#.*", ""); // python 한줄 주석 처리
//			fileContent = fileContent.replaceAll("(?s)\"\"\".*?\"\"\"", ""); // python 여러줄 주석 처리
//			fileContent = fileContent.replaceAll("(?s)'''.*?'''", ""); // python 여러줄 주석 처리
//			break;
//		}
//		return fileContent;
//	}

    public String removeComments(ZipInputStream zipInputStream, String fileType) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = zipInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        Pattern pattern = commentPattern.get(fileType);
            byte[] fileBytes = outputStream.toByteArray();
            String fileContent = new String(fileBytes, "UTF-8");
            Matcher matcher = pattern.matcher(fileContent);
            fileContent = matcher.replaceAll("");
            return fileContent;
//            switch (fileType) {
//    		case ".html":
////    			fileContent = fileContent.replaceAll("//.*", ""); // 일반적인 한줄 주석 처리
////    			fileContent = fileContent.replaceAll("(?s)/\\*.*?\\*/", ""); // /* */ 여러줄 주석 처리
////    			fileContent = fileContent.replaceAll("(?s)<!--.*?-->", ""); // html한줄 && 여러줄 처리
//    			fileContent = fileContent.replaceAll("//.*|(?s)/\\*.*?\\*/|(?s)<!--.*?-->", "");
//
//    			break;
//    		case ".jsp":
////    			fileContent = fileContent.replaceAll("(?s)<!--.*?-->", ""); // html한줄 && 여러줄 처리
////    			fileContent = fileContent.replaceAll("(?s)<%--.*?--%>", ""); // jsp 한줄 && 여러줄 처리
////    			fileContent = fileContent.replaceAll("//.*", ""); // 일반적인 한줄 주석 처리
////    			fileContent = fileContent.replaceAll("(?s)/\\*.*?\\*/", ""); // /* */ 여러줄 주석 처리
//    			fileContent = fileContent.replaceAll("(?s)<!--.*?-->|(?s)<%--.*?--%>|//.*|(?s)/\\*.*?\\*/", "");
//
//    			break;
//    		case ".js":
//    		case ".jsx":
//    		case ".java":
//    		case ".cs":
////    			fileContent = fileContent.replaceAll("//.*", ""); // 일반적인 한줄 주석 처리
////    			fileContent = fileContent.replaceAll("(?s)/\\*.*?\\*/", ""); // /* */ 여러줄 주석 처리
//    			fileContent = fileContent.replaceAll("//.*|(?s)/\\*.*?\\*/", "");
//    			break;
//    		case ".py":
////    			fileContent = fileContent.replaceAll("#.*", ""); // python 한줄 주석 처리
////    			fileContent = fileContent.replaceAll("(?s)\"\"\".*?\"\"\"", ""); // python 여러줄 주석 처리
////    			fileContent = fileContent.replaceAll("(?s)'''.*?'''", ""); // python 여러줄 주석 처리
//    			fileContent = fileContent.replaceAll("#.*|(?s)\"\"\".*?\"\"\"|(?s)'''.*?'''", "");
//
//    			break;
//    		}
//            return fileContent;
    }




}
