package com.exercise.cuml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class LoginPage extends AppCompatActivity {

    public EditText editText;
    private Spinner spinner;
    private Button buttonCont;
    public String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //hide the action bar from the login screen
        getSupportActionBar().hide();

        spinner = (Spinner)findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        editText = (EditText)findViewById(R.id.editTextPhone);
        buttonCont =(Button)findViewById(R.id.buttonContinue);


        buttonCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                String number = editText.getText().toString().trim();

                if(number.isEmpty() || number.length()<10){
                    editText.setError("valid number is required");
                    editText.requestFocus();
                    return;
                }

                String phoneNumber = "+"+ code + number;
                Intent intent = new Intent(LoginPage.this,VerifyPhoneActivity.class);
                intent.putExtra("phonenumber",phoneNumber);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }
}