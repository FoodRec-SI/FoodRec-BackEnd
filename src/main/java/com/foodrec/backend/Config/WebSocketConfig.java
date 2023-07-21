package com.foodrec.backend.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        /*All route: notifications sent to all users
        * Specific route: notifications sent to specific users.*/
        config.enableSimpleBroker("/all","/specific");

        /*Sends messages to the APPLICATION.*/
        config.setApplicationDestinationPrefixes("/wsapp");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*NOTE: These endpoints MUST BE TYPED ON THE browser URL bar!*/
        registry.addEndpoint("/ws"); //for browsers supporting WebSocket
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS(); //for browsers not supporting WebSocket
    }
}
