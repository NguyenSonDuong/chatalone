package com.nguyenduong.chatalone.websocket;

import com.nguyenduong.chatalone.model.ChatMessage;
import com.nguyenduong.chatalone.responstory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ChatController {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody ChatMessage textMessageDTO) {
        template.convertAndSend("/topic/message", textMessageDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload ChatMessage textMessageDTO) {
        System.out.println(textMessageDTO.getData());
        template.convertAndSend("/topicchat/topic/message.user-"+textMessageDTO.getSender(),textMessageDTO);
    }


    @SendTo("/topic/message")
    public ChatMessage broadcastMessage(@Payload ChatMessage textMessageDTO) {
        return textMessageDTO;
    }

}