package com.nguyenduong.chatalone.model;

import com.nimbusds.jose.shaded.json.JSONObject;

import java.util.Date;

public class HelpController {
    public static JSONObject GetSuccess(String message,Object data){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content",data);
        jsonObject.put("create_at",new Date());
        jsonObject.put("message",message);
        return  jsonObject;
    }
    public static JSONObject GetError(String message,int status){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("create_at",new Date());
        jsonObject.put("message",message);
        jsonObject.put("status",status);
        return  jsonObject;
    }
}
