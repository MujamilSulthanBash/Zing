package com.i2i.zing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ZingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZingApplication.class, args);
	}

}
