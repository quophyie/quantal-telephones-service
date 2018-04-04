package com.quantal.telephones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.quantal", scanBasePackageClasses = {com.quantal.telephones.config.shared.SharedConfig.class})
public class QuantalTelephonesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuantalTelephonesServiceApplication.class, args);
	}
}
