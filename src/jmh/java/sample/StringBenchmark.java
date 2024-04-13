package sample;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@State(Scope.Thread)
public class StringBenchmark {

    private static final int MAP_SIZE = 1000; // Adjust size as needed
    private static final Gson gson = new Gson();
    private Map<String, Set<String>> textContent;
    
    @Setup
    public void setup() {
    	textContent = generateMap();
    }

    private Map<String, Set<String>> generateMap() {
    	Random random = new Random();
    	Map<String, Set<String>> map = new HashMap<>();
    	
    	for (int i = 0; i < MAP_SIZE; i++) {
    		String key = "Key" + i;
    		Set<String> values = new HashSet<>();
    		int numValues = random.nextInt(10); // 임의의 값 생성
    		
    		for (int j = 0; j < numValues; j++) {
    			values.add("Value" + random.nextInt(100));
    		}
    		
    		map.put(key, values);
    	}
    	
    	return map;
    }
    
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public byte[] jacksonSerialization() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(bos, textContent);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to write JSON to byte array", e);
        }
    }
    
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public byte[] gsonSerialization() {
        return gson.toJson(textContent).getBytes();
    }

}

