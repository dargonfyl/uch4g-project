package com.example.jeffrey.hack4good;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class RandomImageActivity extends AppCompatActivity {

    private Bitmap getBitmap(VectorDrawable drawable) {
        try {
            Bitmap bitmap;

            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            // Handle the error
            return null;
        }
    }

    private void cycleThroughImages(ImageView seeImage) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletionService<List<Submission>> completionService = new ExecutorCompletionService<List<Submission>>(executorService);

        List<Submission> submissionList = null;

        completionService.submit(new Callable<List<Submission>>() {
            @Override
            public List<Submission> call() throws Exception {
                RestClient restClient = new HttpRestClient();
                restClient.setUserAgent("bot/1.0 by name");

                User user = new User(restClient, "throwawayh4g", "hack4good");
                try {
                    user.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Submissions sumbs = new Submissions(restClient, user);
                return sumbs.ofSubreddit("aww", SubmissionSort.NEW, -1, 100, null, null, true);
            }
        });

        try {
            final Future<List<Submission>> completedFuture = completionService.take();
            submissionList = completedFuture.get();

            int i = 0;
//            for (i = 0; i < 10; i++) {
//                Submission sub1 = submissionList.get(i);
//                String imgURL = sub1.getURL();
//
//                new DownloadImageFromInternet((ImageView) findViewById(R.id.imageCycler))
//                        .execute(imgURL);
//            }

            sendFinalImage(submissionList.get(i + 1), seeImage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void sendFinalImage(Submission sub, ImageView im) {
        ImageView imageView = findViewById(R.id.imageCycler);
        new DownloadImageFromInternet(imageView).execute(sub.getURL());
        Bitmap bitmap = getBitmap((VectorDrawable) imageView.getDrawable());
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] byteArray = bStream.toByteArray();

        Intent goToColoring = new Intent(getApplicationContext(), ColoringActivity.class);
        goToColoring.putExtra("Image", byteArray);
        startActivity(goToColoring);
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        Bitmap res;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
//            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
//                Log.e("Error Message", e.getMessage());
//                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            this.res = result;
            imageView.setImageBitmap(result);
        }

        protected Bitmap getBitmap() {
            return res;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_loader);

        final ImageView seeImage = findViewById(R.id.imageCycler);
        cycleThroughImages(seeImage);
    }
}
