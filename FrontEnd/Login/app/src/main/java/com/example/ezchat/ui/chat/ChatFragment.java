package com.example.ezchat.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.ezchat.ui.activity.ChatActivity;
import com.example.ezchat.ui.activity.FriendsProfileActivity;

import com.example.ezchat.ui.activity.GroupChatActivity;
import com.example.ezchat.R;
import com.example.ezchat.ui.friends.FriendsFragment;
import com.example.ezchat.ui.login.LoginActivity;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import app.VolleyController;

import static com.example.ezchat.ui.friends.FriendsFragment.gson;

/**
 * The page to show all the chat history and new chat.
 *
 * @author Dengyun Ma
 */

public class ChatFragment extends Fragment {

//    Button b;

    private ChatViewModel chatViewModel;
    private int temp =0;
    private String username="";

    private String getFriendName(int i){
        return LoginActivity.user_list.get(i).getUsername();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        chatViewModel =
                ViewModelProviders.of(this).get(ChatViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_chat, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
//        b = (Button)root.findViewById(R.id.buttonGroup);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getContext(), GroupChatActivity.class);
//                startActivity(i);
//            }
//        });



        LinearLayout ly = (LinearLayout) root.findViewById(R.id.chatList_ly);
        for(int i=0;i<FriendsFragment.friends_list.size();i++){
            if(FriendsFragment.friends_list.get(i).approved==1&&FriendsFragment.friends_list.get(i).status==1){
                for(int j=0;j<LoginActivity.user_list.size();j++){
                    if(LoginActivity.user_list.get(j).getUerID()==FriendsFragment.friends_list.get(i).friendsId){
                        username = LoginActivity.user_list.get(j).getUsername();
                    }
                }

                temp =FriendsFragment.friends_list.get(i).friendsId;

                TextView tx = new TextView(getContext());
                tx.setText("   "+username);
                tx.setTextSize(20);
                tx.setHeight(150);
                tx.getPaint().setFakeBoldText(true);
                tx.setOnClickListener(new View.OnClickListener() {
                    int id_temp = temp;
                    @Override
                    public void onClick(View view) {
                        FriendsFragment.chatWith = id_temp;
                        Intent i = new Intent(getContext(), ChatActivity.class);
                        startActivity(i);
                    }
                });
                View temp = new View(getContext());
                ly.addView(tx);
                ly.addView(temp);
            }

        }
        TextView textView1 = new TextView(getContext());
        textView1.setText("   "+"Group");
        textView1.setTextSize(20);
        textView1.setHeight(150);
        textView1.getPaint().setFakeBoldText(true);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), GroupChatActivity.class);
                startActivity(i);
            }
        });
        ly.addView(textView1);

        return root;
    }
}
