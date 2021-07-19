package com.example.ezchat.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ezchat.R;
import com.example.ezchat.ui.login.LoginActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

import com.example.ezchat.domain.User;
import app.VolleyController;
import com.example.ezchat.domain.MySingleton;
import com.example.ezchat.domain.User;

/**
 * This activity allows user to create a new account
 * @author Wentao Pei
 */
public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Gson gson;
    public static ArrayList<User> user = new ArrayList<User>();
    public static String newUserName = null;
    public static User newUser = new User(0,null,null,null,null,null);
    public static String newUserID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final Spinner genderChoose = (Spinner) findViewById(R.id.gender_spinner);
        final Spinner typeChoose = (Spinner) findViewById(R.id.type_spinner);

        ArrayAdapter<CharSequence> adapter_gender = ArrayAdapter.createFromResource(this,R.array.gender_select,android.R.layout.simple_spinner_item);
        adapter_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderChoose.setAdapter(adapter_gender);
        genderChoose.setOnItemSelectedListener(this);

        genderChoose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                String s = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        ArrayAdapter<CharSequence> adapter_type = ArrayAdapter.createFromResource(this,R.array.type_select,android.R.layout.simple_spinner_item);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeChoose.setAdapter(adapter_type);
        typeChoose.setOnItemSelectedListener(this);

        typeChoose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                if(typeChoose.getSelectedItem().equals("Please choose account type"))
                {

                    //Do nothing.
                }
                else{

                    Toast.makeText(RegisterActivity.this, typeChoose.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


        Button signUp = (Button) findViewById(R.id.signup_button);
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(signUpCorrectly()){
                    EditText usName = (EditText)findViewById(R.id.usernam_enter);
                    EditText pw =(EditText)findViewById(R.id.password_enter);
                    EditText pw2 = (EditText)findViewById(R.id.password_reenter);
                    EditText email = (EditText)findViewById(R.id.email_enter);
                    Spinner gender = (Spinner)findViewById(R.id.gender_spinner);
                    Spinner type = (Spinner)findViewById(R.id.type_spinner);


                    newUserName = usName.getText().toString();

                    String type_int="0";
                    if(type.getSelectedItem().toString().equals("Normal")){
                        type_int = "0";
                    }
                    else {
                        type_int = "1";
                    }

                    newUser = new User(99999999,usName.getText().toString(),pw.getText().toString(),email.getText().toString(),gender.getSelectedItem().toString(),type_int);
                    user.add(newUser);
                    VolleyController.sendRegisterRequest(getApplicationContext(),usName,pw,email,gender,type_int);

                    Intent i = new Intent(RegisterActivity.this, SuccessSignUp.class);
                    startActivity(i);
                }

            }


        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * This method to check whether the user input all information correctly.
     * @return Return true if all register information input correctly.
     */
    public boolean signUpCorrectly(){
        EditText usName = (EditText)findViewById(R.id.usernam_enter);
        EditText pw =(EditText)findViewById(R.id.password_enter);
        EditText pw2 = (EditText)findViewById(R.id.password_reenter);
        EditText email = (EditText)findViewById(R.id.email_enter);
        Spinner gender = (Spinner)findViewById(R.id.gender_spinner);
        Spinner type = (Spinner)findViewById(R.id.type_spinner);

        for(int i=0;i< LoginActivity.user_list.size();i++){
            if(email.getText().toString().equalsIgnoreCase(LoginActivity.user_list.get(i).email)){
                Toast.makeText(getApplicationContext(), "Email address already existed. Please input a different address.", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if(pw.getText().toString().length()>=6){
            if(pw.getText().toString().equals(pw2.getText().toString())){
                if (gender.getSelectedItem().toString().equals("Male")||gender.getSelectedItem().toString().equals("Female")){
                    if(type.getSelectedItem().toString().equals("Public")||type.getSelectedItem().toString().equals("Normal")){
                        if(email.getText().toString().contains("@")){
                            return true;
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Email address format incorrect. Please input your Email correctly.", Toast.LENGTH_LONG).show();
                            return false;
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please choose your account type.", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please choose your gender.", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Password Confirmation failed.Please make sure both password inputs are the same", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return false;
    }

}
