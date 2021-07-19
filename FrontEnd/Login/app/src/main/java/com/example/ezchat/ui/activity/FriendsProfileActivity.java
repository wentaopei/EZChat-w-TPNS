package com.example.ezchat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ezchat.R;
import com.example.ezchat.ui.friends.FriendsFragment;
import com.example.ezchat.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


import com.example.ezchat.domain.MySingleton;
import com.example.ezchat.domain.User;


import com.example.ezchat.domain.FriendsList;
import com.example.ezchat.domain.MySingleton;
import com.example.ezchat.domain.User;

/**
 * This activity would show a selected friend's profile to the user
 * @author Wentao Pei
 */
public class FriendsProfileActivity extends AppCompatActivity {

    public int id_friend = 0;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_profile);

        id_friend = FriendsFragment.chatWith;
        User temp = searchNameByid(id_friend);

        TextView name = (TextView) findViewById(R.id.friends_userName_show);
        TextView id = (TextView)findViewById(R.id.friends_iD_show);
        TextView gender = (TextView) findViewById(R.id.friends_gender_show);
        TextView email = (TextView) findViewById(R.id.friends_email_show);
        Button addFriendOrChat = (Button) findViewById(R.id.buttonAlternative);


        name.setText(temp.getUsername());
        String id_str = String.valueOf(temp.getUerID());
        id.setText(id_str);
        gender.setText(temp.gender());
        email.setText(temp.email);

        if(isYourfriend(temp.getUerID())){
            addFriendOrChat.setText("Chat");
            addFriendOrChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(FriendsProfileActivity.this,ChatActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            });
        }
        else{
            final int temp_id = temp.getUerID();
            addFriendOrChat.setText("AddFriend");
            addFriendOrChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendFriendRequest(temp_id);
                    Toast.makeText(getApplicationContext(), "Friend Request sent.", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    /**
     * If the search id is not friend to the user, the user could send the friend request to him/her
     * @param id friend's id
     */
    private void sendFriendRequest(int id){
        User temp_user = searchNameByid(id);
        User login = LoginActivity.user_login;

        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

        HashMap<String, Object> headers = new HashMap<String,Object>();
        headers.put("id","99999");
        headers.put("status","1".replaceAll("\"",""));
        headers.put("approved","0".replaceAll("\"",""));
        headers.put("user",temp_user);
        headers.put("friendId",String.valueOf(login.getUerID()).replaceAll("\"",""));


        JSONObject jsonObject = new JSONObject(headers);

        String url = "http://coms-309-ss-3.misc.iastate.edu:8080/users/";
        url = url+String.valueOf(temp_user.getUerID());
        url = url+"/friends";

        JsonObjectRequest js = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG",response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG",error.getMessage(),error);
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(js);
    }

    /**
     * This method found the user by its id
     * @param id the id need to be found
     * @return return the user that it found it by the given id, if id do not exist, return null
     */
    public User searchNameByid(int id){
        ArrayList<User> temp = LoginActivity.user_list;
        for(int i=0;i<LoginActivity.user_list.size();i++){
            if(temp.get(i).getUerID()==id){
                return temp.get(i);
            }
        }
        return null;
    }

    /**
     * This method would determine if another user is a friend to the user.
     * @param id id that user want to search
     * @return true if the id that user search is already a friend, else false.
     */
    private boolean isYourfriend(int id){
        ArrayList<FriendsList> temp = FriendsFragment.friends_list;
        for(int i=0;i<temp.size();i++){
            if(temp.get(i).friendsId==id){
                return true;
            }
        }
        return false;
    }
}
