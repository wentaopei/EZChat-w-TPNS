package app;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.ezchat.domain.Post;
import com.example.ezchat.ui.friends.FriendsFragment;
import com.example.ezchat.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.ezchat.domain.MySingleton;
import com.example.ezchat.domain.User;
import com.example.ezchat.domain.FriendsList;

public class VolleyController {
    private static Gson gson;

    public static void sendLoginRequestToServer(Context context)
    {
        if(!checkNetworkAvailable(context)){
            Toast.makeText(context, "Connection Failed, please check your internet." ,Toast.LENGTH_LONG).show();
            return;
        }
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

        StringRequest stringRequest = new StringRequest("http://coms-309-ss-3.misc.iastate.edu:8080/users",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        JsonObject json =  null;
                        Log.d("TAG", response);
                        LoginActivity.user_list = gson.fromJson(response, new TypeToken<ArrayList<User>>(){}.getType());
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

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void updateTheFriendStatus(FriendsList fl,Context context){
        if(!checkNetworkAvailable(context)){
            Toast.makeText(context, "Connection Failed, please check your internet." ,Toast.LENGTH_LONG).show();
            return;
        }
        User temp_user = searchNameByid(fl.friendsId);
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

        HashMap<String, Object> headers = new HashMap<String,Object>();
        String id_str = String.valueOf(fl.id);
        headers.put("id",id_str.replaceAll("\"",""));
        headers.put("status","1".replaceAll("\"",""));
        headers.put("approved","1".replaceAll("\"",""));
        headers.put("user",fl.user_id);
        headers.put("friendId",String.valueOf(temp_user.getUerID()).replaceAll("\"",""));


        JSONObject jsonObject = new JSONObject(headers);

        String url = "http://coms-309-ss-3.misc.iastate.edu:8080/users/";
        url = url+String.valueOf(fl.user_id.getUerID());
        url = url+"/friends";

        JsonObjectRequest js = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
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
                    }
                });

        MySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(js);
    }

    public static void sendFriendRequest(int id,Context context){
        if(!checkNetworkAvailable(context)){
            Toast.makeText(context, "Connection Failed, please check your internet." ,Toast.LENGTH_LONG).show();
            return;
        }
        User temp_user = searchNameByid(id);
        User login = LoginActivity.user_login;
        //friendsList newFriend = new friendsList();

        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

        HashMap<String, Object> headers = new HashMap<String,Object>();
        headers.put("id","99999");
        headers.put("status","1".replaceAll("\"",""));
        headers.put("approved","1".replaceAll("\"",""));
        headers.put("user",temp_user);
        headers.put("friendId",String.valueOf(login.getUerID()).replaceAll("\"",""));


        JSONObject jsonObject = new JSONObject(headers);

        String url = "http://coms-309-ss-3.misc.iastate.edu:8080/users/";
        url = url+String.valueOf(temp_user.getUerID());
        url = url+"/friends";

        JsonObjectRequest js = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
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
                    }
                });

        MySingleton.getInstance(context).addToRequestQueue(js);
    }

    public static void sendText(final int id1, final int id2, final String txt,final String picture,Context context){
        if(!checkNetworkAvailable(context)){
            Toast.makeText(context, "Connection Failed, please check your internet." ,Toast.LENGTH_LONG).show();
            return;
        }

        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();



        final HashMap<String, String> headers = new HashMap<String,String>();

        headers.put("id", " ");
        String id1_str = String.valueOf(id1);
        headers.put("sender",id1_str.replaceAll("\"",""));
        String id2_str = String.valueOf(id2);
        headers.put("receiver",id2_str.replaceAll("\"",""));
        headers.put("text",txt.replaceAll("\"",""));
        headers.put("time","  ".replaceAll("\"",""));
        headers.put("ispicture",picture.replaceAll("\"",""));

        System.out.println(headers.toString());


        JSONArray ja = new JSONArray();
        ja.put(headers);
        System.out.println(ja);
        System.out.println(ja.toString());


        JSONObject jsonObject = new JSONObject(headers);

        String url = "http://coms-309-ss-3.misc.iastate.edu:8080/history/add/";

        System.out.println(url);

        JsonObjectRequest js = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
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

        MySingleton.getInstance(context).addToRequestQueue(js);

    }

    public static void sendChatRequestToServer(int id1, int id2,Context context,StringRequest request)
    {
        if(!checkNetworkAvailable(context)){
            Toast.makeText(context, "Connection Failed, please check your internet." ,Toast.LENGTH_LONG).show();
            return;
        }
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public static void sendMomentRequestToServer(Context context,StringRequest request)
    {
        if(!checkNetworkAvailable(context)){
            Toast.makeText(context, "Connection Failed, please check your internet." ,Toast.LENGTH_LONG).show();
            return;
        }
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public static void updateLike(int id,int iuserid,String itext,String iurl,int imlike,int imdislike,String itime,Context context){
        if(!checkNetworkAvailable(context)){
            Toast.makeText(context, "Connection Failed, please check your internet." ,Toast.LENGTH_LONG).show();
            return;
        }

        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

        HashMap<String, Object> headers = new HashMap<String,Object>();
        headers.put("id",id);
        headers.put("userid",iuserid);
        headers.put("text",itext.replaceAll("\"",""));
        headers.put("imageURL",iurl);
        headers.put("mlike",imlike);
        headers.put("mdislike",imdislike);
        headers.put("time",itime.replaceAll("\"",""));


        JSONObject jsonObject = new JSONObject(headers);

        String url = "http://coms-309-ss-3.misc.iastate.edu:8080/moments/add";

        JsonObjectRequest js = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
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
                    }
                });

        MySingleton.getInstance(context).addToRequestQueue(js);
    }


    public static void sendCommentRequestToServer(Context context,StringRequest request)
    {
        if(!checkNetworkAvailable(context)){
            Toast.makeText(context, "Connection Failed, please check your internet." ,Toast.LENGTH_LONG).show();
            return;
        }
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public static void sendPostRequest(Context context, String image, boolean isPicture, EditText et,String image2){
        if(!checkNetworkAvailable(context)){
            Toast.makeText(context, "Connection Failed, please check your internet." ,Toast.LENGTH_LONG).show();
            return;
        }
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

        final ProgressDialog dialog = new ProgressDialog(context);

        HashMap<String, Object> headers = new HashMap<String,Object>();
        headers.put("id",null);
        headers.put("userid",LoginActivity.user_login.getUerID());
        headers.put("text",et.getText().toString().replaceAll("\"",""));
        if(isPicture==false) {
            headers.put("imageURL", null);
            headers.put("image2",null);
        }
        else{
            headers.put("imageURL",image);
            if(image2==null){
                headers.put("image2",null);
            }
            else{
                headers.put("image2",image2);
            }
        }
        headers.put("mlike",0);
        headers.put("mdislike",0);
        headers.put("time",null);


        JSONObject jsonObject = new JSONObject(headers);

        String url = "http://coms-309-ss-3.misc.iastate.edu:8080/moments/add";

        JsonObjectRequest js = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
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

                    }
                });

        MySingleton.getInstance(context).addToRequestQueue(js);
    }

    public static void sendRegisterRequest(Context context, EditText usName, EditText pw, EditText email, Spinner gender,String type_int){
        if(!checkNetworkAvailable(context)){
            Toast.makeText(context, "Connection Failed, please check your internet." ,Toast.LENGTH_LONG).show();
            return;
        }
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("id","99999");
        headers.put("name",usName.getText().toString().replaceAll("\"",""));
        headers.put("passward",pw.getText().toString().replaceAll("\"",""));
        headers.put("email",email.getText().toString().replaceAll("\"",""));
        headers.put("gender",gender.getSelectedItem().toString().replaceAll("\"",""));
        headers.put("type",type_int.replaceAll("\"",""));


        JSONObject jsonObject = new JSONObject(headers);

        String url = "http://coms-309-ss-3.misc.iastate.edu:8080/users";

        JsonObjectRequest js = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
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
                    }
                });

        MySingleton.getInstance(context).addToRequestQueue(js);
    }

    public static void replyOnPost(String comment, String id, Post post,Context context){
        if(!checkNetworkAvailable(context)){
            Toast.makeText(context, "Connection Failed, please check your internet." ,Toast.LENGTH_LONG).show();
            return;
        }
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

        MySingleton.getInstance(context).addToRequestQueue(js);
    }

    public static void sendListRequestToServer(Context context,String uRL)
    {
        if(!checkNetworkAvailable(context)){
            Toast.makeText(context, "Connection Failed, please check your internet." ,Toast.LENGTH_LONG).show();
            return;
        }
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

        StringRequest stringRequest = new StringRequest(uRL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        JsonObject json =  null;
                        Log.d("TAG", response);
                        ArrayList<FriendsList> temp = gson.fromJson(response, new TypeToken<ArrayList<FriendsList>>(){}.getType());
                        FriendsFragment.friends_list = (ArrayList<FriendsList>) temp.clone();
                        LoginActivity.friends = FriendsFragment.friends_list;
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
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

        FriendsFragment.connected = true;
    }

    public static boolean checkNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return true;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }




    public static User searchNameByid(int id){
        ArrayList<User> temp = LoginActivity.user_list;
        for(int i=0;i<temp.size();i++){
            if(temp.get(i).getUerID()==id){
                return temp.get(i);
            }
        }
        return null;
    }
}
