package com.cognixia.jump;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cognixia.jump.repository.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SpringBootHealtyEnrollmentManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHealtyEnrollmentManagementSystemApplication.class, args);
	}

}
