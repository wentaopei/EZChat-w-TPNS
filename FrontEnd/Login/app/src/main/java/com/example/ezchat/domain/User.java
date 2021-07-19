package com.example.ezchat.domain;


import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Construct users for different type. The user will have username, password, email, id, gender and type.
 * @author Wentao Pei
 */

public class User implements UserInterface
{
    @SerializedName("name")
    public String userName;

    @SerializedName("password")
    public String passWord;
    public String email;

    @SerializedName("id")
    public int Id;

    public String gender;

    @SerializedName("type")
    public String pub;

    public ArrayList<Integer> friends = new ArrayList<Integer>();
    public ArrayList<Chat> chatRecords = new ArrayList<Chat>();

    public User(int id, String un,String pw, String em, String gd,String pu)
    {
        Id = id;
        userName = un;
        passWord = pw;
        email = em;
        gender = gd;
        pub = pu;
    }
    @Override
    public int getUerID()
    {
        return Id;
    }

    @Override
    public String getUsername()
    {
        return userName;
    }
    @Override
    public String getPassword()
    {
        return passWord;
    }

    @Override
    public String getEmail()
    {
        return email;
    }

    @Override
    public String pub() {
        return pub;
    }

    @Override
    public String gender()
    {
        return gender;
    }

    @Override
    public boolean setUsername(String un)
    {
        userName = un;
        return true;
    }
    @Override
    public boolean setPassword(String pw)
    {
        passWord = pw;
        return true;
    }

    @Override
    public boolean setEmail(String em)
    {
        email = em;
        return true;
    }

    @Override
    public boolean setUerID(int id)
    {
        Id = id;
        return true;
    }

    @Override
    public void setGender(String gd)
    {
        gender = gd;
    }
}
