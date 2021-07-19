package com.example.ezchat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ezchat.R;
import com.example.ezchat.ui.login.LoginActivity;

/**
 * This is the main page of a public account
 * @author Wentao Pei
 */
public class Pub_MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub__main_page);
        Button lg = (Button) findViewById(R.id.public_logout);
        lg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(Pub_MainPage.this,LoginActivity.class);
                startActivity(i);
            }
        });

    }
}
