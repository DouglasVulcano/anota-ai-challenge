package br.com.vulcanodev.anota_ai_challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class AnotaAiChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnotaAiChallengeApplication.class, args);
	}

}
