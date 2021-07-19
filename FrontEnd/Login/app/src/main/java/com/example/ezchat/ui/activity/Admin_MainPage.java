package com.example.ezchat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ezchat.R;
import com.example.ezchat.ui.login.LoginActivity;

/**
 * This the main page of Administrator
 * @author Wentao Pei
 */
public class Admin_MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button lg = (Button) findViewById(R.id.admin_Logout);
        lg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(Admin_MainPage.this,LoginActivity.class);
                startActivity(i);
            }
        });

    }
}
