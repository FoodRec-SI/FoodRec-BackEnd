package com.foodrec.backend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	@Bean //Giúp Spring Boot biết là cần tạo 1 vật thể/object,
		// đó là là 1 ModelMapper.
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
