package com.example.ezchat.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ezchat.R;
import com.example.ezchat.ui.friends.FriendsFragment;
import com.example.ezchat.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


import com.example.ezchat.domain.Chat;
import com.example.ezchat.domain.MySingleton;
import com.example.ezchat.domain.User;
import app.VolleyController;

/**
 * This activity allows users chat with each other
 * @author Dengyun Ma
 */
public class ChatActivity extends AppCompatActivity {

    private Gson gson;

    public static ArrayList<Chat> history = new ArrayList<Chat>();
    public int id1 = LoginActivity.user_login.Id;
    public int id2 = FriendsFragment.chatWith;

    public Uri selectImageUri=null;
    public String[] filePathColumn = {MediaStore.Images.Media.DATA};
    public Cursor cursor=null;
    public int columnIndex=0;
    public String picturePath=null;
    public Bitmap bitmap=null;


    public int counter = 0;

    private String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }

    private Bitmap decodeImage(String in){
        byte[] imageByte = Base64.decode(in,Base64.NO_WRAP);
        InputStream is = new ByteArrayInputStream(imageByte);
        Bitmap b = BitmapFactory.decodeStream(is);
        return b;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final LinearLayout layout = (LinearLayout)findViewById(R.id.history_layout);
        final EditText et = (EditText)findViewById(R.id.editText_Text);
        Button bt = (Button) findViewById(R.id.button_sendText);
        ImageButton imb = (ImageButton) findViewById(R.id.imageButton_selectImage);
        final ScrollView sv = (ScrollView) findViewById(R.id.scrollView_chat);

        Handler handler = new Handler();

        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                sv.fullScroll(sv.FOCUS_DOWN);
            }
        });

        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView_chat);



        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VolleyController.sendText(id1, id2, et.getText().toString(), "0",getApplicationContext());
                et.setText("");
                MessageLoad(id1,id2);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });

        imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent1,1);
            }
        });


        MessageLoad(id1,id2);
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(1000);
                        MessageLoad(id1,id2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

        Thread myThread = new Thread(myRunnable);
        myThread.start();
    }


    private void selectPic(Intent intent) throws FileNotFoundException {
        selectImageUri = intent.getData();
        cursor = getContentResolver().query(selectImageUri,filePathColumn,null,null,null);
        cursor.moveToFirst();
        columnIndex=cursor.getColumnIndex(filePathColumn[0]);
        picturePath=cursor.getString(columnIndex);
        System.out.println(picturePath);
        cursor.close();
        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectImageUri));
        VolleyController.sendText(id1, id2, getStringImage(bitmap), "1",getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    try {
                        selectPic(data);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }


    /**
     * This method found the user by its id
     * @param id the id need to be found
     * @return return the user that it found it by the given id, if id do not exist, return null
     */
    public static User searchNameByid(int id){
        ArrayList<User> temp = LoginActivity.user_list;
        for(int i=0;i<temp.size();i++){
            if(temp.get(i).getUerID()==id){
                return temp.get(i);
            }
        }
        return null;
    }

    private void MessageLoad(int id1, int id2){
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

        String uRl = "http://coms-309-ss-3.misc.iastate.edu:8080/history/all/";
        uRl = uRl +String.valueOf(id1);
        uRl = uRl +"/";
        uRl = uRl +String.valueOf(id2);
        StringRequest stringRequest = new StringRequest(uRl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        JsonObject json =  null;
                        Log.d("TAG", response);
                        history = gson.fromJson(response, new TypeToken<ArrayList<Chat>>(){}.getType());
                        if(history.size()==0){
                            return;
                        }
                        for (int i = counter; i < history.size(); i++) {
                            if (history.get(i).ispicture.equalsIgnoreCase("0")) {
                                counter++;
                                TextView time = new TextView(ChatActivity.this);
                                time.setText(history.get(i).time);
                                TextView name = new TextView(ChatActivity.this);
                                name.setText(searchNameByid(history.get(i).sender).getUsername() + ":");
                                name.setTextSize(20);
                                TextView text = new TextView(ChatActivity.this);
                                text.setText(history.get(i).text);
                                text.setTextSize(20);
                                TextView empty = new TextView(ChatActivity.this);
                                empty.setText(" ");
                                LinearLayout layout = (LinearLayout) findViewById(R.id.history_layout);

                                layout.addView(time);
                                layout.addView(name);
                                layout.addView(text);
                                layout.addView(empty);
                            } else {
                                counter++;
                                TextView time = new TextView(ChatActivity.this);
                                time.setText(history.get(i).time);
                                TextView name = new TextView(ChatActivity.this);
                                name.setText(searchNameByid(history.get(i).sender).getUsername() + ":");
                                name.setTextSize(20);


                                TextView empty = new TextView(ChatActivity.this);
                                empty.setText(" ");

                                final ImageView mImageView = new ImageView(getApplicationContext());
                                mImageView.setImageBitmap(decodeImage(history.get(i).text));
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(800, 800);
                                mImageView.setLayoutParams(layoutParams);

                                LinearLayout layout = (LinearLayout) findViewById(R.id.history_layout);

                                layout.addView(time);
                                layout.addView(name);
                                layout.addView(mImageView);
                                layout.addView(empty);
                            }
                        }

                        Log.e("TAG", history.get(0).text);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("TAG", error.getMessage(), error);
                        System.out.println("get wrong");
                    }
                });
        VolleyController.sendChatRequestToServer(id1,id2,getApplicationContext(),stringRequest);
    }

    public boolean sendRequestToServerTest()
    {
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

        final boolean[] success = {false};
        String uRl = "http://coms-309-ss-3.misc.iastate.edu:8080/history/all/4/7";
        System.out.println(uRl);
        StringRequest stringRequest = new StringRequest(uRl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        JsonObject json =  null;
                        Log.d("TAG", response);
                        success[0] =true;

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("TAG", error.getMessage(), error);
                        System.out.println("get wrong");
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        return success[0];
    }

}

