package com.naveen.mmi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MmiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmiApplication.class, args);
	}

}
