package com.exercise.cuml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    //variables
    Animation topAnim, bottomAnim;
    TextView splashTextBig,splashTextSmall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        //hiding the tool bar for this main activities
        getSupportActionBar().hide();

        //animation declaration
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        splashTextBig = findViewById(R.id.splashTextBig);
        splashTextSmall = findViewById(R.id.splashTextSmall);

        splashTextBig.setAnimation(topAnim);
        splashTextSmall.setAnimation(bottomAnim);

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(FirebaseAuth.getInstance().getCurrentUser() != null){
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    startActivity(new Intent(SplashScreen.this, LoginPage.class));
                    finish();
                }
            }
        }, secondsDelayed * 1200);
    }
}