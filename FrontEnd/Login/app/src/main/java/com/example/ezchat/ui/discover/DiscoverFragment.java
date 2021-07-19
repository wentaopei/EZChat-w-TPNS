package com.example.ezchat.ui.discover;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.ezchat.domain.Comments;
import com.example.ezchat.domain.MySingleton;
import com.example.ezchat.domain.Post;
import com.example.ezchat.domain.User;
import com.example.ezchat.ui.activity.MomentsActivity;
import com.example.ezchat.R;
import com.example.ezchat.ui.activity.NewPostActivity;
import com.example.ezchat.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import app.VolleyController;

/**
 * Page that contains other functions for user such as moments.
 * @author Wentao Pei
 */

public class DiscoverFragment extends Fragment {

    private DiscoverViewModel discoverViewModel;
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

//    public void replyOnPost(String comment,String id,Post post){
//        GsonBuilder gb = new GsonBuilder();
//        gson = gb.create();
//        final HashMap<String, Object> headers = new HashMap<String,Object>();
//        headers.put("id", " ");
//        headers.put("moments_id",post);
//        headers.put("comment",comment.replaceAll("\"",""));
//        headers.put("userId",id.replaceAll("\"",""));
//        JSONArray ja = new JSONArray();
//        ja.put(headers);
//        JSONObject jsonObject = new JSONObject(headers);
//        String uRL = "http://coms-309-ss-3.misc.iastate.edu:8080/moment/";
//        uRL = uRL+post.id;
//        uRL = uRL+"/comment";
//        JsonObjectRequest js = new JsonObjectRequest(Request.Method.POST, uRL, jsonObject,
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
//                        System.out.println("send wrong");
//                    }
//                });
//
//        MySingleton.getInstance(getContext()).addToRequestQueue(js);
//    }
    public void createLayout(View view){
        final LinearLayout ly = (LinearLayout) view.findViewById(R.id.ly_moment);
        for(int i=0;i<posts.size();i++){
            TextView userName = new TextView(getContext());
            TextView text = new TextView(getContext());
            TextView time = new TextView(getContext());
            final TextView peopleLike = new TextView(getContext());
            final TextView peopleDislike = new TextView(getContext());

            final Button like = new Button(getContext());
            final Button dislike = new Button(getContext());

            User temp = searchNameByid(posts.get(i).userid);
            userName.setText(temp.getUsername()+":");
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
                    VolleyController.updateLike(id,tem.userid,tem.text,tem.imageURL,newLike,tem.mdislike,tem.time,getContext());
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
                    VolleyController.updateLike(id,tem.userid,tem.text,tem.imageURL,tem.mlike,newDislike,tem.time,getContext());
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
                final ImageView mImageView = new ImageView(getContext());
                mImageView.setImageBitmap(decodeImage(posts.get(i).imageURL));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(800, 800);
                mImageView.setLayoutParams(layoutParams);
                LinearLayout temLy = new LinearLayout(getContext());
                temLy.addView(mImageView);
                ly.addView(temLy);
                //END Here
                if(posts.get(i).image2!=null){
                    layoutParams.height=400;
                    layoutParams.weight=400;
                    final ImageView mImageView2 = new ImageView(getContext());
                    mImageView2.setImageBitmap(decodeImage(posts.get(i).image2));
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(400, 400);
                    mImageView2.setLayoutParams(layoutParams2);
                    temLy.addView(mImageView2);
                }
            }


            ly.addView(time);

            LinearLayout likeLy = new LinearLayout(getContext());
            likeLy.addView(peopleLike);
            likeLy.addView(peopleDislike);

            ly.addView(likeLy);

            //comments shows here
            final LinearLayout commentLy = new LinearLayout(getContext());
            commentLy.setOrientation(LinearLayout.VERTICAL);
            ly.addView(commentLy);

            final TextView showComment = new TextView(getContext());
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
            LinearLayout likeButtonLy = new LinearLayout(getContext());
            likeButtonLy.addView(like);
            likeButtonLy.addView(dislike);
            ly.addView(likeButtonLy);
            TextView empty = new TextView(getContext());
            ly.addView(empty);

        }
    }
    public User searchNameByid(int id){
        ArrayList<User> temp = LoginActivity.user_list;
        for(int i=0;i<temp.size();i++){
            if(temp.get(i).getUerID()==id){
                return temp.get(i);
            }
        }
        return null;
    }

    public void loadPost(final View view){
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

                        createLayout(view);
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
        VolleyController.sendMomentRequestToServer(getContext(),stringMomentRequest);
    }

    public void loadComment(final LinearLayout commentLy,final Post tem,final TextView showComment){
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
                        final LinearLayout comly = new LinearLayout(getContext());
                        comly.setOrientation(LinearLayout.VERTICAL);
                        for(int i=0;i<comments.size();i++){
                            TextView com = new TextView(getContext());
                            com.setText("   "+comments.get(i).userId+": "+comments.get(i).comment);
                            comly.addView(com);
                        }
                        commentLy.addView(comly);
                        final EditText et = new EditText(getContext());
                        et.setHint("say something to this post...");
                        commentLy.addView(et);

                        Button sendComment = new Button(getContext());
                        sendComment.setText("Reply");
                        sendComment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String content = et.getText().toString();
                                VolleyController.replyOnPost(content,LoginActivity.user_login.userName,tem,getContext());
                                TextView com = new TextView(getContext());
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
        VolleyController.sendCommentRequestToServer(getContext(),stringRequest);
    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        discoverViewModel =
                ViewModelProviders.of(this).get(DiscoverViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_discover, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        discoverViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        loadPost(root);

        FloatingActionButton refesh = (FloatingActionButton) root.findViewById(R.id.floatingActionButton_refesh);
        refesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPost(root);
                LinearLayout ly = (LinearLayout) root.findViewById(R.id.ly_moment);
                ly.removeAllViews();
                createLayout(root);
            }
        });

        FloatingActionButton create = (FloatingActionButton) root.findViewById(R.id.floatingActionButton_NewPost);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), NewPostActivity.class);
                startActivity(i);
            }
        });


        return root;
    }
}