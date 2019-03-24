package com.example.jeffrey.hack4good;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ColoringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coloring);


        Bitmap bmp;
        byte[] byteArray = getIntent().getByteArrayExtra("Image");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        final ImageView view = findViewById(R.id.imageLocation);
        view.setImageBitmap(bmp);
    }
}
