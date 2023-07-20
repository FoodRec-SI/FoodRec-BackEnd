package com.foodrec.backend.PushNotifAPI.controller;


import com.foodrec.backend.PushNotifAPI.controller.dto.MessageDTO;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PushNotifController {
    @Autowired
    //Sends a message to a user.
    SimpMessagingTemplate simpMessagingTemplate;

    /*Receives messages (called A) on this endpoint.
    * To access this, type: /wsapp/msg
    * (wsapp is the endpoint defined in WebSocketConfig)*/
    @MessageMapping("/public")
    @SendTo("/all/messages") //sends the received msg (A) to this endpoint
    public MessageDTO send(final MessageDTO message) throws Exception{
        System.out.println("This is a test message!");
        return message;
    }
    @MessageMapping("/private")
    public void sendToSpecificUser(@Payload MessageDTO message) {
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/specific", message);
    }
}
