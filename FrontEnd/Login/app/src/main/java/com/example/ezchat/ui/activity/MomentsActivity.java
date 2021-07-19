package com.example.ezchat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.ezchat.R;
import com.example.ezchat.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.ezchat.domain.Comments;
import com.example.ezchat.domain.MySingleton;
import com.example.ezchat.domain.Post;
import com.example.ezchat.domain.User;
import app.VolleyController;


/**
 * This activity will show all friends' social shares to a user
 * @author Dengyun Ma
 */
public class MomentsActivity extends AppCompatActivity {
    private Gson gson;
    public static ArrayList<Post> posts = new ArrayList<Post>();
    public static ArrayList<Comments> comments = new ArrayList<Comments>();

    /**
     * This method can get all friends' shares from the server.
     */
    public Bitmap decodeImage(String in){
        byte[] imageByte = Base64.decode(in,Base64.NO_WRAP);
        InputStream is = new ByteArrayInputStream(imageByte);
        Bitmap b = BitmapFactory.decodeStream(is);
        return b;
    }

    private void replyOnPost(String comment,String id,Post post){
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();
        final HashMap<String, Object> headers = new HashMap<String,Object>();
        headers.put("id", " ");
        headers.put("moments_id",post);
        headers.put("comment",comment.replaceAll("\"",""));
        headers.put("userId",id.replaceAll("\"",""));
        JSONArray ja = new JSONArray();
        ja.put(headers);
        JSONObject jsonObject = new JSONObject(headers);
        String uRL = "http://coms-309-ss-3.misc.iastate.edu:8080/moment/";
        uRL = uRL+post.id;
        uRL = uRL+"/comment";
        JsonObjectRequest js = new JsonObjectRequest(Request.Method.POST, uRL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG",response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG",error.getMessage(),error);
                        System.out.println("send wrong");
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(js);
    }
    /**
     * This method create the post list to the linear layout and show to the user.
     */
    public void createLayout(){
        final LinearLayout ly = (LinearLayout) findViewById(R.id.layout_post);
        for(int i=0;i<posts.size();i++){
                TextView userName = new TextView(this);
                TextView text = new TextView(this);
                TextView time = new TextView(this);
                final TextView peopleLike = new TextView(this);
                final TextView peopleDislike = new TextView(this);

                final Button like = new Button(this);
                final Button dislike = new Button(this);

                User temp = searchNameByid(posts.get(i).userid);
                userName.setText(temp.getUsername());
                userName.setTextSize(20);
                text.setText(posts.get(i).text);
                text.setTextSize(20);
                time.setText(posts.get(i).time);

                String howmanylikes = posts.get(i).mlike + " likes";
                peopleLike.setText(howmanylikes);
                peopleLike.setTextColor(Color.BLUE);

                String howmanydislike = posts.get(i).mdislike+" dislikes";
                peopleDislike.setText(howmanydislike);
                peopleDislike.setTextColor(Color.RED);

                like.setText("Like");
                dislike.setText("Dislike");

                final Post tem = posts.get(i);
                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = tem.id;
                        int newLike = tem.mlike+1;
                        int likenum = tem.mlike;
                        VolleyController.updateLike(id,tem.userid,tem.text,tem.imageURL,newLike,tem.mdislike,tem.time,getApplicationContext());
                        likenum++;
                        peopleLike.setText(likenum+" likes");
                    }
                });

                dislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = tem.id;
                        int newDislike = tem.mdislike+1;
                        int dislikenum = tem.mdislike;
                        VolleyController.updateLike(id,tem.userid,tem.text,tem.imageURL,tem.mlike,newDislike,tem.time,getApplicationContext());
                        dislikenum++;
                        peopleDislike.setText(dislikenum+" dislikes");
                    }
                });

                ly.addView(userName);
                ly.addView(text);

                if(posts.get(i).imageURL!=null) {
                    /**
                     * IMAGE Here.
                     */
                    final ImageView mImageView = new ImageView(this);
                    mImageView.setImageBitmap(decodeImage(posts.get(i).imageURL));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(800, 800);
                    mImageView.setLayoutParams(layoutParams);
                    ly.addView(mImageView);
                    //END Here
                }


                ly.addView(time);

                LinearLayout likeLy = new LinearLayout(this);
                likeLy.addView(peopleLike);
                likeLy.addView(peopleDislike);

                ly.addView(likeLy);

                //comments shows here
                final LinearLayout commentLy = new LinearLayout(this);
                commentLy.setOrientation(LinearLayout.VERTICAL);
                ly.addView(commentLy);

                final TextView showComment = new TextView(this);
                showComment.setText("Show comment");
                showComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(showComment.getText().toString().equalsIgnoreCase("Show comment")){
                            loadComment(commentLy,tem,showComment);
                        }
                        else{
                            commentLy.removeAllViews();
                            showComment.setText("Show comment");
                        }
                    }
                });
                ly.addView(showComment);
                LinearLayout likeButtonLy = new LinearLayout(this);
                likeButtonLy.addView(like);
                likeButtonLy.addView(dislike);
                ly.addView(likeButtonLy);
                TextView empty = new TextView(this);
                ly.addView(empty);

        }
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

