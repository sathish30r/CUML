package com.exercise.cuml;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class Graph extends AppCompatActivity {

    //prograss bar coding values
    ProgressBar determinateBar,determinateBar1,determinateBar2,determinateBar3;
    ProgressBar determinateBarCore1,determinateBarCore2,determinateBarCore3,
            determinateBarCore4,determinateBarCore5,determinateBarCore6,
            determinateBarCore7,determinateBarCore8,determinateBarCore9,
            determinateBarCore10,determinateBarCore11,determinateBarCore12,
            determinateBarCore13,determinateBarCore14,determinateBarCore15,
            determinateBarCore16,determinateBarCore17,determinateBarCore18,
            determinateBarCore19,determinateBarCore20,determinateBarCore21,
            determinateBarCore22,determinateBarCore23,determinateBarCore24,
            determinateBarCore25,determinateBarCore26,determinateBarCore27,
            determinateBarCore28,determinateBarCore29,determinateBarCore30;
    ProgressBar determinateBarUpper1,determinateBarUpper2,determinateBarUpper3,
            determinateBarUpper4,determinateBarUpper5,determinateBarUpper6,
            determinateBarUpper7,determinateBarUpper8,determinateBarUpper9,
            determinateBarUpper10,determinateBarUpper11,determinateBarUpper12,
            determinateBarUpper13,determinateBarUpper14,determinateBarUpper15,
            determinateBarUpper16,determinateBarUpper17,determinateBarUpper18,
            determinateBarUpper19,determinateBarUpper20,determinateBarUpper21,
            determinateBarUpper22,determinateBarUpper23,determinateBarUpper24,
            determinateBarUpper25,determinateBarUpper26,determinateBarUpper27,
            determinateBarUpper28,determinateBarUpper29,determinateBarUpper30;
    ProgressBar determinateBarMiddle1,determinateBarMiddle2,determinateBarMiddle3,
            determinateBarMiddle4,determinateBarMiddle5,determinateBarMiddle6,
            determinateBarMiddle7,determinateBarMiddle8,determinateBarMiddle9,
            determinateBarMiddle10,determinateBarMiddle11,determinateBarMiddle12,
            determinateBarMiddle13,determinateBarMiddle14,determinateBarMiddle15,
            determinateBarMiddle16,determinateBarMiddle17,determinateBarMiddle18,
            determinateBarMiddle19,determinateBarMiddle20,determinateBarMiddle21,
            determinateBarMiddle22,determinateBarMiddle23,determinateBarMiddle24,
            determinateBarMiddle25,determinateBarMiddle26,determinateBarMiddle27,
            determinateBarMiddle28,determinateBarMiddle29,determinateBarMiddle30;
    ProgressBar determinateBarLower1,determinateBarLower2,determinateBarLower3,
            determinateBarLower4,determinateBarLower5,determinateBarLower6,
            determinateBarLower7,determinateBarLower8,determinateBarLower9,
            determinateBarLower10,determinateBarLower11,determinateBarLower12,
            determinateBarLower13,determinateBarLower14,determinateBarLower15,
            determinateBarLower16,determinateBarLower17,determinateBarLower18,
            determinateBarLower19,determinateBarLower20,determinateBarLower21,
            determinateBarLower22,determinateBarLower23,determinateBarLower24,
            determinateBarLower25,determinateBarLower26,determinateBarLower27,
            determinateBarLower28,determinateBarLower29,determinateBarLower30;

    TextView textViewMonthCountCore,textViewMonthCountUpper,
            textViewMonthCountMiddle,textViewMonthCountLower;
    int valueToSetCore,valueToSetUpper,valueToSetMiddle,valueToSetLower;
    int core30Count,upper30Count,middle30Count,lower30Count;
    int coreTemp,upperTemp,middleTemp,lowerTemp;
    int beginner=45,intermediate=70,advance=100;
    String userLevelName;
    //date as unique id for users particular date
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
    String strDate = formatter.format(date);

    public DatabaseReference databasecuml;
    String userId,profile = "profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        //hiding the tool bar for this main activities
        getSupportActionBar().hide();

        // database reference
        databasecuml = FirebaseDatabase.getInstance().getReference("cuml");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getPhoneNumber();//get phone no from firebase and set it as user id

        //month graph core, upper , middle, lower once at start of app
        databasecuml.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    monthGraphCore();
                    monthGraphUpper();
                    monthGraphMiddle();
                    monthGraphLower();
                    setDailyExerciseCountAsPerUser();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        monthGraphCore();
        monthGraphUpper();
        monthGraphMiddle();
        monthGraphLower();
        setDailyExerciseCountAsPerUser();
    }

    private void monthGraphCore() {

        determinateBarCore1=(ProgressBar)findViewById(R.id.determinateBarCore1);
        determinateBarCore2=(ProgressBar)findViewById(R.id.determinateBarCore2);
        determinateBarCore3=(ProgressBar)findViewById(R.id.determinateBarCore3);
        determinateBarCore4=(ProgressBar)findViewById(R.id.determinateBarCore4);
        determinateBarCore5=(ProgressBar)findViewById(R.id.determinateBarCore5);
        determinateBarCore6=(ProgressBar)findViewById(R.id.determinateBarCore6);
        determinateBarCore7=(ProgressBar)findViewById(R.id.determinateBarCore7);
        determinateBarCore8=(ProgressBar)findViewById(R.id.determinateBarCore8);
        determinateBarCore9=(ProgressBar)findViewById(R.id.determinateBarCore9);
        determinateBarCore10=(ProgressBar)findViewById(R.id.determinateBarCore10);
        determinateBarCore11=(ProgressBar)findViewById(R.id.determinateBarCore11);
        determinateBarCore12=(ProgressBar)findViewById(R.id.determinateBarCore12);
        determinateBarCore13=(ProgressBar)findViewById(R.id.determinateBarCore13);
        determinateBarCore14=(ProgressBar)findViewById(R.id.determinateBarCore14);
        determinateBarCore15=(ProgressBar)findViewById(R.id.determinateBarCore15);
        determinateBarCore16=(ProgressBar)findViewById(R.id.determinateBarCore16);
        determinateBarCore17=(ProgressBar)findViewById(R.id.determinateBarCore17);
        determinateBarCore18=(ProgressBar)findViewById(R.id.determinateBarCore18);
        determinateBarCore19=(ProgressBar)findViewById(R.id.determinateBarCore19);
        determinateBarCore20=(ProgressBar)findViewById(R.id.determinateBarCore20);
        determinateBarCore21=(ProgressBar)findViewById(R.id.determinateBarCore21);
        determinateBarCore22=(ProgressBar)findViewById(R.id.determinateBarCore22);
        determinateBarCore23=(ProgressBar)findViewById(R.id.determinateBarCore23);
        determinateBarCore24=(ProgressBar)findViewById(R.id.determinateBarCore24);
        determinateBarCore25=(ProgressBar)findViewById(R.id.determinateBarCore25);
        determinateBarCore26=(ProgressBar)findViewById(R.id.determinateBarCore26);
        determinateBarCore27=(ProgressBar)findViewById(R.id.determinateBarCore27);
        determinateBarCore28=(ProgressBar)findViewById(R.id.determinateBarCore28);
        determinateBarCore29=(ProgressBar)findViewById(R.id.determinateBarCore29);
        determinateBarCore30=(ProgressBar)findViewById(R.id.determinateBarCore30);

        textViewMonthCountCore = (TextView)findViewById(R.id.textViewMonthCountCore);

        determinateBarCore1.setMax(valueToSetCore);
        determinateBarCore2.setMax(valueToSetCore);
        determinateBarCore3.setMax(valueToSetCore);
        determinateBarCore4.setMax(valueToSetCore);
        determinateBarCore5.setMax(valueToSetCore);
        determinateBarCore6.setMax(valueToSetCore);
        determinateBarCore7.setMax(valueToSetCore);
        determinateBarCore8.setMax(valueToSetCore);
        determinateBarCore9.setMax(valueToSetCore);
        determinateBarCore10.setMax(valueToSetCore);
        determinateBarCore11.setMax(valueToSetCore);
        determinateBarCore12.setMax(valueToSetCore);
        determinateBarCore13.setMax(valueToSetCore);
        determinateBarCore14.setMax(valueToSetCore);
        determinateBarCore15.setMax(valueToSetCore);
        determinateBarCore16.setMax(valueToSetCore);
        determinateBarCore17.setMax(valueToSetCore);
        determinateBarCore18.setMax(valueToSetCore);
        determinateBarCore19.setMax(valueToSetCore);
        determinateBarCore20.setMax(valueToSetCore);
        determinateBarCore21.setMax(valueToSetCore);
        determinateBarCore22.setMax(valueToSetCore);
        determinateBarCore23.setMax(valueToSetCore);
        determinateBarCore24.setMax(valueToSetCore);
        determinateBarCore25.setMax(valueToSetCore);
        determinateBarCore26.setMax(valueToSetCore);
        determinateBarCore27.setMax(valueToSetCore);
        determinateBarCore28.setMax(valueToSetCore);
        determinateBarCore29.setMax(valueToSetCore);
        determinateBarCore30.setMax(valueToSetCore);

        final int temp30days[] = new int[30];

        // database reference
        databasecuml = FirebaseDatabase.getInstance().getReference("cuml");
        databasecuml.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    core30Count = 0;
                    for(int i=0; i<30; i++)
                    {
                        Date date = new Date(new Date().getTime() - (i)*24*3600*1000);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
                        String strDate1 = formatter.format(date);
                        String x = null;
                        if(snapshot.child(strDate1)!=null &&
                                snapshot.child(strDate1).child("coreCount").getValue()!=null)
                            x = snapshot.child(strDate1).child("coreCount").getValue().toString();
                        if(x==null || x.equals("0"))
                        { temp30days[i]=0; x = "0";}
                        else if(Integer.parseInt(x)>valueToSetCore)
                        {temp30days[i]= Integer.parseInt(String.valueOf(valueToSetCore));}
                        else
                        { temp30days[i]= Integer.parseInt(x); }
                        int x1 =Integer.parseInt(x);
                        core30Count = core30Count + x1;
                    }
                    textViewMonthCountCore.setText(String.valueOf(core30Count));
                    determinateBarCore1.setProgress(temp30days[0]);
                    determinateBarCore2.setProgress(temp30days[1]);
                    determinateBarCore3.setProgress(temp30days[2]);
                    determinateBarCore4.setProgress(temp30days[3]);
                    determinateBarCore5.setProgress(temp30days[4]);
                    determinateBarCore6.setProgress(temp30days[5]);
                    determinateBarCore7.setProgress(temp30days[6]);
                    determinateBarCore8.setProgress(temp30days[7]);
                    determinateBarCore9.setProgress(temp30days[8]);
                    determinateBarCore10.setProgress(temp30days[9]);
                    determinateBarCore11.setProgress(temp30days[10]);
                    determinateBarCore12.setProgress(temp30days[11]);
                    determinateBarCore13.setProgress(temp30days[12]);
                    determinateBarCore14.setProgress(temp30days[13]);
                    determinateBarCore15.setProgress(temp30days[14]);
                    determinateBarCore16.setProgress(temp30days[15]);
                    determinateBarCore17.setProgress(temp30days[16]);
                    determinateBarCore18.setProgress(temp30days[17]);
                    determinateBarCore19.setProgress(temp30days[18]);
                    determinateBarCore20.setProgress(temp30days[19]);
                    determinateBarCore21.setProgress(temp30days[20]);
                    determinateBarCore22.setProgress(temp30days[21]);
                    determinateBarCore23.setProgress(temp30days[22]);
                    determinateBarCore24.setProgress(temp30days[23]);
                    determinateBarCore25.setProgress(temp30days[24]);
                    determinateBarCore26.setProgress(temp30days[25]);
                    determinateBarCore27.setProgress(temp30days[26]);
                    determinateBarCore28.setProgress(temp30days[27]);
                    determinateBarCore29.setProgress(temp30days[28]);
                    determinateBarCore30.setProgress(temp30days[29]);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void monthGraphUpper() {

        determinateBarUpper1=(ProgressBar)findViewById(R.id.determinateBarUpper1);
        determinateBarUpper2=(ProgressBar)findViewById(R.id.determinateBarUpper2);
        determinateBarUpper3=(ProgressBar)findViewById(R.id.determinateBarUpper3);
        determinateBarUpper4=(ProgressBar)findViewById(R.id.determinateBarUpper4);
        determinateBarUpper5=(ProgressBar)findViewById(R.id.determinateBarUpper5);
        determinateBarUpper6=(ProgressBar)findViewById(R.id.determinateBarUpper6);
        determinateBarUpper7=(ProgressBar)findViewById(R.id.determinateBarUpper7);
        determinateBarUpper8=(ProgressBar)findViewById(R.id.determinateBarUpper8);
        determinateBarUpper9=(ProgressBar)findViewById(R.id.determinateBarUpper9);
        determinateBarUpper10=(ProgressBar)findViewById(R.id.determinateBarUpper10);
        determinateBarUpper11=(ProgressBar)findViewById(R.id.determinateBarUpper11);
        determinateBarUpper12=(ProgressBar)findViewById(R.id.determinateBarUpper12);
        determinateBarUpper13=(ProgressBar)findViewById(R.id.determinateBarUpper13);
        determinateBarUpper14=(ProgressBar)findViewById(R.id.determinateBarUpper14);
        determinateBarUpper15=(ProgressBar)findViewById(R.id.determinateBarUpper15);
        determinateBarUpper16=(ProgressBar)findViewById(R.id.determinateBarUpper16);
        determinateBarUpper17=(ProgressBar)findViewById(R.id.determinateBarUpper17);
        determinateBarUpper18=(ProgressBar)findViewById(R.id.determinateBarUpper18);
        determinateBarUpper19=(ProgressBar)findViewById(R.id.determinateBarUpper19);
        determinateBarUpper20=(ProgressBar)findViewById(R.id.determinateBarUpper20);
        determinateBarUpper21=(ProgressBar)findViewById(R.id.determinateBarUpper21);
        determinateBarUpper22=(ProgressBar)findViewById(R.id.determinateBarUpper22);
        determinateBarUpper23=(ProgressBar)findViewById(R.id.determinateBarUpper23);
        determinateBarUpper24=(ProgressBar)findViewById(R.id.determinateBarUpper24);
        determinateBarUpper25=(ProgressBar)findViewById(R.id.determinateBarUpper25);
        determinateBarUpper26=(ProgressBar)findViewById(R.id.determinateBarUpper26);
        determinateBarUpper27=(ProgressBar)findViewById(R.id.determinateBarUpper27);
        determinateBarUpper28=(ProgressBar)findViewById(R.id.determinateBarUpper28);
        determinateBarUpper29=(ProgressBar)findViewById(R.id.determinateBarUpper29);
        determinateBarUpper30=(ProgressBar)findViewById(R.id.determinateBarUpper30);

        textViewMonthCountUpper = (TextView)findViewById(R.id.textViewMonthCountUpper);

        determinateBarUpper1.setMax(valueToSetUpper);
        determinateBarUpper2.setMax(valueToSetUpper);
        determinateBarUpper3.setMax(valueToSetUpper);
        determinateBarUpper4.setMax(valueToSetUpper);
        determinateBarUpper5.setMax(valueToSetUpper);
        determinateBarUpper6.setMax(valueToSetUpper);
        determinateBarUpper7.setMax(valueToSetUpper);
        determinateBarUpper8.setMax(valueToSetUpper);
        determinateBarUpper9.setMax(valueToSetUpper);
        determinateBarUpper10.setMax(valueToSetUpper);
        determinateBarUpper11.setMax(valueToSetUpper);
        determinateBarUpper12.setMax(valueToSetUpper);
        determinateBarUpper13.setMax(valueToSetUpper);
        determinateBarUpper14.setMax(valueToSetUpper);
        determinateBarUpper15.setMax(valueToSetUpper);
        determinateBarUpper16.setMax(valueToSetUpper);
        determinateBarUpper17.setMax(valueToSetUpper);
        determinateBarUpper18.setMax(valueToSetUpper);
        determinateBarUpper19.setMax(valueToSetUpper);
        determinateBarUpper20.setMax(valueToSetUpper);
        determinateBarUpper21.setMax(valueToSetUpper);
        determinateBarUpper22.setMax(valueToSetUpper);
        determinateBarUpper23.setMax(valueToSetUpper);
        determinateBarUpper24.setMax(valueToSetUpper);
        determinateBarUpper25.setMax(valueToSetUpper);
        determinateBarUpper26.setMax(valueToSetUpper);
        determinateBarUpper27.setMax(valueToSetUpper);
        determinateBarUpper28.setMax(valueToSetUpper);
        determinateBarUpper29.setMax(valueToSetUpper);
        determinateBarUpper30.setMax(valueToSetUpper);

        final int temp30days[] = new int[30];

        // database reference
        databasecuml = FirebaseDatabase.getInstance().getReference("cuml");
        databasecuml.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    upper30Count = 0;
                    for(int i=0; i<30; i++)
                    {
                        Date date = new Date(new Date().getTime() - (i)*24*3600*1000);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
                        String strDate1 = formatter.format(date);
                        String x = null;
                        if(snapshot.child(strDate1)!=null &&
                                snapshot.child(strDate1).child("upperCount").getValue()!=null)
                            x = snapshot.child(strDate1).child("upperCount").getValue().toString();
                        if(x==null || x.equals("0"))
                        { temp30days[i]=0; x = "0";}
                        else if(Integer.parseInt(x)>valueToSetUpper)
                        {temp30days[i]= Integer.parseInt(String.valueOf(valueToSetUpper));}
                        else
                        { temp30days[i]= Integer.parseInt(x); }
                        int x1 =Integer.parseInt(x);
                        upper30Count = upper30Count + x1;
                    }
                    textViewMonthCountUpper.setText(String.valueOf(upper30Count));
                    determinateBarUpper1.setProgress(temp30days[0]);
                    determinateBarUpper2.setProgress(temp30days[1]);
                    determinateBarUpper3.setProgress(temp30days[2]);
                    determinateBarUpper4.setProgress(temp30days[3]);
                    determinateBarUpper5.setProgress(temp30days[4]);
                    determinateBarUpper6.setProgress(temp30days[5]);
                    determinateBarUpper7.setProgress(temp30days[6]);
                    determinateBarUpper8.setProgress(temp30days[7]);
                    determinateBarUpper9.setProgress(temp30days[8]);
                    determinateBarUpper10.setProgress(temp30days[9]);
                    determinateBarUpper11.setProgress(temp30days[10]);
                    determinateBarUpper12.setProgress(temp30days[11]);
                    determinateBarUpper13.setProgress(temp30days[12]);
                    determinateBarUpper14.setProgress(temp30days[13]);
                    determinateBarUpper15.setProgress(temp30days[14]);
                    determinateBarUpper16.setProgress(temp30days[15]);
                    determinateBarUpper17.setProgress(temp30days[16]);
                    determinateBarUpper18.setProgress(temp30days[17]);
                    determinateBarUpper19.setProgress(temp30days[18]);
                    determinateBarUpper20.setProgress(temp30days[19]);
                    determinateBarUpper21.setProgress(temp30days[20]);
                    determinateBarUpper22.setProgress(temp30days[21]);
                    determinateBarUpper23.setProgress(temp30days[22]);
                    determinateBarUpper24.setProgress(temp30days[23]);
                    determinateBarUpper25.setProgress(temp30days[24]);
                    determinateBarUpper26.setProgress(temp30days[25]);
                    determinateBarUpper27.setProgress(temp30days[26]);
                    determinateBarUpper28.setProgress(temp30days[27]);
                    determinateBarUpper29.setProgress(temp30days[28]);
                    determinateBarUpper30.setProgress(temp30days[29]);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void monthGraphMiddle() {

        determinateBarMiddle1=(ProgressBar)findViewById(R.id.determinateBarMiddle1);
        determinateBarMiddle2=(ProgressBar)findViewById(R.id.determinateBarMiddle2);
        determinateBarMiddle3=(ProgressBar)findViewById(R.id.determinateBarMiddle3);
        determinateBarMiddle4=(ProgressBar)findViewById(R.id.determinateBarMiddle4);
        determinateBarMiddle5=(ProgressBar)findViewById(R.id.determinateBarMiddle5);
        determinateBarMiddle6=(ProgressBar)findViewById(R.id.determinateBarMiddle6);
        determinateBarMiddle7=(ProgressBar)findViewById(R.id.determinateBarMiddle7);
        determinateBarMiddle8=(ProgressBar)findViewById(R.id.determinateBarMiddle8);
        determinateBarMiddle9=(ProgressBar)findViewById(R.id.determinateBarMiddle9);
        determinateBarMiddle10=(ProgressBar)findViewById(R.id.determinateBarMiddle10);
        determinateBarMiddle11=(ProgressBar)findViewById(R.id.determinateBarMiddle11);
        determinateBarMiddle12=(ProgressBar)findViewById(R.id.determinateBarMiddle12);
        determinateBarMiddle13=(ProgressBar)findViewById(R.id.determinateBarMiddle13);
        determinateBarMiddle14=(ProgressBar)findViewById(R.id.determinateBarMiddle14);
        determinateBarMiddle15=(ProgressBar)findViewById(R.id.determinateBarMiddle15);
        determinateBarMiddle16=(ProgressBar)findViewById(R.id.determinateBarMiddle16);
        determinateBarMiddle17=(ProgressBar)findViewById(R.id.determinateBarMiddle17);
        determinateBarMiddle18=(ProgressBar)findViewById(R.id.determinateBarMiddle18);
        determinateBarMiddle19=(ProgressBar)findViewById(R.id.determinateBarMiddle19);
        determinateBarMiddle20=(ProgressBar)findViewById(R.id.determinateBarMiddle20);
        determinateBarMiddle21=(ProgressBar)findViewById(R.id.determinateBarMiddle21);
        determinateBarMiddle22=(ProgressBar)findViewById(R.id.determinateBarMiddle22);
        determinateBarMiddle23=(ProgressBar)findViewById(R.id.determinateBarMiddle23);
        determinateBarMiddle24=(ProgressBar)findViewById(R.id.determinateBarMiddle24);
        determinateBarMiddle25=(ProgressBar)findViewById(R.id.determinateBarMiddle25);
        determinateBarMiddle26=(ProgressBar)findViewById(R.id.determinateBarMiddle26);
        determinateBarMiddle27=(ProgressBar)findViewById(R.id.determinateBarMiddle27);
        determinateBarMiddle28=(ProgressBar)findViewById(R.id.determinateBarMiddle28);
        determinateBarMiddle29=(ProgressBar)findViewById(R.id.determinateBarMiddle29);
        determinateBarMiddle30=(ProgressBar)findViewById(R.id.determinateBarMiddle30);

        textViewMonthCountMiddle = (TextView)findViewById(R.id.textViewMonthCountMiddle);

        determinateBarMiddle1.setMax(valueToSetMiddle);
        determinateBarMiddle2.setMax(valueToSetMiddle);
        determinateBarMiddle3.setMax(valueToSetMiddle);
        determinateBarMiddle4.setMax(valueToSetMiddle);
        determinateBarMiddle5.setMax(valueToSetMiddle);
        determinateBarMiddle6.setMax(valueToSetMiddle);
        determinateBarMiddle7.setMax(valueToSetMiddle);
        determinateBarMiddle8.setMax(valueToSetMiddle);
        determinateBarMiddle9.setMax(valueToSetMiddle);
        determinateBarMiddle10.setMax(valueToSetMiddle);
        determinateBarMiddle11.setMax(valueToSetMiddle);
        determinateBarMiddle12.setMax(valueToSetMiddle);
        determinateBarMiddle13.setMax(valueToSetMiddle);
        determinateBarMiddle14.setMax(valueToSetMiddle);
        determinateBarMiddle15.setMax(valueToSetMiddle);
        determinateBarMiddle16.setMax(valueToSetMiddle);
        determinateBarMiddle17.setMax(valueToSetMiddle);
        determinateBarMiddle18.setMax(valueToSetMiddle);
        determinateBarMiddle19.setMax(valueToSetMiddle);
        determinateBarMiddle20.setMax(valueToSetMiddle);
        determinateBarMiddle21.setMax(valueToSetMiddle);
        determinateBarMiddle22.setMax(valueToSetMiddle);
        determinateBarMiddle23.setMax(valueToSetMiddle);
        determinateBarMiddle24.setMax(valueToSetMiddle);
        determinateBarMiddle25.setMax(valueToSetMiddle);
        determinateBarMiddle26.setMax(valueToSetMiddle);
        determinateBarMiddle27.setMax(valueToSetMiddle);
        determinateBarMiddle28.setMax(valueToSetMiddle);
        determinateBarMiddle29.setMax(valueToSetMiddle);
        determinateBarMiddle30.setMax(valueToSetMiddle);

        final int temp30days[] = new int[30];

        // database reference
        databasecuml = FirebaseDatabase.getInstance().getReference("cuml");
        databasecuml.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    middle30Count = 0;
                    for(int i=0; i<30; i++)
                    {
                        Date date = new Date(new Date().getTime() - (i)*24*3600*1000);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
                        String strDate1 = formatter.format(date);
                        String x = null;
                        if(snapshot.child(strDate1)!=null &&
                                snapshot.child(strDate1).child("middleCount").getValue()!=null)
                            x = snapshot.child(strDate1).child("middleCount").getValue().toString();
                        if(x==null || x.equals("0"))
                        { temp30days[i]=0; x = "0";}
                        else if(Integer.parseInt(x)>valueToSetMiddle)
                        {temp30days[i]= Integer.parseInt(String.valueOf(valueToSetMiddle));}
                        else
                        { temp30days[i]= Integer.parseInt(x); }
                        int x1 =Integer.parseInt(x);
                        middle30Count = middle30Count + x1;
                    }
                    textViewMonthCountMiddle.setText(String.valueOf(middle30Count));
                    determinateBarMiddle1.setProgress(temp30days[0]);
                    determinateBarMiddle2.setProgress(temp30days[1]);
                    determinateBarMiddle3.setProgress(temp30days[2]);
                    determinateBarMiddle4.setProgress(temp30days[3]);
                    determinateBarMiddle5.setProgress(temp30days[4]);
                    determinateBarMiddle6.setProgress(temp30days[5]);
                    determinateBarMiddle7.setProgress(temp30days[6]);
                    determinateBarMiddle8.setProgress(temp30days[7]);
                    determinateBarMiddle9.setProgress(temp30days[8]);
                    determinateBarMiddle10.setProgress(temp30days[9]);
                    determinateBarMiddle11.setProgress(temp30days[10]);
                    determinateBarMiddle12.setProgress(temp30days[11]);
                    determinateBarMiddle13.setProgress(temp30days[12]);
                    determinateBarMiddle14.setProgress(temp30days[13]);
                    determinateBarMiddle15.setProgress(temp30days[14]);
                    determinateBarMiddle16.setProgress(temp30days[15]);
                    determinateBarMiddle17.setProgress(temp30days[16]);
                    determinateBarMiddle18.setProgress(temp30days[17]);
                    determinateBarMiddle19.setProgress(temp30days[18]);
                    determinateBarMiddle20.setProgress(temp30days[19]);
                    determinateBarMiddle21.setProgress(temp30days[20]);
                    determinateBarMiddle22.setProgress(temp30days[21]);
                    determinateBarMiddle23.setProgress(temp30days[22]);
                    determinateBarMiddle24.setProgress(temp30days[23]);
                    determinateBarMiddle25.setProgress(temp30days[24]);
                    determinateBarMiddle26.setProgress(temp30days[25]);
                    determinateBarMiddle27.setProgress(temp30days[26]);
                    determinateBarMiddle28.setProgress(temp30days[27]);
                    determinateBarMiddle29.setProgress(temp30days[28]);
                    determinateBarMiddle30.setProgress(temp30days[29]);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void monthGraphLower() {

        determinateBarLower1=(ProgressBar)findViewById(R.id.determinateBarLower1);
        determinateBarLower2=(ProgressBar)findViewById(R.id.determinateBarLower2);
        determinateBarLower3=(ProgressBar)findViewById(R.id.determinateBarLower3);
        determinateBarLower4=(ProgressBar)findViewById(R.id.determinateBarLower4);
        determinateBarLower5=(ProgressBar)findViewById(R.id.determinateBarLower5);
        determinateBarLower6=(ProgressBar)findViewById(R.id.determinateBarLower6);
        determinateBarLower7=(ProgressBar)findViewById(R.id.determinateBarLower7);
        determinateBarLower8=(ProgressBar)findViewById(R.id.determinateBarLower8);
        determinateBarLower9=(ProgressBar)findViewById(R.id.determinateBarLower9);
        determinateBarLower10=(ProgressBar)findViewById(R.id.determinateBarLower10);
        determinateBarLower11=(ProgressBar)findViewById(R.id.determinateBarLower11);
        determinateBarLower12=(ProgressBar)findViewById(R.id.determinateBarLower12);
        determinateBarLower13=(ProgressBar)findViewById(R.id.determinateBarLower13);
        determinateBarLower14=(ProgressBar)findViewById(R.id.determinateBarLower14);
        determinateBarLower15=(ProgressBar)findViewById(R.id.determinateBarLower15);
        determinateBarLower16=(ProgressBar)findViewById(R.id.determinateBarLower16);
        determinateBarLower17=(ProgressBar)findViewById(R.id.determinateBarLower17);
        determinateBarLower18=(ProgressBar)findViewById(R.id.determinateBarLower18);
        determinateBarLower19=(ProgressBar)findViewById(R.id.determinateBarLower19);
        determinateBarLower20=(ProgressBar)findViewById(R.id.determinateBarLower20);
        determinateBarLower21=(ProgressBar)findViewById(R.id.determinateBarLower21);
        determinateBarLower22=(ProgressBar)findViewById(R.id.determinateBarLower22);
        determinateBarLower23=(ProgressBar)findViewById(R.id.determinateBarLower23);
        determinateBarLower24=(ProgressBar)findViewById(R.id.determinateBarLower24);
        determinateBarLower25=(ProgressBar)findViewById(R.id.determinateBarLower25);
        determinateBarLower26=(ProgressBar)findViewById(R.id.determinateBarLower26);
        determinateBarLower27=(ProgressBar)findViewById(R.id.determinateBarLower27);
        determinateBarLower28=(ProgressBar)findViewById(R.id.determinateBarLower28);
        determinateBarLower29=(ProgressBar)findViewById(R.id.determinateBarLower29);
        determinateBarLower30=(ProgressBar)findViewById(R.id.determinateBarLower30);

        textViewMonthCountLower = (TextView)findViewById(R.id.textViewMonthCountLower);

        determinateBarLower1.setMax(valueToSetLower);
        determinateBarLower2.setMax(valueToSetLower);
        determinateBarLower3.setMax(valueToSetLower);
        determinateBarLower4.setMax(valueToSetLower);
        determinateBarLower5.setMax(valueToSetLower);
        determinateBarLower6.setMax(valueToSetLower);
        determinateBarLower7.setMax(valueToSetLower);
        determinateBarLower8.setMax(valueToSetLower);
        determinateBarLower9.setMax(valueToSetLower);
        determinateBarLower10.setMax(valueToSetLower);
        determinateBarLower11.setMax(valueToSetLower);
        determinateBarLower12.setMax(valueToSetLower);
        determinateBarLower13.setMax(valueToSetLower);
        determinateBarLower14.setMax(valueToSetLower);
        determinateBarLower15.setMax(valueToSetLower);
        determinateBarLower16.setMax(valueToSetLower);
        determinateBarLower17.setMax(valueToSetLower);
        determinateBarLower18.setMax(valueToSetLower);
        determinateBarLower19.setMax(valueToSetLower);
        determinateBarLower20.setMax(valueToSetLower);
        determinateBarLower21.setMax(valueToSetLower);
        determinateBarLower22.setMax(valueToSetLower);
        determinateBarLower23.setMax(valueToSetLower);
        determinateBarLower24.setMax(valueToSetLower);
        determinateBarLower25.setMax(valueToSetLower);
        determinateBarLower26.setMax(valueToSetLower);
        determinateBarLower27.setMax(valueToSetLower);
        determinateBarLower28.setMax(valueToSetLower);
        determinateBarLower29.setMax(valueToSetLower);
        determinateBarLower30.setMax(valueToSetLower);

        final int temp30days[] = new int[30];

        // database reference
        databasecuml = FirebaseDatabase.getInstance().getReference("cuml");
        databasecuml.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    lower30Count = 0;
                    for(int i=0; i<30; i++)
                    {
                        Date date = new Date(new Date().getTime() - (i)*24*3600*1000);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
                        String strDate1 = formatter.format(date);
                        String x = null;
                        if(snapshot.child(strDate1)!=null &&
                                snapshot.child(strDate1).child("lowerCount").getValue()!=null)
                            x = snapshot.child(strDate1).child("lowerCount").getValue().toString();
                        if(x==null || x.equals("0"))
                        { temp30days[i]=0; x = "0";}
                        else if(Integer.parseInt(x)>valueToSetLower)
                        {temp30days[i]= Integer.parseInt(String.valueOf(valueToSetLower));}
                        else
                        { temp30days[i]= Integer.parseInt(x); }
                        int x1 =Integer.parseInt(x);
                        lower30Count = lower30Count + x1;
                    }
                    textViewMonthCountLower.setText(String.valueOf(lower30Count));
                    determinateBarLower1.setProgress(temp30days[0]);
                    determinateBarLower2.setProgress(temp30days[1]);
                    determinateBarLower3.setProgress(temp30days[2]);
                    determinateBarLower4.setProgress(temp30days[3]);
                    determinateBarLower5.setProgress(temp30days[4]);
                    determinateBarLower6.setProgress(temp30days[5]);
                    determinateBarLower7.setProgress(temp30days[6]);
                    determinateBarLower8.setProgress(temp30days[7]);
                    determinateBarLower9.setProgress(temp30days[8]);
                    determinateBarLower10.setProgress(temp30days[9]);
                    determinateBarLower11.setProgress(temp30days[10]);
                    determinateBarLower12.setProgress(temp30days[11]);
                    determinateBarLower13.setProgress(temp30days[12]);
                    determinateBarLower14.setProgress(temp30days[13]);
                    determinateBarLower15.setProgress(temp30days[14]);
                    determinateBarLower16.setProgress(temp30days[15]);
                    determinateBarLower17.setProgress(temp30days[16]);
                    determinateBarLower18.setProgress(temp30days[17]);
                    determinateBarLower19.setProgress(temp30days[18]);
                    determinateBarLower20.setProgress(temp30days[19]);
                    determinateBarLower21.setProgress(temp30days[20]);
                    determinateBarLower22.setProgress(temp30days[21]);
                    determinateBarLower23.setProgress(temp30days[22]);
                    determinateBarLower24.setProgress(temp30days[23]);
                    determinateBarLower25.setProgress(temp30days[24]);
                    determinateBarLower26.setProgress(temp30days[25]);
                    determinateBarLower27.setProgress(temp30days[26]);
                    determinateBarLower28.setProgress(temp30days[27]);
                    determinateBarLower29.setProgress(temp30days[28]);
                    determinateBarLower30.setProgress(temp30days[29]);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void setDailyExerciseCountAsPerUser() {


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
        {userLevelName ="Amateur";}
        if(valueToSetCore>70&&valueToSetUpper>70&&valueToSetMiddle>70&&valueToSetLower>70)
        {userLevelName = "Pro";}
        if((valueToSetCore>45&&valueToSetCore<100)
                ||(valueToSetCore>45&&valueToSetCore<100)
                ||(valueToSetCore>45&&valueToSetCore<100)
                ||(valueToSetCore>45&&valueToSetCore<100))
        {userLevelName = "Intermediate";}

    }
}