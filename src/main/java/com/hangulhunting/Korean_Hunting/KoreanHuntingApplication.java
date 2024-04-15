package com.hangulhunting.Korean_Hunting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KoreanHuntingApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoreanHuntingApplication.class, args);
	}

}
