package com.example.ezchat.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Construct chat object.
 * @author Dengyun Ma
 */

public class Chat {
    @SerializedName("id")
    public int id;
    @SerializedName("sender")
    public int sender;
    @SerializedName("receiver")
    public int receiver;
    @SerializedName("text")
    public String text;
    @SerializedName("time")
    public String time;

    public String ispicture;

    public User user;

    Chat(int iD,int senDer,int receiveR,String texT,String timE,String isPicture){
        id = iD;
        sender =senDer;
        receiver = receiveR;
        text = texT;
        time =timE;
        ispicture = isPicture;
    }

}
