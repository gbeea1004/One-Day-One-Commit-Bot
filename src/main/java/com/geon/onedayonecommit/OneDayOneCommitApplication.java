package com.geon.onedayonecommit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OneDayOneCommitApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneDayOneCommitApplication.class, args);
	}
}
