//package com.hangulhunting.Korean_Hunting.etc;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler;
//import org.junit.jupiter.api.Test;
//
//public class FernflowerTest {
//
//	@Test
//	public void test() {
//        // 디코딩할 클래스 파일 경로
//        String classFilePath = "src/test/java/com/hangulhunting/Korean_Hunting/etc/AcountService.class";
//
//        // 디코딩된 소스 코드를 저장할 디렉토리 경로
//        String outputDirectory = "src/test/java/com/hangulhunting/Korean_Hunting/etc";
//
//        // Fernflower 옵션 설정
//        Map<String, Object> options = new HashMap<>();
//        // 필요한 옵션이 있으면 여기에 추가
//
//        // ConsoleDecompiler 인스턴스 생성
//        ConsoleDecompiler decompiler = new ConsoleDecompiler(new File(outputDirectory), options);
//
//        // 클래스 파일 추가
//        decompiler.addSpace(new File(classFilePath), true);
//
//        // 디코딩 수행
//        decompiler.decompileContext();
//        
//        // 완벽한 디코딩이 되지 못한다.
//	}
//}
