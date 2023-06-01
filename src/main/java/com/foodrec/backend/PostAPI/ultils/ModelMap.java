package com.foodrec.backend.PostAPI.ultils;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMap {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
