package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController

public class DigitalGameStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalGameStoreApplication.class, args);
	}


	@GetMapping("/hello")
	public String hello() {
		return "Hello World!";
	}
}
