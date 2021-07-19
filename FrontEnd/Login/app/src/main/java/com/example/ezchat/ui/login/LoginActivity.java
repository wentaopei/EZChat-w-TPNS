package com.example.ezchat.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ezchat.ui.activity.Admin_MainPage;
import com.example.ezchat.ui.activity.PhotoActivity;
import com.example.ezchat.ui.activity.Pub_MainPage;
import com.example.ezchat.R;


import com.example.ezchat.domain.User;
import app.VolleyController;

import com.example.ezchat.ui.activity.RegisterActivity;
import com.example.ezchat.ui.activity.UserActivity;
import com.example.ezchat.domain.FriendsList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Login main page for use to login or jump to register page.
 *
 * @author Wentao Pei
 */

public class LoginActivity extends AppCompatActivity
{

    private LoginViewModel loginViewModel;
    public static ArrayList<User> user_list =new ArrayList<User>();
    public String URL = "http://coms-309-ss-3.misc.iastate.edu:8080/users";
    public static User user_login = null;
    public static User user_lastone=null;

    private Gson gson;

    private JsonObject json = new JsonObject();

    public static ArrayList<FriendsList> friends = new ArrayList<FriendsList>();

    ArrayList<User> user = new ArrayList<User>();


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        VolleyController.sendLoginRequestToServer(this);



        Button signUp = (Button) findViewById(R.id.register);
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });


//        Button showList = (Button) findViewById(R.id.button_show);
//        showList.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent a = new Intent(LoginActivity.this,ShowActivity.class);
//                startActivity(a);
//            }
//        });
//
//        Button ima = (Button) findViewById(R.id.buttonImage);
//        ima.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent c = new Intent(LoginActivity.this, PhotoActivity.class);
//                startActivity(c);
//            }
//        });




        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

   }

    /**
     * To check the status of login and show the correct welcome message to the user.
     * @param model
     */

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome);
        String welcomeAdmin = "Welcome ! Administrator";
        String welcomeUser =  "Welcome ! User ";
        String welcomePub = "Welcome ! public user ";
        String loginFailed="login failed. please check your username and password";


        EditText userName,passWord;
        userName = (EditText) findViewById(R.id.username);
        passWord = (EditText) findViewById(R.id.password);
        String userName_str = userName.getText().toString();
        String passWord_str = passWord.getText().toString();


        boolean emailCorrect=false;

        for(int i=0;i<user_list.size();i++){
            if(user_list.get(i).getEmail().equals(userName_str)){
                emailCorrect = true;
                user_login = user_list.get(i);
            }
            user_lastone = user_list.get(i);
        }


        if(emailCorrect == true&&passWord_str.equalsIgnoreCase(user_login.getPassword())){
            if(user_login.pub().equals("1")){
                Intent k = new Intent(LoginActivity.this, Pub_MainPage.class);
                startActivity(k);
                Toast.makeText(getApplicationContext(), welcomePub+user_login.getUsername(), Toast.LENGTH_LONG).show();

            }
            else if(user_login.pub().equals("2")){
                Intent myIntent = new Intent(LoginActivity.this, Admin_MainPage.class);
                startActivity(myIntent);
                Toast.makeText(getApplicationContext(), welcomeAdmin, Toast.LENGTH_LONG).show();
            }
            else{
                Intent k2 = new Intent(LoginActivity.this, UserActivity.class);
                startActivity(k2);
                Toast.makeText(getApplicationContext(), welcomeUser+user_login.getUsername(), Toast.LENGTH_LONG).show();

            }
        }

        else{
            Intent k3 = new Intent(LoginActivity.this,LoginActivity.class);
            startActivity(k3);
            Toast.makeText(getApplicationContext(), loginFailed, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * If login is failed, this method will show the user the message.
     * @param errorString
     */

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


}
