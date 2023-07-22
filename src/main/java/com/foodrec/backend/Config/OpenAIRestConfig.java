package com.foodrec.backend.Config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenAIRestConfig {
    @Value("${OPENAI_API_KEY}")
    private String openAIKey;

    @Bean
    @Qualifier("openaiConfig")
    public RestTemplate openaiConfig() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openAIKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
