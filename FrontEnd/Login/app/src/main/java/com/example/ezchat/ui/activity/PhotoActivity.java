package com.example.ezchat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.example.ezchat.R;

/**
 * Test class for photos
 * @author Wentao Pei
 */

public class PhotoActivity extends AppCompatActivity
{
    public final int CODE_SELECT_IMAGE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Intent albumIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(albumIntent, CODE_SELECT_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
                    selectPic(data);
                }
                break;
        }
    }

    private void selectPic(Intent intent) {
        Uri selectImageUri = intent.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectImageUri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        System.out.println(picturePath);
        cursor.close();
        ImageView imageView = (ImageView) findViewById(R.id.imageViewTest);
        imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
    }

}
