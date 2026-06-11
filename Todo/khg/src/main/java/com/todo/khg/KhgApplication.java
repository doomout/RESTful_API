package com.todo.khg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KhgApplication {

	public static void main(String[] args) {
		SpringApplication.run(KhgApplication.class, args);
	}

}
