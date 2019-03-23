package com.example.jeffrey.hack4good;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

//import com.github.jreddit.entity.User;
//import com.github.jreddit.utils.restclient.HttpRestClient;
//import com.github.jreddit.utils.restclient.RestClient;

import java.net.URL;

public class RandomImageActivity extends AppCompatActivity {

    private void cycleThroughImages(ImageView seeImage) {
//        RestClient restClient = new HttpRestClient();
//        restClient.setUserAgent("bot/1.0 by name");

//        User user = new User(restClient, "throwawayh4g", "hack4good");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_loader);

        final ImageView seeImage = findViewById(R.id.imageCycler);
        cycleThroughImages(seeImage);
    }
}
