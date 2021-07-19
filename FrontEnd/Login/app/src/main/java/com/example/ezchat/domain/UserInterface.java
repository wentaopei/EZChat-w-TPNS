package com.example.ezchat.domain;

/**
 * Interface for users
 * @author Wentao Pei
 */

public interface UserInterface
{
    public int getUerID();
    public String getUsername();
    public String getPassword();
    public String getEmail();
    public String pub();
    public String gender();

    public boolean setUerID(int id);

    public boolean setUsername(String un);
    public boolean setPassword(String pw);
    public boolean setEmail(String em);
    public void setGender(String gd);
}
