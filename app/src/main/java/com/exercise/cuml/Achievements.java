package com.exercise.cuml;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Achievements extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView medalImageOne,medalImageTwo,medalImageThree;
    Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        //hiding the tool bar for this main activities
        getSupportActionBar().hide();

        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        medalImageOne = findViewById(R.id.medalImageOne);
        medalImageTwo = findViewById(R.id.medalImageTwo);
        medalImageThree = findViewById(R.id.medalImageThree);

        medalImageOne.setAnimation(topAnim);
        medalImageTwo.setAnimation(bottomAnim);
        medalImageThree.setAnimation(bottomAnim);
    }
}