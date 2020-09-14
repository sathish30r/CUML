package com.exercise.cuml;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;


public class LeaderBoard extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList,databasePhoneList,list3,list4,lastlist;
    ArrayAdapter arrayAdapter;
    HashSet<String> set;
    private ProgressBar progressBarListView;

    public DatabaseReference databasecuml;
    String userId,profile = "profile";
    String charRemovedPhoneNumber;
    Button buttonFindFriends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        //hiding the tool bar for this main activities
        getSupportActionBar().hide();

        progressBarListView = findViewById(R.id.progressBarListView);
        buttonFindFriends = findViewById(R.id.buttonFindFriends);
        buttonFindFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonFindFriends.setEnabled(false);

                //checking whether the read contact permission is granted.
                if (ContextCompat.checkSelfPermission(LeaderBoard.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    // requesting to the user for permission.
                    ActivityCompat.requestPermissions(LeaderBoard.this, new String[]{Manifest.permission.READ_CONTACTS}, 100);

                } else {
                    //if app already has permission this block will execute.
                    readContacts();
                }
            }
        });

        listView = findViewById(R.id.listViewContact); //listview from xml
        arrayList = new ArrayList<>(); //empty array list.
        list3 = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1   , list3);
        listView.setAdapter(arrayAdapter);

    }

    // if the user clicks ALLOW in dialog this method gets called.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        readContacts();
    }
    // function to read contacts using content resolver
    private void readContacts() {

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Log.i("Names", name);
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    // Query phone here. Covered next
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
                    while (phones.moveToNext()) {
                        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        charRemovedPhoneNumber = phoneNumber.replaceAll("[^a-zA-Z0-9]", "");
                        if(!arrayList.contains(charRemovedPhoneNumber)){
                        arrayList.add(charRemovedPhoneNumber);}
                        arrayAdapter.notifyDataSetChanged();
                        Log.i("Number", phoneNumber);
                    }
                    phones.close();
                }
            }
        }
        fetchAllPhoneNumberFromFirebase();
    }

    public void fetchAllPhoneNumberFromFirebase() {
        // database reference
        databasecuml = FirebaseDatabase.getInstance().getReference("cuml");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getPhoneNumber();//get phone no from firebase and set it as user id
        databasecuml.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    databasePhoneList = new ArrayList<>();
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        String PhoneList = ds.getKey();
                        String trimmedPhone;
                        if(PhoneList.length() >10)
                        {
                            trimmedPhone = PhoneList.substring(PhoneList.length() - 10);
                        }
                        else
                            trimmedPhone = PhoneList;
                        databasePhoneList.add(trimmedPhone);
                    }

                    //find the common elements when comparing two arrayList...
                    //list 1: (arrayList): fetchAllPhoneNumberFromFirebase() method... fetching databasePhoneList
                    //list 2: (databasePhoneList): readcontacts() method... fetching arraylist
                    //comparing list 1 and list2 and comman data retained in list3
                    for (String gettingNumber:arrayList)
                    {
                        if(databasePhoneList.contains(gettingNumber))
                        {
                            list3.add(gettingNumber);
                            arrayAdapter.notifyDataSetChanged();
                            Log.i("PHONEBOOK", gettingNumber);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}