//    /**
//     * This method allows user like a post and save the like in the server
//     * @param id The post's id
//     * @param iuserid the user id
//     * @param itext the post's text
//     * @param iurl the image url
//     * @param imlike the like numbers
//     * @param imdislike the dislike number
//     * @param itime the time that the user sent the post
//     */
//    public void updateLike(int id,int iuserid,String itext,String iurl,int imlike,int imdislike,String itime){
//        GsonBuilder gb = new GsonBuilder();
//        gson = gb.create();
//
//        HashMap<String, Object> headers = new HashMap<String,Object>();
//        headers.put("id",id);
//        headers.put("userid",iuserid);
//        headers.put("text",itext.replaceAll("\"",""));
//        headers.put("imageURL",iurl);
//        headers.put("mlike",imlike);
//        headers.put("mdislike",imdislike);
//        headers.put("time",itime.replaceAll("\"",""));
//
//
//        JSONObject jsonObject = new JSONObject(headers);
//
//        String url = "http://coms-309-ss-3.misc.iastate.edu:8080/moments/add";
//
//        JsonObjectRequest js = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("TAG",response.toString());
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("TAG",error.getMessage(),error);
//                    }
//                });
//
//        MySingleton.getInstance(MomentsActivity.this).addToRequestQueue(js);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);
        loadPost();

        ImageButton refesh = (ImageButton) findViewById(R.id.imageButton_refesh);
        refesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPost();
                LinearLayout ly = (LinearLayout) findViewById(R.id.layout_post);
                ly.removeAllViews();
                createLayout();
            }
        });

        ImageButton create = (ImageButton) findViewById(R.id.imageButton_createPost);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MomentsActivity.this,NewPostActivity.class);
                startActivity(i);
            }
        });
    }

    private void loadPost(){
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();
        String uRL = "http://coms-309-ss-3.misc.iastate.edu:8080/moments/all/"+ LoginActivity.user_login.getUerID();;

        StringRequest stringMomentRequest = new StringRequest(uRL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        JsonObject json =  null;
                        Log.d("TAG", response);
                        posts = gson.fromJson(response, new TypeToken<ArrayList<Post>>(){}.getType());

                        createLayout();
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
        VolleyController.sendMomentRequestToServer(getApplicationContext(),stringMomentRequest);
    }

    private void loadComment(final LinearLayout commentLy,final Post tem,final TextView showComment){
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();
        String uRL = "http://coms-309-ss-3.misc.iastate.edu:8080/moment/";
        uRL = uRL+tem.id;
        uRL = uRL+"/comment";
        StringRequest stringRequest = new StringRequest(uRL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        JsonObject json =  null;
                        Log.d("TAG", response);
                        comments = gson.fromJson(response, new TypeToken<ArrayList<Comments>>(){}.getType());
                        Log.e("TAG", String.valueOf(comments.size()));
                        final LinearLayout comly = new LinearLayout(MomentsActivity.this);
                        comly.setOrientation(LinearLayout.VERTICAL);
                        for(int i=0;i<comments.size();i++){
                            TextView com = new TextView(MomentsActivity.this);
                            com.setText("   "+comments.get(i).userId+": "+comments.get(i).comment);
                            comly.addView(com);
                        }
                        commentLy.addView(comly);
                        final EditText et = new EditText(MomentsActivity.this);
                        et.setHint("say something to this post...");
                        commentLy.addView(et);

                        Button sendComment = new Button(MomentsActivity.this);
                        sendComment.setText("Reply");
                        sendComment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String content = et.getText().toString();
                                replyOnPost(content,LoginActivity.user_login.userName,tem);
                                TextView com = new TextView(MomentsActivity.this);
                                com.setText("   "+LoginActivity.user_login.userName+": "+content);
                                comly.addView(com);
                                et.setText("");
                            }
                        });
                        commentLy.addView(sendComment);
                        showComment.setText("Hide comment");
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
        VolleyController.sendCommentRequestToServer(getApplicationContext(),stringRequest);
    }

    public boolean sendRequestToServerTest()
    {
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

        String uRL = "http://coms-309-ss-3.misc.iastate.edu:8080/moments/all/14";
        final boolean[] success = {false};

        StringRequest stringRequest = new StringRequest(uRL,
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

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        return success[0];
    }



}
