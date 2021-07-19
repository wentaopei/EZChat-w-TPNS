package com.example.ezchat.domain;

/**
 * Construct post for moments.
 * @author Wentao Pei
 */

public class Post {
    public int id;
    public int userid;
    public String text;
    public String imageURL;
    public int mlike;
    public int mdislike;
    public String time;
    public String image2;

    public Post(int luserid,String ltext,String limageURL,int lmlike,int lmdislike,String im2){
        userid = luserid;
        text = ltext;
        imageURL = limageURL;
        mlike = lmlike;
        mdislike = lmdislike;
        image2 = im2;
    }
}
