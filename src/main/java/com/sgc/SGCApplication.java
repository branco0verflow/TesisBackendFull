package com.sgc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class SGCApplication {

	public static void main(String[] args) {
		SpringApplication.run(SGCApplication.class, args);
	}

}
