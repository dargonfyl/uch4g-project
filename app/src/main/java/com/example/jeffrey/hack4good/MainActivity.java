package com.example.jeffrey.hack4good;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Goes to the get a random image from r/aww
         */
        final Button rawwButton = findViewById(R.id.button);
        rawwButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent goToRandomImage = new Intent(getApplicationContext(), RandomImageActivity.class);
                startActivity(goToRandomImage);
            }
        });

        final Button pickImageButton = findViewById(R.id.button2);
        pickImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent getImage = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(getImage, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bimage = BitmapFactory.decodeFile(picturePath);
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            bimage.compress(Bitmap.CompressFormat.PNG, 100, bStream);
            byte[] byteArray = bStream.toByteArray();

            Intent goToColoring = new Intent(getApplicationContext(), ColoringActivity.class);
            goToColoring.putExtra("Image", byteArray);
            startActivity(goToColoring);

        }
    }
}