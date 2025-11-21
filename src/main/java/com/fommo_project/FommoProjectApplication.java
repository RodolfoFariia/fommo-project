package com.fommo_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FommoProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FommoProjectApplication.class, args);
	}

}
