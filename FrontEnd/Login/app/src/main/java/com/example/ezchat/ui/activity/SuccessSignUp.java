package com.example.ezchat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ezchat.R;
import com.example.ezchat.ui.login.LoginActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

import com.example.ezchat.domain.User;

/**
 * This activity allows user to review the account information after the user successfully signed up.
 * @author Dengyun Ma
 */
public class SuccessSignUp extends AppCompatActivity {
    private Gson gs;
    ArrayList<User> user_su = LoginActivity.user_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_sign_up);

        TextView userName = (TextView) findViewById(R.id.username_sign_up);
        TextView iD = (TextView) findViewById(R.id.userID_sign_up);

        userName.setText(RegisterActivity.newUserName);

        String id_str = String.valueOf(user_su.get(user_su.size()-1).getUerID()+1);
        iD.setText(id_str);

        Button conTinue = (Button) findViewById(R.id.sign_up_to_login);
        conTinue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent a = new Intent(SuccessSignUp.this,LoginActivity.class);
                startActivity(a);
            }
        });
    }
    @Override
    public void onResume() {

        super.onResume();

    }

}
