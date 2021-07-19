package com.example.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class DisplayContact extends AppCompatActivity {

    TextView firstNameTV, lastNameTV, emailTV, phoneNumberTV, addressTV, titleTV;
    String firstNameString, lastNameString, emailString, phoneNumberString, addressString;
    ImageView backImgV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        backImgV = findViewById(R.id.back_image_view);
        backImgV.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // get parsed data from main activity with getIntent and getString methods

        firstNameString = getIntent().getStringExtra("first_name");
        lastNameString = getIntent().getStringExtra("last_name");
        emailString = getIntent().getStringExtra("email");
        phoneNumberString = getIntent().getStringExtra("phone_number");
        addressString = getIntent().getStringExtra("address");

        // find the text views to hold the data
        firstNameTV = findViewById(R.id.first_name_tv);
        lastNameTV = findViewById(R.id.last_name_tv);
        emailTV = findViewById(R.id.email_tv);
        phoneNumberTV = findViewById(R.id.phone_number_tv);
        addressTV = findViewById(R.id.address_tv);
        titleTV = findViewById(R.id.selected_name);

        // set parsed data onto their respective text views with set text method
        firstNameTV.setText(firstNameString);
        lastNameTV.setText(lastNameString);
        emailTV.setText(emailString);
        phoneNumberTV.setText(phoneNumberString);
        addressTV.setText(addressString);
        titleTV.setText(firstNameString +" "+ lastNameString);

    }
}