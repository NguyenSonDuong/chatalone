package com.nguyenduong.chatalone.service;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.nguyenduong.chatalone.model.PnsRequest;
import org.springframework.stereotype.Service;

@Service
public class FCMService {
    public String pushNotification(PnsRequest pnsRequest) {
        Message message = Message.builder().setToken(pnsRequest.getFcmToken())
                .setNotification(new Notification(pnsRequest.getTitle(), pnsRequest.getContent()))
                .putData("click_action","FLUTTER_NOTIFICATION_CLICK")
                .build();
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        return response;
    }
}
