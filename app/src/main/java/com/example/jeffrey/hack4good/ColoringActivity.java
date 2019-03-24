package com.example.jeffrey.hack4good;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class ColoringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coloring);


        Bitmap bmp;
        byte[] byteArray = getIntent().getByteArrayExtra("Image");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        ImageView view = findViewById(R.id.imageLocation);
        view.setImageBitmap(bmp);

        final cv scratch = new cv(bmp);
        final ImageView scratched = findViewById(R.id.scratchedImage);
        scratched.setImageBitmap(scratch.convertScratched());

        scratched.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    scratch.scratch((int) event.getX(), (int) event.getY());
                    scratched.setImageBitmap(scratch.convertScratched());
                    return true;
                }
                return false;
            }
        });
    }

    protected void onClick() {

    }
}
