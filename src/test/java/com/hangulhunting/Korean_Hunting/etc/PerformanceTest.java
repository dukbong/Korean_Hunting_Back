//package com.hangulhunting.Korean_Hunting.etc;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Random;
//import java.util.Set;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//public class PerformanceTest {
//
//    private static final int ITERATIONS = 1000;
//    private static final Map<String, Set<String>> largeData = generateLargeData();
//
//    public static void main(String[] args) {
//        // Gson 성능 테스트
//        long gsonStartTime = System.currentTimeMillis();
//        for (int i = 0; i < ITERATIONS; i++) {
//            byte[] gsonJsonBytes = writeSearchResultToByteArrayUsingGson(largeData);
//        }
//        long gsonEndTime = System.currentTimeMillis();
//        long gsonDuration = gsonEndTime - gsonStartTime;
//        System.out.println("Gson Performance Test - Average Time: " + (gsonDuration / (double) ITERATIONS) + " milliseconds");
//
//        // Jackson 성능 테스트
//        long jacksonStartTime = System.currentTimeMillis();
//        for (int i = 0; i < ITERATIONS; i++) {
//            byte[] jacksonJsonBytes = writeSearchResultToByteArrayUsingJackson(largeData);
//        }
//        long jacksonEndTime = System.currentTimeMillis();
//        long jacksonDuration = jacksonEndTime - jacksonStartTime;
//        System.out.println("Jackson Performance Test - Average Time: " + (jacksonDuration / (double) ITERATIONS) + " milliseconds");
//    }
//
//    private static Map<String, Set<String>> generateLargeData() {
//        Map<String, Set<String>> data = new HashMap<>();
//
//        Random random = new Random();
//        for (int i = 0; i < 100; i++) {
//            String entryName = "Entry" + i;
//            Set<String> words = new HashSet<>();
//            for (int j = 0; j < 100; j++) {
//                String word = generateRandomWord(random);
//                words.add(word);
//            }
//            data.put(entryName, words);
//        }
//
//        return data;
//    }
//
//    private static String generateRandomWord(Random random) {
//        StringBuilder word = new StringBuilder();
//        int length = random.nextInt(10) + 1; // 단어 길이를 1에서 10 사이의 랜덤 값으로 생성
//        for (int i = 0; i < length; i++) {
//            char ch = (char) ('a' + random.nextInt(26)); // 알파벳 소문자 랜덤 생성
//            word.append(ch);
//        }
//        return word.toString();
//    }
//
//    private static byte[] writeSearchResultToByteArrayUsingGson(Map<String, Set<String>> textContent) {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String json = gson.toJson(textContent);
//        return json.getBytes(StandardCharsets.UTF_8);
//    }
//
//    private static byte[] writeSearchResultToByteArrayUsingJackson(Map<String, Set<String>> textContent) {
//        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.writeValue(bos, textContent);
//            return bos.toByteArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
