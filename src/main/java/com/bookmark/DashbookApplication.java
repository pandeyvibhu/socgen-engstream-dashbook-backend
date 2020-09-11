package com.bookmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DashbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashbookApplication.class, args);
	}
}
