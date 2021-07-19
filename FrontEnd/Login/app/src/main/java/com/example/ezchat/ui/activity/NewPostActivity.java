package com.example.ezchat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.internal.http.multipart.FilePart;
import com.android.internal.http.multipart.Part;
import com.android.internal.http.multipart.StringPart;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ezchat.R;
import com.example.ezchat.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.ezchat.domain.MultipartRequest;
import com.example.ezchat.domain.MySingleton;

/**
 * This activity allows user post a new share to the server.
 * @author Dengyun Ma
 */
public class NewPostActivity extends AppCompatActivity {
    private Gson gson;

    public Uri selectImageUri;
    public String[] filePathColumn = {MediaStore.Images.Media.DATA};
    public Cursor cursor;
    public int columnIndex;
    public String picturePath;
    public boolean picturePost = false;
    public Bitmap bitmap;


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }

    public void sendPicture(){

        List<Part> partList = new ArrayList<Part>();
//        HashMap<String, Object> headers = new HashMap<String,Object>();
//        headers.put("id",0);
//        headers.put("momentid",99);
//        JSONObject jsonObject = new JSONObject(headers);
//        partList.add(new FilePart("image", (PartSource) jsonObject));
//        partList.add(new StringPart("id", "99".replaceAll("\"","")));
//        partList.add(new StringPart("momentid", "99".replaceAll("\"","")));


        partList.add(new StringPart("image","99".replaceAll("\"","")));
        try {
            File f = new File(picturePath);
            System.out.println(f.canRead());
            partList.add(new FilePart("path", f));
            System.out.println(picturePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(picturePath);
        }
        String url = "http://coms-309-ss-3.misc.iastate.edu:8080/image";

        MultipartRequest profileUpdateRequest = new MultipartRequest(url, partList.toArray(new Part[partList.size()]), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("on response");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MultipartRequest", error.getMessage(), error);
            }
        });
        System.out.println("Request created");
        MySingleton.getInstance(NewPostActivity.this).addToRequestQueue(profileUpdateRequest);
//
//        String url = "http://coms-309-ss-3.misc.iastate.edu:8080/image";
//
//        StringRequest js = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                String result= response;
//                Log.e("Result",response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Error",error.getMessage());
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams(){
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id","99".replaceAll("\"",""));
//                params.put("momentid","99".replaceAll("\"",""));
//                ImageView im = findViewById(R.id.selectedImage);
//                Bitmap bm = ((BitmapDrawable)im.getDrawable()).getBitmap();
//                params.put("path",getStringImage(bm));
//                return params;
//            }
//        };
//
//        HashMap<String, Object> headers = new HashMap<String,Object>();
//        headers.put("id",0);
//        headers.put("momentid",99);
//        ImageView im = findViewById(R.id.selectedImage);
//        Bitmap bm = ((BitmapDrawable)im.getDrawable()).getBitmap();
//        headers.put("path",getStringImage(bm));
//
//        JSONObject jsonObject = new JSONObject(headers);
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
//        MySingleton.getInstance(NewPostActivity.this).addToRequestQueue(js);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        final EditText et = (EditText) findViewById(R.id.editText_postText);

        Button sendPost = (Button)findViewById(R.id.button_sendPost);
        sendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GsonBuilder gb = new GsonBuilder();
                gson = gb.create();

                final ProgressDialog dialog = new ProgressDialog(NewPostActivity.this);
                dialog.setMessage("Sending");
                dialog.setCancelable(false);
                dialog.setInverseBackgroundForced(false);
                dialog.show();

                HashMap<String, Object> headers = new HashMap<String,Object>();
                headers.put("id",null);
                headers.put("userid",LoginActivity.user_login.getUerID());
                headers.put("text",et.getText().toString().replaceAll("\"",""));
                if(picturePost==false) {
                    headers.put("imageURL", null);
                }
                else{
                    headers.put("imageURL",getStringImage(bitmap));
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
                                dialog.hide();
//                                Toast.makeText(getApplicationContext(), "Post successfully", Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("TAG",error.getMessage(),error);
                                dialog.hide();
//                                Toast.makeText(getApplicationContext(), "Post failed", Toast.LENGTH_LONG).show();

                            }
                        });

                MySingleton.getInstance(NewPostActivity.this).addToRequestQueue(js);


                et.setText("");

                Toast.makeText(getApplicationContext(), "Post successfully", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        Button selectImage = findViewById(R.id.button_chooseImage);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent1,1);
            }
        });



    }
    private void selectPic(Intent intent) throws FileNotFoundException {
        selectImageUri = intent.getData();
        cursor = getContentResolver().query(selectImageUri,filePathColumn,null,null,null);
        cursor.moveToFirst();
        columnIndex=cursor.getColumnIndex(filePathColumn[0]);
        picturePath=cursor.getString(columnIndex);
        System.out.println(picturePath);
        cursor.close();
        ImageView imageView = (ImageView)findViewById(R.id.selectedImage);
        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectImageUri));
        imageView.setImageBitmap(bitmap);
        picturePost = true;
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
}
