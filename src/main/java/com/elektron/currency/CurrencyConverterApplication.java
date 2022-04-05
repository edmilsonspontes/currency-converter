package com.elektron.currency;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.elektron.currency.entity.UserEntity;
import com.elektron.currency.repository.UserRepository;

@SpringBootApplication
@EnableWebMvc
public class CurrencyConverterApplication {
	
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConverterApplication.class, args);
	}
	
	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			userRepository.save(new UserEntity(null, "Edmilson", "edmilsonspontes@gmail.com", null));
			userRepository.save(new UserEntity(null, "Grazielle", "grazi@gmail.com", null));
		};
	}
}
