package xyz.hoppr.hoppr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HopprApplication {

	public static void main(String[] args) {
		SpringApplication.run(HopprApplication.class, args);
	}

}
