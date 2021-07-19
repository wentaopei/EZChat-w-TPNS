package com.example.ezchat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ezchat.R;
import com.example.ezchat.ui.friends.FriendsFragment;
import com.example.ezchat.ui.login.LoginActivity;

import java.util.ArrayList;

import com.example.ezchat.domain.User;

/**
 * This activity allows user to search a new friends
 * @author Dengyun Ma
 */
public class SearchNewFriendsActivity extends AppCompatActivity {

    public String id =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_new_friends);

        final EditText et = (EditText) findViewById(R.id.editText_friendsId);
        Button bt = (Button) findViewById(R.id.button_searchNewFriend);

        id = et.getText().toString();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = et.getText().toString();
                if(searchNameByid(Integer.valueOf(id))==null){
                    Toast.makeText(getApplicationContext(), "ID not exist", Toast.LENGTH_LONG).show();
                }
                else{
                    FriendsFragment.chatWith = Integer.valueOf(id);
                    Intent i = new Intent(SearchNewFriendsActivity.this,FriendsProfileActivity.class);
                    startActivity(i);
                }
            }
        });
    }
    /**
     * This method found the user by its id
     * @param id the id need to be found
     * @return return the user that it found it by the given id, if id do not exist, return null
     */
    public User searchNameByid(int id){
        ArrayList<User> temp = LoginActivity.user_list;
        for(int i=0;i<temp.size();i++){
            if(temp.get(i).getUerID()==id){
                return temp.get(i);
            }
        }
        return null;
    }
}
