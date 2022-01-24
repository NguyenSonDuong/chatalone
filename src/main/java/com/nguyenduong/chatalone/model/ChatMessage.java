package com.nguyenduong.chatalone.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String content;
    private byte[] data;
    private String sender;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    private String messageStatus;
    private String messageType;

    public class MessageStatus{
        public static final String CHAT = "chat";
        public static final String CLOSE = "close";
        public static final String CONNECT = "connect";

    }
    public class MessageType{
        public static final String IMAGE = "image";
        public static final String TEXT = "text";
        public static final String VOICE = "voice";
        public static final String VIDEO = "video";


    }
}


