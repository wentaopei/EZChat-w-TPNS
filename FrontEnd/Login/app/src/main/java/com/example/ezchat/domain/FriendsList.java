package com.example.ezchat.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Construct friends list.
 * @author Dengyun Ma
 */

public class FriendsList {
    @SerializedName("id")
    public int id;

    @SerializedName("friendId")
    public int friendsId;

    @SerializedName("status")
    public int status;

    @SerializedName("approved")
    public int approved;

    @SerializedName("user")
    public User user_id;

    FriendsList(int iD, int friendsID, int staTus, int approVed, User user_ID){
        id = iD;
        friendsId = friendsID;
        status = staTus;
        approved = approVed;
        user_id = user_ID;
    }
}
