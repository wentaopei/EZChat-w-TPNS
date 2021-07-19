package com.example.ezchat.domain;


public class Comments {
    public int id;
    public String userId;
    public String comment;
    public Post moments_id;

    Comments(int iid,String iuser_id,String icomment,Post imoment_id){
        id = iid;
        userId=iuser_id;
        comment=icomment;
        moments_id=imoment_id;
    }
}
