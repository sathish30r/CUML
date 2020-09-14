package com.exercise.cuml;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements PopUpDialog.DialogListener {

    String userId,profile = "profile",
            editName,editHeight,
            editWeight,editAge,
            editGender,editCountryCode;

    // to get the dashboard count and add it
    String coreCount="coreCount",upperCount="upperCount";
    String middleCount="middleCount",lowerCount="lowerCount";
    String userDate = "userDate";
    // to get the dashboard count and add it
    String coreCountAdd="0",upperCountAdd="0",middleCountAdd="0",lowerCountAdd="0";
    String coreDataFetch,upperDataFetch,middleDataFetch,lowerDataFetch;
    String getButtonPressNo;
    int workoutIconChange = 2;

    public DatabaseReference databasecuml,databasecumlDataFetch,databaseRefGeoFire;

    //prograss bar coding values
    ProgressBar determinateBar,determinateBar1,determinateBar2,determinateBar3;
    ImageView coreIconChange,upperIconChange,middleIconChange,lowerIconChange;
    ImageView musclePushups,muscleSitups,muscleSquats,muscleBackground;
    TextView MaxValue,MaxValue1,MaxValue2,MaxValue3;
    TextView userLevelCore,userLevelUpper,userLevelMiddle,userLevelLower;
    TextView textViewMonthCountCore,textViewMonthCountUpper,
            textViewMonthCountMiddle,textViewMonthCountLower;
    int coreTemp,upperTemp,middleTemp,lowerTemp;
    int core30Count,upper30Count,middle30Count,lower30Count;
    int valueToSetCore,valueToSetUpper,valueToSetMiddle,valueToSetLower;
    int beginner=45,intermediate=70,advance=100;
    String defaultValue = "0";
    String userLevelName;
    private long backPressedTime;
    private Toast backToast;

    ProgressDialog progressDialog;

    //date as unique id for users particular date
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
    String strDate = formatter.format(date);

    @Override
    public void applyCounts(String count,String myDataFromActivity ) {
        if (myDataFromActivity == "1") {
            if(isInternetOn() == true){
            //adding popup value and firebase fetched values
            coreCountAdd=Integer.toString(Integer.parseInt(count) + Integer.parseInt(coreDataFetch));
            Map<String,Object> updates1 = new HashMap<String,Object>();
            updates1.put("coreCount",coreCountAdd);
            databasecuml.child(userId).child(strDate).updateChildren(updates1);
            /*databasecuml.child(userId).child(strDate).child(coreCount).setValue(coreCountAdd);*/
            //set added values in progress bar
            determinateBar.setProgress(Integer.parseInt(coreCountAdd));}
            else
                {
                    Toast.makeText(this, " Internet is Not Connected ", Toast.LENGTH_LONG).show();
                }
        }

        else if(myDataFromActivity == "2"){
            if(isInternetOn() == true){
            upperCountAdd=Integer.toString(Integer.parseInt(count)+Integer.parseInt(upperDataFetch));
            Map<String,Object> updates2 = new HashMap<String,Object>();
            updates2.put("upperCount",upperCountAdd);
            databasecuml.child(userId).child(strDate).updateChildren(updates2);
            /*databasecuml.child(userId).child(strDate).child(upperCount).setValue(upperCountAdd);*/
            determinateBar1.setProgress(Integer.parseInt(upperCountAdd));}
            else {
                Toast.makeText(this, " Internet is Not Connected ", Toast.LENGTH_LONG).show();
            }
        }

        else if(myDataFromActivity == "3") {
            if (isInternetOn() == true) {
                middleCountAdd = Integer.toString(Integer.parseInt(count) + Integer.parseInt(middleDataFetch));
                Map<String, Object> updates3 = new HashMap<String, Object>();
                updates3.put("middleCount", middleCountAdd);
                databasecuml.child(userId).child(strDate).updateChildren(updates3);
                /*databasecuml.child(userId).child(strDate).child(middleCount).setValue(middleCountAdd);*/
                determinateBar2.setProgress(Integer.parseInt(middleCountAdd));
            } else {
                Toast.makeText(this, " Internet is Not Connected ", Toast.LENGTH_LONG).show();
            }
        }

        else if(myDataFromActivity == "4") {
            if (isInternetOn() == true) {
                lowerCountAdd = Integer.toString(Integer.parseInt(count) + Integer.parseInt(lowerDataFetch));
                Map<String, Object> updates4 = new HashMap<String, Object>();
                updates4.put("lowerCount", lowerCountAdd);
                databasecuml.child(userId).child(strDate).updateChildren(updates4);
                /*databasecuml.child(userId).child(strDate).child(lowerCount).setValue(lowerCountAdd);*/
                determinateBar3.setProgress(Integer.parseInt(lowerCountAdd));
            } else {
                Toast.makeText(this, " Internet is Not Connected ", Toast.LENGTH_LONG).show();
            }
        }
        muscleColorFinder();

        todayPendingExerciseCount();
        //today pending count for each exercise
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //hiding the tool bar for this main activities
        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading is....");
        progressDialog.show();


        //card view click to pop up window
        CardView cardView1 = findViewById(R.id.linearLayoutMain2);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
            private void openDialog() {
                //sending the value and getting it to find the button pressed
                getButtonPressNo = "1";
                PopUpDialog popUpDialog = new PopUpDialog();
                popUpDialog.show(getSupportFragmentManager(),"count info");
            }
        });
        CardView cardView2 = findViewById(R.id.linearLayoutMain3);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {openDialog(); }
            private void openDialog() {
                //sending the value and getting it to find the button pressed
                getButtonPressNo = "2";
                PopUpDialog popUpDialog = new PopUpDialog();
                popUpDialog.show(getSupportFragmentManager(),"count info");
            }
        });
        CardView cardView3 = findViewById(R.id.linearLayoutMain4);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {openDialog(); }
            private void openDialog() {
                //sending the value and getting it to find the button pressed
                getButtonPressNo = "3";
                PopUpDialog popUpDialog = new PopUpDialog();
                popUpDialog.show(getSupportFragmentManager(),"count info");
            }
        });
        CardView cardView4 = findViewById(R.id.linearLayoutMain5);
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {openDialog(); }
            private void openDialog() {
                //sending the value and getting it to find the button pressed
                getButtonPressNo = "4";
                PopUpDialog popUpDialog = new PopUpDialog();
                popUpDialog.show(getSupportFragmentManager(),"count info");
            }
        });

        // database reference
        databasecuml = FirebaseDatabase.getInstance().getReference("cuml");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getPhoneNumber();//get phone no from firebase and set it as user id
        
        //database data retrieve
        databasecumlDataFetch = FirebaseDatabase.getInstance().getReference("cuml").child(userId).child(strDate);
        databasecumlDataFetch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    //database data retrieve - if data not available, insert default value 0
                    databasecuml.child(userId).child(strDate).child(coreCount).setValue(defaultValue);
                    databasecuml.child(userId).child(strDate).child(upperCount).setValue(defaultValue);
                    databasecuml.child(userId).child(strDate).child(middleCount).setValue(defaultValue);
                    databasecuml.child(userId).child(strDate).child(lowerCount).setValue(defaultValue);
                    databasecuml.child(userId).child(strDate).child(userDate).setValue(strDate);

                    //workout icon will change every day
                    if(workoutIconChange == 1)
                    {
                        workoutIconChange = 2;
                    }
                    else if(workoutIconChange == 2)
                    {
                        workoutIconChange = 1;
                    }
                }
                else {
                    updateDashboardGraphAndCount();
                }
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //progress bar (Circle) for core/upper/middle/lower exercise coding here


        //menu button code here
        Button buttonMenu = (Button)findViewById(R.id.buttonMenu);
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Menu.class);
                startActivity(intent);
            }
        });

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "BzlJ_xDzmdg";
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        //workout icon will change every day
        if(workoutIconChange == 1)
        {
            coreIconChange = findViewById(R.id.coreIconChange);
            coreIconChange.setBackgroundResource(R.drawable.crossfit_burpee);
            coreIconChange.setBackgroundTintList(ContextCompat
                    .getColorStateList(getApplicationContext(), R.color.md_grey_400));
            upperIconChange = findViewById(R.id.upperIconChange);
            upperIconChange.setBackgroundResource(R.drawable.crossfit_pullups);
            upperIconChange.setBackgroundTintList(ContextCompat
                    .getColorStateList(getApplicationContext(), R.color.md_grey_400));
            middleIconChange = findViewById(R.id.middleIconChange);
            middleIconChange.setBackgroundResource(R.drawable.crossfit_plank);
            middleIconChange.setBackgroundTintList(ContextCompat
                    .getColorStateList(getApplicationContext(), R.color.md_grey_400));
            lowerIconChange = findViewById(R.id.lowerIconChange);
            lowerIconChange.setBackgroundResource(R.drawable.crossfit_lunges);
            lowerIconChange.setBackgroundTintList(ContextCompat
                    .getColorStateList(getApplicationContext(), R.color.md_grey_400));
        }
        else if(workoutIconChange == 2)
        {
            coreIconChange = findViewById(R.id.coreIconChange);
            coreIconChange.setBackgroundResource(R.drawable.crossfit_jump);
            coreIconChange.setBackgroundTintList(ContextCompat
                    .getColorStateList(getApplicationContext(), R.color.md_grey_400));
            upperIconChange = findViewById(R.id.upperIconChange);
            upperIconChange.setBackgroundResource(R.drawable.crossfit_pushups);
            upperIconChange.setBackgroundTintList(ContextCompat
                    .getColorStateList(getApplicationContext(), R.color.md_grey_400));
            middleIconChange = findViewById(R.id.middleIconChange);
            middleIconChange.setBackgroundResource(R.drawable.crossfit_situps);
            middleIconChange.setBackgroundTintList(ContextCompat
                    .getColorStateList(getApplicationContext(), R.color.md_grey_400));
            lowerIconChange = findViewById(R.id.lowerIconChange);
            lowerIconChange.setBackgroundResource(R.drawable.crossfit_squat);
            lowerIconChange.setBackgroundTintList(ContextCompat
                    .getColorStateList(getApplicationContext(), R.color.md_grey_400));
        }

        profileUpdate();
        profileDatafetchWarning();
        setDailyExerciseCountAsPerUser();
    }

    private void todayPendingExerciseCount() {
        userLevelCore = (TextView)findViewById(R.id.userLevelCore);
        userLevelUpper = (TextView)findViewById(R.id.userLevelUpper);
        userLevelMiddle = (TextView)findViewById(R.id.userLevelMiddle);
        userLevelLower = (TextView)findViewById(R.id.userLevelLower);
        String todoCoreToday = Integer.toString(valueToSetCore - Integer.parseInt(coreCountAdd));
        if(Integer.parseInt(todoCoreToday)<0){todoCoreToday = "0";}
        String todoUpperToday = Integer.toString(valueToSetUpper - Integer.parseInt(upperCountAdd));
        if(Integer.parseInt(todoUpperToday)<0){todoUpperToday = "0";}
        String todoMiddleToday = Integer.toString(valueToSetMiddle - Integer.parseInt(middleCountAdd));
        if(Integer.parseInt(todoMiddleToday)<0){todoMiddleToday = "0";}
        String todoLowerToday = Integer.toString(valueToSetLower - Integer.parseInt(lowerCountAdd));
        if(Integer.parseInt(todoLowerToday)<0){todoLowerToday = "0";}
        userLevelCore.setText(todoCoreToday);
        userLevelUpper.setText(todoUpperToday);
        userLevelMiddle.setText(todoMiddleToday);
        userLevelLower.setText(todoLowerToday);
    }

    private void todayPendingExerciseCountAtAppStart() {
        userLevelCore = (TextView)findViewById(R.id.userLevelCore);
        userLevelUpper = (TextView)findViewById(R.id.userLevelUpper);
        userLevelMiddle = (TextView)findViewById(R.id.userLevelMiddle);
        userLevelLower = (TextView)findViewById(R.id.userLevelLower);
        String todoCoreToday = Integer.toString(valueToSetCore - Integer.parseInt(coreDataFetch));
        if(Integer.parseInt(todoCoreToday)<0){todoCoreToday = "0";}
        String todoUpperToday = Integer.toString(valueToSetUpper - Integer.parseInt(upperDataFetch));
        if(Integer.parseInt(todoUpperToday)<0){todoUpperToday = "0";}
        String todoMiddleToday = Integer.toString(valueToSetMiddle - Integer.parseInt(middleDataFetch));
        if(Integer.parseInt(todoMiddleToday)<0){todoMiddleToday = "0";}
        String todoLowerToday = Integer.toString(valueToSetLower - Integer.parseInt(lowerDataFetch));
        if(Integer.parseInt(todoLowerToday)<0){todoLowerToday = "0";}
        userLevelCore.setText(todoCoreToday);
        userLevelUpper.setText(todoUpperToday);
        userLevelMiddle.setText(todoMiddleToday);
        userLevelLower.setText(todoLowerToday);
    }

    private void setDailyExerciseCountAsPerUser() {
        determinateBar = (ProgressBar)findViewById(R.id.determinateBar);
        determinateBar1 = (ProgressBar)findViewById(R.id.determinateBar1);
        determinateBar2 = (ProgressBar)findViewById(R.id.determinateBar2);
        determinateBar3 = (ProgressBar)findViewById(R.id.determinateBar3);

            //sum of the all exercise count from the beginning to till end
            //database sum of particular column from the beginning to till the end

            databasecuml.child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        coreTemp = 0;
                        upperTemp = 0;
                        middleTemp = 0;
                        lowerTemp = 0;
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Map<String, String> map = (Map<String, String>) ds.getValue();
                            String coreCount = Objects.requireNonNull(map).get("coreCount");
                            String upperCount = Objects.requireNonNull(map).get("upperCount");
                            String middleCount = Objects.requireNonNull(map).get("middleCount");
                            String lowerCount = Objects.requireNonNull(map).get("lowerCount");
                            if(coreCount != null && upperCount != null &&
                                    middleCount != null && lowerCount != null) {
                                int cValue = Integer.parseInt(Objects.requireNonNull(coreCount));
                                int uValue = Integer.parseInt(Objects.requireNonNull(upperCount));
                                int mValue = Integer.parseInt(Objects.requireNonNull(middleCount));
                                int lValue = Integer.parseInt(Objects.requireNonNull(lowerCount));
                                coreTemp = coreTemp + cValue;
                                upperTemp = upperTemp + uValue;
                                middleTemp = middleTemp + mValue;
                                lowerTemp = lowerTemp + lValue;
                            }
                        }
                    }
                    else{ }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        //set the value according to the user count - beginner/intermediate/advance
        if(coreTemp >= 0 && coreTemp <500) { valueToSetCore = beginner;}
        else if(coreTemp >= 500 && coreTemp <1000) { valueToSetCore = intermediate;}
        else if(coreTemp >= 1000) { valueToSetCore = advance;}
        determinateBar.setMax(valueToSetCore);

        if(upperTemp >= 0 && upperTemp <500) { valueToSetUpper = beginner;}
        else if(upperTemp >= 500 && upperTemp <1000) { valueToSetUpper = intermediate;}
        else if(upperTemp >= 1000) { valueToSetUpper = advance;}
        determinateBar1.setMax(valueToSetUpper);

        if(middleTemp >= 0 && middleTemp <500) { valueToSetMiddle = beginner;}
        else if(middleTemp >= 500 && middleTemp <1000) { valueToSetMiddle = intermediate;}
        else if(middleTemp >= 1000) { valueToSetMiddle = advance;}
        determinateBar2.setMax(valueToSetMiddle);

        if(lowerTemp >= 0 && lowerTemp <500) { valueToSetLower = beginner;}
        else if(lowerTemp >= 500 && lowerTemp <1000) { valueToSetLower = intermediate;}
        else if(lowerTemp >= 1000) { valueToSetLower = advance;}
        determinateBar3.setMax(valueToSetLower);

        if(valueToSetCore==45||valueToSetUpper==45||valueToSetMiddle==45||valueToSetLower==45)
        {userLevelName ="Amateur";}
        if(valueToSetCore>70&&valueToSetUpper>70&&valueToSetMiddle>70&&valueToSetLower>70)
        {userLevelName = "Pro";}
        if((valueToSetCore>45&&valueToSetCore<100)
                ||(valueToSetCore>45&&valueToSetCore<100)
                ||(valueToSetCore>45&&valueToSetCore<100)
                ||(valueToSetCore>45&&valueToSetCore<100))
        {userLevelName = "Intermediate";}

    }

    public void updateDashboardGraphAndCount(){
        databasecumlDataFetch = FirebaseDatabase.getInstance().getReference("cuml").child(userId).child(strDate);
        databasecumlDataFetch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

                    MaxValue  = (TextView)findViewById(R.id.MaxValue);
                    MaxValue1  = (TextView)findViewById(R.id.MaxValue1);
                    MaxValue2  = (TextView)findViewById(R.id.MaxValue2);
                    MaxValue3  = (TextView)findViewById(R.id.MaxValue3);

                    //database data retrieve - if available, set in progress bar and count
                    coreDataFetch = Objects.requireNonNull(dataSnapshot.child("coreCount").getValue(String.class));
                    upperDataFetch = Objects.requireNonNull(dataSnapshot.child("upperCount").getValue(String.class));
                    middleDataFetch = Objects.requireNonNull(dataSnapshot.child("middleCount").getValue(String.class));
                    lowerDataFetch = Objects.requireNonNull(dataSnapshot.child("lowerCount").getValue(String.class));

                    determinateBar.setProgress(Integer.parseInt(coreDataFetch));
                    determinateBar1.setProgress(Integer.parseInt(upperDataFetch));
                    determinateBar2.setProgress(Integer.parseInt(middleDataFetch));
                    determinateBar3.setProgress(Integer.parseInt(lowerDataFetch));

                    MaxValue.setText(coreDataFetch);
                    MaxValue1.setText(upperDataFetch);
                    MaxValue2.setText(middleDataFetch);
                    MaxValue3.setText(lowerDataFetch);

                    /*monthGraphCore();
                    monthGraphUpper();
                    monthGraphMiddle();
                    monthGraphLower();*/
                    muscleColorFinderAtAppStarting();
                    todayPendingExerciseCountAtAppStart();
                }
                else
                {
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void muscleColorFinder(){
        //muscle map
        muscleBackground = findViewById(R.id.muscleBackground);
        musclePushups = findViewById(R.id.musclePushups);
        muscleSitups = findViewById(R.id.muscleSitups);
        muscleSquats = findViewById(R.id.muscleSquats);

        if((Integer.parseInt(upperCountAdd)) == 0
                && (Integer.parseInt(middleCountAdd)) ==0
                && (Integer.parseInt(lowerCountAdd)) ==0)
        {
            musclePushups.setVisibility(View.VISIBLE);
            muscleSitups.setVisibility(View.VISIBLE);
            muscleSquats.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(musclePushups.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_grey_700));
            DrawableCompat.setTint(muscleSitups.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_grey_700));
            DrawableCompat.setTint(muscleSquats.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_grey_700));
        }

        /*if ((Integer.parseInt(coreDataFetch)) > 10 && (Integer.parseInt(coreDataFetch)) < 30) {
            muscleBackground.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleBackground.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_light_blue_100));
        }
        else if ((Integer.parseInt(coreDataFetch)) >= 30 && (Integer.parseInt(coreDataFetch)) < 80) {
            muscleBackground.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleBackground.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_light_blue_600));
        }
        else if ((Integer.parseInt(coreDataFetch)) >= 80) {
            muscleBackground.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleBackground.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_blue_900));
        }*/

        int tenPercentUpper = (int) (valueToSetUpper*0.10);
        int thirtyPercentUpper = (int) (valueToSetUpper*0.30);
        int eightyPercentUpper = (int) (valueToSetUpper*0.80);
        int tenPercentMiddle = (int) (valueToSetMiddle*0.10);
        int thirtyPercentMiddle = (int) (valueToSetMiddle*0.30);
        int eightyPercentMiddle = (int) (valueToSetMiddle*0.80);
        int tenPercentLower = (int) (valueToSetLower*0.10);
        int thirtyPercentLower = (int) (valueToSetLower*0.30);
        int eightyPercentLower = (int) (valueToSetLower*0.80);

        if((Integer.parseInt(upperCountAdd))>tenPercentUpper && (Integer.parseInt(upperCountAdd))<thirtyPercentUpper){
            musclePushups.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(musclePushups.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_200));
        }
        else if ((Integer.parseInt(upperCountAdd))>=thirtyPercentUpper && (Integer.parseInt(upperCountAdd))<eightyPercentUpper){
            musclePushups.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(musclePushups.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_500));
        }
        else if ((Integer.parseInt(upperCountAdd))>=eightyPercentUpper){
            musclePushups.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(musclePushups.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_900));
        }

        if((Integer.parseInt(middleCountAdd))>tenPercentMiddle && (Integer.parseInt(middleCountAdd))<thirtyPercentMiddle){
            muscleSitups.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleSitups.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_200));
        }
        else if ((Integer.parseInt(middleCountAdd))>=thirtyPercentMiddle && (Integer.parseInt(middleCountAdd))<eightyPercentMiddle){
            muscleSitups.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleSitups.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_500));
        }
        else if ((Integer.parseInt(middleCountAdd))>=eightyPercentMiddle){
            muscleSitups.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleSitups.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_blue_900));
        }

        if((Integer.parseInt(lowerCountAdd))>tenPercentLower && (Integer.parseInt(lowerCountAdd))<thirtyPercentLower){
            muscleSquats.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleSquats.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_200));
        }
        else if ((Integer.parseInt(lowerCountAdd))>=thirtyPercentLower && (Integer.parseInt(lowerCountAdd))<eightyPercentLower){
            muscleSquats.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleSquats.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_500));
        }
        else if ((Integer.parseInt(lowerCountAdd))>=eightyPercentLower){
            muscleSquats.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleSquats.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_900));
        }
    }

    public void muscleColorFinderAtAppStarting() {
        //muscle map
        muscleBackground = (ImageView) findViewById(R.id.muscleBackground);
        musclePushups = (ImageView) findViewById(R.id.musclePushups);
        muscleSitups = (ImageView) findViewById(R.id.muscleSitups);
        muscleSquats = (ImageView) findViewById(R.id.muscleSquats);

        if ((Integer.parseInt(upperDataFetch)) == 0
                && (Integer.parseInt(middleDataFetch)) == 0
                && (Integer.parseInt(lowerDataFetch)) == 0) {
            musclePushups.setVisibility(View.VISIBLE);
            muscleSitups.setVisibility(View.VISIBLE);
            muscleSquats.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(musclePushups.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_grey_700));
            DrawableCompat.setTint(muscleSitups.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_grey_700));
            DrawableCompat.setTint(muscleSquats.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_grey_700));
        }

        /*if ((Integer.parseInt(coreDataFetch)) > 10 && (Integer.parseInt(coreDataFetch)) < 30) {
            muscleBackground.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleBackground.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_light_blue_100));
        }
        else if ((Integer.parseInt(coreDataFetch)) >= 30 && (Integer.parseInt(coreDataFetch)) < 80) {
            muscleBackground.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleBackground.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_light_blue_600));
        }
        else if ((Integer.parseInt(coreDataFetch)) >= 80) {
            muscleBackground.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleBackground.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_blue_900));
        }*/

        int tenPercentUpper = (int) (valueToSetUpper*0.10);
        int thirtyPercentUpper = (int) (valueToSetUpper*0.30);
        int eightyPercentUpper = (int) (valueToSetUpper*0.80);
        int tenPercentMiddle = (int) (valueToSetMiddle*0.10);
        int thirtyPercentMiddle = (int) (valueToSetMiddle*0.30);
        int eightyPercentMiddle = (int) (valueToSetMiddle*0.80);
        int tenPercentLower = (int) (valueToSetLower*0.10);
        int thirtyPercentLower = (int) (valueToSetLower*0.30);
        int eightyPercentLower = (int) (valueToSetLower*0.80);

        if ((Integer.parseInt(upperDataFetch)) > tenPercentUpper && (Integer.parseInt(upperDataFetch)) < thirtyPercentUpper) {
            musclePushups.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(musclePushups.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_200));
        }
        else if ((Integer.parseInt(upperDataFetch)) >= thirtyPercentUpper && (Integer.parseInt(upperDataFetch)) < eightyPercentUpper) {
            musclePushups.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(musclePushups.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_500));
        }
        else if ((Integer.parseInt(upperDataFetch)) >= eightyPercentUpper) {
            musclePushups.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(musclePushups.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_900));
        }

        if ((Integer.parseInt(middleDataFetch)) > tenPercentMiddle && (Integer.parseInt(middleDataFetch)) < thirtyPercentMiddle) {
            muscleSitups.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleSitups.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_200));
        }
        else if ((Integer.parseInt(middleDataFetch)) >= thirtyPercentMiddle && (Integer.parseInt(middleDataFetch)) < eightyPercentMiddle) {
            muscleSitups.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleSitups.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_500));
        }
        else if ((Integer.parseInt(middleDataFetch)) >= eightyPercentMiddle) {
            muscleSitups.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleSitups.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_900));
        }

        if ((Integer.parseInt(lowerDataFetch)) > tenPercentLower && (Integer.parseInt(lowerDataFetch)) < thirtyPercentLower) {
            muscleSquats.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleSquats.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_200));
        }
        else if ((Integer.parseInt(lowerDataFetch)) >= thirtyPercentLower && (Integer.parseInt(lowerDataFetch)) < eightyPercentLower) {
            muscleSquats.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleSquats.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_500));
        }
        else if ((Integer.parseInt(lowerDataFetch)) >= eightyPercentLower) {
            muscleSquats.setVisibility(View.VISIBLE);
            DrawableCompat.setTint(muscleSquats.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.md_red_900));
        }
    }

    public void profileUpdate(){
        //editName from profile through intent
        Intent intent = getIntent();
        editName = intent.getStringExtra("editName");
        editHeight = intent.getStringExtra("editHeight");
        editWeight = intent.getStringExtra("editWeight");
        editAge = intent.getStringExtra("editAge");
        editGender = intent.getStringExtra("editGender");
        editCountryCode = intent.getStringExtra("editCountryCode");

        if(null != editName
                &&null != editHeight
                &&null != editWeight
                &&null != editAge
                &&null != editGender
                &&null != editCountryCode){
            Map<String, Object> updates = new HashMap<String, Object>();
            updates.put("name", editName);
            updates.put("dob", editAge);
            updates.put("gender",editGender);
            updates.put("weight", editWeight);
            updates.put("height", editHeight);
            updates.put("country",editCountryCode);
            databasecuml.child(userId).child(profile).updateChildren(updates);
        }
    }

    public String sendCoreText(){return getButtonPressNo;}

    @Override
    public void onBackPressed() {


        if(backPressedTime+2000>System.currentTimeMillis())
        {
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else
        {
            backToast = Toast.makeText(getBaseContext(),"Press back again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    public final boolean isInternetOn() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet

            /*Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();*/
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            /*Toast.makeText(this, " Internet is Not Connected ", Toast.LENGTH_LONG).show();*/
            return false;
        }
        return false;
    }

    public void profileDatafetchWarning(){
       databasecuml.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (!snapshot.hasChild(profile)) {
                   // run some code
                   final LinearLayout LinearLayoutWarning = findViewById(R.id.LinearLayoutWarning);
                   LinearLayoutWarning.setVisibility(View.VISIBLE);
                   LinearLayoutWarning.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           LinearLayoutWarning.setVisibility(View.GONE);
                       }
                   });
               }
           }
           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }

}