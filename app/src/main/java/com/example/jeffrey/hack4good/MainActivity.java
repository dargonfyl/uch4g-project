package com.example.jeffrey.hack4good;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    }
}