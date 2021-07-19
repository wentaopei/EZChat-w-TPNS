package com.example.ezchat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ezchat.R;
import com.example.ezchat.ui.friends.FriendsFragment;
import com.example.ezchat.ui.login.LoginActivity;

import java.util.ArrayList;

import com.example.ezchat.domain.User;
import com.example.ezchat.domain.FriendsList;
import com.example.ezchat.domain.User;

/**
 * This class allows users search their friends from their friends list
 * @author Wentao Pei
 */
public class FriendSearchActivity extends AppCompatActivity {

    public String keyword;
    public static ArrayList<FriendsList> fl = new ArrayList<FriendsList>();
    public int temp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_search);
        formTheList();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * This method can create the friend list based on the keyword input by user.
     */
    private void formTheList(){
        keyword = FriendsFragment.searchKey;
        fl = LoginActivity.friends;
        System.out.println(keyword);
        System.out.println(fl.size());
        final ArrayList<User> outputList = new ArrayList<User>();
        for (int i=0;i<fl.size();i++){
            if(searchNameByid(fl.get(i).friendsId).getUsername().contains(keyword)){
                outputList.add(searchNameByid(fl.get(i).friendsId));
            }
        }

        LinearLayout friends = (LinearLayout) findViewById(R.id.friends_search_list);


        for(int j=0;j<outputList.size();j++){
            temp = outputList.get(j).Id;
            TextView tx = new TextView(this);
            tx.setText(outputList.get(j).getUsername());
            tx.setTextSize(20);
            tx.setHeight(150);
            tx.setOnClickListener(new View.OnClickListener() {
                int id_temp = temp;
                @Override
                public void onClick(View view) {
                    FriendsFragment.chatWith = id_temp;
                    Intent i = new Intent(FriendSearchActivity.this, FriendsProfileActivity.class);
                    startActivity(i);
                }
            });
            View temp = new View(this);
            friends.addView(tx);
            friends.addView(temp);
        }
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
