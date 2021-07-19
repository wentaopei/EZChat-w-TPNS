package com.example.ezchat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

import com.example.ezchat.domain.FriendsList;
import com.example.ezchat.domain.MySingleton;
import com.example.ezchat.domain.User;

/**
 * This Activity allow users to accept friend requests from other users.
 * New Friends relationship will be saved in the sever.
 * @author Dengyun Ma
 */

public class AcceptRequestActivity extends AppCompatActivity {

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_request);

        final LinearLayout l = (LinearLayout) findViewById(R.id.request_layout);

        System.out.println(FriendsFragment.friend_request.size());

        for(int i=0;i< FriendsFragment.friend_request.size();i++){
            final TextView tv = new TextView(this);
            tv.setText(searchNameByid(FriendsFragment.friend_request.get(i).friendsId).getUsername()+" want to be your friend");
            tv.setTextSize(20);

            Button bt = new Button(this);
            bt.setText("Accept");

            final int finalI = i;
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    l.removeView(tv);
                    updateTheFriendStatus(FriendsFragment.friend_request.get(finalI));
                    sendFriendRequest(FriendsFragment.friend_request.get(finalI).friendsId);
                    Toast.makeText(getApplicationContext(), searchNameByid(FriendsFragment.friend_request.get(finalI).friendsId).getUsername()+" is now your friend", Toast.LENGTH_LONG).show();
                }
            });
            l.addView(tv);
            l.addView(bt);
        }
    }
    @Override
    public void onResume() {

        super.onResume();
    }

    /**
     * This method help users generate the new friend relation ship and saved in the server.
     * @param fl the Friend that user accepted
     */
    private void updateTheFriendStatus(FriendsList fl){
        User temp_user = searchNameByid(fl.friendsId);
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

        HashMap<String, Object> headers = new HashMap<String,Object>();
        String id_str = String.valueOf(fl.id);
        headers.put("id",id_str.replaceAll("\"",""));
        headers.put("status","1".replaceAll("\"",""));
        headers.put("approved","1".replaceAll("\"",""));
        headers.put("user",fl.user_id);
        headers.put("friendId",String.valueOf(temp_user.getUerID()).replaceAll("\"",""));


        JSONObject jsonObject = new JSONObject(headers);

        String url = "http://coms-309-ss-3.misc.iastate.edu:8080/users/";
        url = url+String.valueOf(fl.user_id.getUerID());
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
     * This method allow user send friend request to another user
     * @param id the id that the user want to add as friend
     */
    private void sendFriendRequest(int id){
        User temp_user = searchNameByid(id);
        User login = LoginActivity.user_login;
        //FriendsList newFriend = new FriendsList();

        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

        HashMap<String, Object> headers = new HashMap<String,Object>();
        headers.put("id","99999");
        headers.put("status","1".replaceAll("\"",""));
        headers.put("approved","1".replaceAll("\"",""));
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
    private User searchNameByid(int id){
        ArrayList<User> temp = LoginActivity.user_list;
        for(int i=0;i<temp.size();i++){
            if(temp.get(i).getUerID()==id){
                return temp.get(i);
            }
        }
        return null;
    }
}
