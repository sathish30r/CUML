package com.exercise.cuml;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Objects;

public class Menu extends AppCompatActivity {

    public DatabaseReference databasecuml;
    int coreTemp,upperTemp,middleTemp,lowerTemp;
    int valueToSetCore,valueToSetUpper,valueToSetMiddle,valueToSetLower;
    int beginner=45,intermediate=70,advance=100;
    TextView maxValueCore,maxValueUpper,maxValueMiddle,maxValueLower;
    TextView userNameProfile,userLevelFind;
    String userId,profile = "profile",fetchName;
    Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //hiding the tool bar for this main activities
        getSupportActionBar().hide();

        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // database reference
        databasecuml = FirebaseDatabase.getInstance().getReference("cuml");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getPhoneNumber();//get phone no from firebase and set it as user id


        //edit profile button will open to profile activities
        ConstraintLayout editProfile;
        editProfile = findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this,Profile.class);
                startActivity(intent);
            }
        });

        //edit achievements button open achievements calls
        ConstraintLayout editAchievements;
        editAchievements=findViewById(R.id.editAchievements);
        editAchievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this,Achievements.class));
            }
        });

        //edit Leadboard button
        ConstraintLayout editLeadboard;
        editLeadboard=findViewById(R.id.editLeadboard);
        editLeadboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, LeaderBoard.class));
            }
        });

        //edit editInstructions button open achievements calls
        ConstraintLayout editInstructions;
        editInstructions=findViewById(R.id.editInstructions);
        editInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this,Instructions.class));
            }
        });

        //edit editRun button open achievements calls
        ConstraintLayout editRun;
        editRun=findViewById(R.id.editRun);
        editRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, Walk.class));
            }
        });

        //edit editGraph button open achievements calls
        ConstraintLayout editGraph;
        editGraph=findViewById(R.id.editGraph);
        editGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, Graph.class));
            }
        });

        //logout button code here
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Menu.this,LoginPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
            }
        });

        sumOfAllExerciseCount();
        showProfileData();

    }

    private void sumOfAllExerciseCount() {
        //sum of the all exercise count from the beginning to till end
        //database sum of particular column from the beginning to till the end
        maxValueCore =findViewById(R.id.maxValueCore);
        maxValueUpper =findViewById(R.id.maxValueUpper);
        maxValueMiddle =findViewById(R.id.maxValueMiddle);
        maxValueLower =findViewById(R.id.maxValueLower);
        userLevelFind = findViewById(R.id.userLevelFind);


        databasecuml.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    coreTemp = 0;
                    upperTemp = 0;
                    middleTemp = 0;
                    lowerTemp = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, String> map = (Map<String, String>) ds.getValue();
                        String coreCount = Objects.requireNonNull(map).get("coreCount");
                        String upperCount = Objects.requireNonNull(map).get("upperCount");
                        String middleCount = Objects.requireNonNull(map).get("middleCount");
                        String lowerCount = Objects.requireNonNull(map).get("lowerCount");
                        if(coreCount != null||upperCount != null||
                                middleCount != null||lowerCount != null) {
                            int cValue = Integer.parseInt(Objects.requireNonNull(coreCount));
                            int uValue = Integer.parseInt(Objects.requireNonNull(upperCount));
                            int mValue = Integer.parseInt(Objects.requireNonNull(middleCount));
                            int lValue = Integer.parseInt(Objects.requireNonNull(lowerCount));
                            coreTemp = coreTemp + cValue;
                            upperTemp = upperTemp + uValue;
                            middleTemp = middleTemp + mValue;
                            lowerTemp = lowerTemp + lValue;
                            maxValueCore.setText(String.valueOf(coreTemp));
                            maxValueUpper.setText(String.valueOf(upperTemp));
                            maxValueMiddle.setText(String.valueOf(middleTemp));
                            maxValueLower.setText(String.valueOf(lowerTemp));
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

        if(upperTemp >= 0 && upperTemp <500) { valueToSetUpper = beginner;}
        else if(upperTemp >= 500 && upperTemp <1000) { valueToSetUpper = intermediate;}
        else if(upperTemp >= 1000) { valueToSetUpper = advance;}

        if(middleTemp >= 0 && middleTemp <500) { valueToSetMiddle = beginner;}
        else if(middleTemp >= 500 && middleTemp <1000) { valueToSetMiddle = intermediate;}
        else if(middleTemp >= 1000) { valueToSetMiddle = advance;}

        if(lowerTemp >= 0 && lowerTemp <500) { valueToSetLower = beginner;}
        else if(lowerTemp >= 500 && lowerTemp <1000) { valueToSetLower = intermediate;}
        else if(lowerTemp >= 1000) { valueToSetLower = advance;}

        if(valueToSetCore==45||valueToSetUpper==45||valueToSetMiddle==45||valueToSetLower==45)
        {userLevelFind.setText("Amateur");}
        if(valueToSetCore>70&&valueToSetUpper>70&&valueToSetMiddle>70&&valueToSetLower>70)
        {userLevelFind.setText("Pro");}
        if((valueToSetCore>45&&valueToSetCore<100)
                ||(valueToSetCore>45&&valueToSetCore<100)
                ||(valueToSetCore>45&&valueToSetCore<100)
                ||(valueToSetCore>45&&valueToSetCore<100))
        {userLevelFind.setText("Intermediate");}
    }

    public void showProfileData() {
        // database reference
        databasecuml = FirebaseDatabase.getInstance().getReference("cuml");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getPhoneNumber();//get phone no from firebase and set it as user id
        userNameProfile = findViewById(R.id.userNameProfile);
        databasecuml.child(userId).child(profile).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    fetchName = snapshot.child("name").getValue(String.class);
                    userNameProfile.setText(fetchName);
                }
                else{
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}