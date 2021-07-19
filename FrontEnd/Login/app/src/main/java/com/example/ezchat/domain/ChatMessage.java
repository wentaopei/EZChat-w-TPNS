package com.example.ezchat.domain;

/**
 * Construct message object.
 * @author Dengyun Ma
 */

public class ChatMessage {
    public String content;
    public String userMark;

    public ChatMessage(String conTent,String user_mark){
        content = conTent;
        userMark = user_mark;
    }

    public void changeContent(String c){
        content = c;
    }
    public void changeUser(String u){
        userMark = u;
    }
}
