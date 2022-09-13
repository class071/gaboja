package com.daily.gaboja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GabojaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GabojaApplication.class, args);
	}

}
