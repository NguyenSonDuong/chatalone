package com.nguyenduong.chatalone.model.responsive;

import com.nimbusds.jose.shaded.json.JSONObject;

import java.util.Date;

public class Responsive {
    public static JSONObject SuccessResponsive(String title,Object content){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","success");
        jsonObject.put("title",title);
        jsonObject.put("content",content);
        jsonObject.put("create_time", new Date());
        return  jsonObject;
    }
    public static JSONObject ErrorResponsive(String title,int code){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","Error");
        jsonObject.put("title",title);
        jsonObject.put("code",code);
        return  jsonObject;
    }
}
