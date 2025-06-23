package com.emergence.locator.app.emergence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EmergenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmergenceApplication.class, args);
	}

}
