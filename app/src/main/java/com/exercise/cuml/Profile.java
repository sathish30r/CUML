package com.exercise.cuml;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.dhaval2404.imagepicker.ImagePicker;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class Profile extends AppCompatActivity {

    public DatePicker datePicker;
    public Calendar calendar;
    public TextView dateView;
    public int year, month, day;
    public StringBuilder selectedDate;
    String selectedGender = "Male";
    String selectedName;
    String selectedHeight;
    String selectedWeight;
    String dob;
    Button buttonProfile,buttonSkipProfile,buttonBack;

    EditText editName;
    EditText editHeight;
    EditText editWeight;
    TextView editAge;

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Spinner spinnerCountry;

    public DatabaseReference databasecuml;
    String userId,profile = "profile";
    String fetchName,fetchHeight,fetchWeight,fetchDOB,fetchCountryCode;
    private static final int PICK_IMAGE_REQUEST =  1;

    Button profilePictureOpenButton;
    ImageView profilePictureImageView;
    private Uri mImageUri;
    private StorageReference firebaseStorageRef;

    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        firebaseStorageRef = FirebaseStorage.getInstance().getReference("cuml");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getPhoneNumber();//get phone no from firebase and set it as user id

        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        profilePictureOpenButton = findViewById(R.id.profilePictureOpenButton);
        profilePictureImageView = findViewById(R.id.profilePictureImageView);
        profilePictureOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        STORAGE_PERMISSION_CODE);
                /*checkPermission(Manifest.permission.CAMERA,
                        CAMERA_PERMISSION_CODE);*/
            }
        });


        buttonProfile = findViewById(R.id.buttonProfile);
        buttonSkipProfile =findViewById(R.id.buttonSkipProfile);
        radioGroup = findViewById(R.id.radioGroup);
        spinnerCountry = (Spinner)findViewById(R.id.spinnerCountry);
        spinnerCountry.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
        //date selection
        dateView = findViewById(R.id.editAge);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        editName = (EditText)findViewById(R.id.editName);
        editHeight = (EditText)findViewById(R.id.editHeight);
        editWeight = (EditText)findViewById(R.id.editWeight);
        editAge = (TextView) findViewById(R.id.editAge);


        //data send through intent on save button press
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedName = editName.getText().toString();
                selectedHeight = editHeight.getText().toString();
                selectedWeight = editWeight.getText().toString();
                dob = selectedDate.toString();
                fetchCountryCode = CountryData.countryAreaCodes[spinnerCountry.getSelectedItemPosition()];

                if(selectedName.isEmpty()){editName.setError("Name is required");
                    editName.requestFocus();return; }
                if(selectedHeight.isEmpty()){editHeight.setError("Height is required");
                    editHeight.requestFocus();return; }
                if(selectedWeight.isEmpty()){editWeight.setError("Weight is required");
                    editWeight.requestFocus();return; }

                Intent intent = new Intent(Profile.this,MainActivity.class);

                intent.putExtra("editName",selectedName);
                intent.putExtra("editHeight",selectedHeight);
                intent.putExtra("editWeight",selectedWeight);
                intent.putExtra("editGender",selectedGender);
                intent.putExtra("editAge", dob);
                intent.putExtra("editCountryCode",fetchCountryCode);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        buttonSkipProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        showProfileData();
    }

    //radio button clicked
    public void checkButton(View view){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        selectedGender = String.valueOf(radioButton.getText());
    }
    //date picker code here
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }
    //date picker code here
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }
    //date picker code here
    public DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year// arg2 = month// arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };
    //date picker code here
    public void showDate(int year, int month, int day) {
        selectedDate = new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year);
        dateView.setText(selectedDate);
    }

    public void showProfileData() {
        // database reference
        databasecuml = FirebaseDatabase.getInstance().getReference("cuml");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getPhoneNumber();//get phone no from firebase and set it as user id
        databasecuml.child(userId).child(profile).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    fetchName = snapshot.child("name").getValue(String.class);
                    fetchHeight = snapshot.child("height").getValue(String.class);
                    fetchWeight = snapshot.child("weight").getValue(String.class);
                    fetchDOB = snapshot.child("dob").getValue(String.class);

                    if(editName.getText().toString().isEmpty()){
                    editName.append(fetchName);}
                    if(editHeight.getText().toString().isEmpty()){
                    editHeight.append(fetchHeight);}
                    if(editWeight.getText().toString().isEmpty()){
                    editWeight.append(fetchWeight);}
                    editAge.setText(fetchDOB);
                }
                else{
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==RESULT_OK){
            if (data != null) {
                mImageUri = data.getData();

                //make an image black and white code starts here////////
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                profilePictureImageView.setColorFilter(filter);
                //make an image black and white code ends here////////

                profilePictureImageView.setImageURI(mImageUri);
            }
        }
        /*if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                        &&data != null && data.getData() != null)
        {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(profilePictureImageView);
        }*/
    }

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(Profile.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(Profile.this,new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                    requestCode);
        }
        else {
            openFileChoosen();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openFileChoosen();
            } else {
                    //denied
                    int x =0;
            }
            break;
        }

    }

    private void openFileChoosen() {

        /*Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(galleryIntent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent,PICK_IMAGE_REQUEST);*/
        ImagePicker.Companion.with(Profile.this)
                .crop(1f,1f)	    			//Crop image(Optional), Check Customization for more option
                .compress(40)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(400, 400)
                .start();
    }

}