package com.example.ezchat.ui.friends;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ezchat.ui.activity.AcceptRequestActivity;
import com.example.ezchat.ui.activity.FriendSearchActivity;
import com.example.ezchat.ui.activity.FriendsProfileActivity;
import com.example.ezchat.R;
import com.example.ezchat.ui.activity.SearchNewFriendsActivity;
import com.example.ezchat.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import com.example.ezchat.domain.MySingleton;


import app.VolleyController;
import com.example.ezchat.domain.FriendsList;

/**
 * The page to list all the user's friends as well as searching friends
 * @author Dengyun Ma
 */

public class FriendsFragment extends Fragment {

    private FriendsViewModel friendsViewModel;
    private Object FriendsViewModel;

    public int user_id;

    public static int chatWith;
    public int temp = 0;
    public static boolean connected = true;

    public static String searchKey;

    public static ArrayList<FriendsList> friends_list = new ArrayList<FriendsList>();
    public static ArrayList<FriendsList> friend_request = new ArrayList<FriendsList>();
    public static Gson gson;

    public String uRL;

    /**
     * Method that use volley to send request to the server
     */
//    public void sendRequestToServer()
//    {
//        GsonBuilder gb = new GsonBuilder();
//        gson = gb.create();
//
//        StringRequest stringRequest = new StringRequest(uRL,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response)
//                    {
//                        JsonObject json =  null;
//                        Log.d("TAG", response);
//                        ArrayList<friendsList> temp = gson.fromJson(response, new TypeToken<ArrayList<friendsList>>(){}.getType());
//                        friends_list = (ArrayList<friendsList>) temp.clone();
//                        LoginActivity.friends = friends_list;
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error)
//                    {
//                        Log.e("TAG", error.getMessage(), error);
//                    }
//                });
//        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
//
//        connected = true;
//    }
    public void sendRequestToServer()
    {
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

        StringRequest stringRequest = new StringRequest(uRL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        JsonObject json =  null;
                        Log.d("TAG", response);
                        ArrayList<FriendsList> temp = gson.fromJson(response, new TypeToken<ArrayList<FriendsList>>(){}.getType());
                        friends_list = (ArrayList<FriendsList>) temp.clone();
                        LoginActivity.friends = friends_list;
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

        connected = true;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        friendsViewModel =
                ViewModelProviders.of(this).get(FriendsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_friends, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        friendsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        user_id = LoginActivity.user_login.getUerID();
        uRL = "http://coms-309-ss-3.misc.iastate.edu:8080/users/";
        uRL = uRL+Integer.toString(user_id);
        uRL = uRL+"/friends";

        VolleyController.sendListRequestToServer(getContext(),uRL);

//        System.out.println(uRL);
//        System.out.println(friends_list.size());
//        System.out.println(LoginActivity.friends.size());

        Button addFriend = (Button)root.findViewById(R.id.button_addFriends);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SearchNewFriendsActivity.class);
                startActivity(i);
            }
        });


        onResume();

        friends_list = LoginActivity.friends;

        final LinearLayout friends = (LinearLayout) root.findViewById(R.id.friends_list);

        final Button bt = (Button) root.findViewById(R.id.button_friend_request);

        String username = "";




        for(int i=0;i<friends_list.size();i++){
            friend_request = new ArrayList<FriendsList>();
            if(friends_list.get(i).approved==1&&friends_list.get(i).status==1){
                for(int j=0;j<LoginActivity.user_list.size();j++){
                    if(LoginActivity.user_list.get(j).getUerID()==friends_list.get(i).friendsId){
                        username = LoginActivity.user_list.get(j).getUsername();
                    }
                }

                temp =friends_list.get(i).friendsId;

                TextView tx = new TextView(getContext());
                tx.setText("   "+username);
                tx.setTextSize(20);
                tx.setHeight(150);
                tx.getPaint().setFakeBoldText(true);
                tx.setOnClickListener(new View.OnClickListener() {
                    int id_temp = temp;
                    @Override
                    public void onClick(View view) {
                        chatWith = id_temp;
                        Intent i = new Intent(getContext(), FriendsProfileActivity.class);
                        startActivity(i);
                    }
                });
                View temp = new View(getContext());
                friends.addView(tx);
                friends.addView(temp);
            }
            else if(friends_list.get(i).approved==0&&friends_list.get(i).status==1){
                friend_request.add(friends_list.get(i));
            }
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AcceptRequestActivity.class);
                startActivity(i);
            }
        });

        final EditText keyword = (EditText) root.findViewById(R.id.friends_name);
        Button searchButton = (Button) root.findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchKey = keyword.getText().toString();
                Intent i = new Intent(getContext(), FriendSearchActivity.class);
                startActivity(i);
            }
        });


        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);

        user_id = LoginActivity.user_login.getUerID();
        uRL = "http://coms-309-ss-3.misc.iastate.edu:8080/users/";
        uRL = uRL+Integer.toString(user_id);
        uRL = uRL+"/friends";
        VolleyController.sendListRequestToServer(getContext(),uRL);


    }
    @Override
    public void onStart() {

        super.onStart();
        user_id = LoginActivity.user_login.getUerID();
        uRL = "http://coms-309-ss-3.misc.iastate.edu:8080/users/";
        uRL = uRL+Integer.toString(user_id);
        uRL = uRL+"/friends";
        VolleyController.sendListRequestToServer(getContext(),uRL);
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onResume() {

        super.onResume();
        VolleyController.sendListRequestToServer(getContext(),uRL);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        FriendsViewModel = ViewModelProviders.of(this).get(FriendsViewModel.class);

        user_id = LoginActivity.user_login.getUerID();
        uRL = "http://coms-309-ss-3.misc.iastate.edu:8080/users/";
        uRL = uRL+Integer.toString(user_id);
        uRL = uRL+"/friends";
        VolleyController.sendListRequestToServer(getContext(),uRL);


    }

    public boolean sendRequestToServerTest()
    {
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

        final boolean[] success = {false};

        StringRequest stringRequest = new StringRequest("http://coms-309-ss-3.misc.iastate.edu:8080/users/14/friends",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        JsonObject json =  null;
                        Log.d("TAG", response);
                        success[0] = true;
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
        return success[0];
    }

}