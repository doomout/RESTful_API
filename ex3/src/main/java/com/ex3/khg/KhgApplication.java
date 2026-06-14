package com.ex3.khg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
// JPA Auditing 기능을 켜서 @CreatedDate, @LastModifiedDate 값을 자동으로 채웁니다.
@EnableJpaAuditing
public class KhgApplication {

	public static void main(String[] args) {
		// Spring Boot 애플리케이션을 실행하는 시작점입니다.
		SpringApplication.run(KhgApplication.class, args);
	}

}
