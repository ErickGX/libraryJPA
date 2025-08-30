package com.erickgx.libraryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //config necessaria para usar @EntityListeners(AuditingEntityListener.class) na model
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

}
