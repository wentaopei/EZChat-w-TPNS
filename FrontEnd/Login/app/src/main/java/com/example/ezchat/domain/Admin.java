package com.example.ezchat.domain;

/**
 * Construct admin object.
 * @author Dengyun Ma
 */

public class Admin implements AdminInterface {
    public String loginID;
    public String password;

    public Admin(String login_ID,String passwd)
    {
        loginID = login_ID;
        password = passwd;
    }
    @Override
    public boolean reSetPasswd(String passd) {
        if (passd.length()<6){
            return false;
        }
        else{
            password = passd;
        }
        return true;
    }
}
