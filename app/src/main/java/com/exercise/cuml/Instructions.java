package com.exercise.cuml;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.URL;

public class Instructions extends AppCompatActivity {

    ProgressBar progressbarSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        //hiding the tool bar for this main activities
        getSupportActionBar().hide();

        // Get the widgets reference from XML layout
        final LinearLayout rl = findViewById(R.id.r1);
        final ImageView iv = findViewById(R.id.iv);
        final ImageView iv1 = findViewById(R.id.iv1);
        progressbarSample = findViewById(R.id.progressbarSample);

        final String imgURL  = "https://darebee.com/images/workouts/death-by-burpees-workout.jpg";
                new DownLoadImageTask(iv).execute(imgURL);
                new DownLoadImageTask(iv1).execute(imgURL);
    }
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
            progressbarSample.setVisibility(View.VISIBLE);
        }
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
            progressbarSample.setVisibility(View.GONE);

        }
    }
}