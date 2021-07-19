package com.example.contactapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactapp.model.Person;

import io.realm.Realm;

public class AddContact extends AppCompatActivity {

    private Realm realm;
    ImageView backImageView;
    Button saveButton;
    EditText firstNameEdit, lastNameEdit, emailEdit, phoneNumberEdit, addressEdit;

    String firstName, lastName, email, address, phoneNumber2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);


        // create an instance of Realm database

        realm = Realm.getDefaultInstance();


        // set an onclick listener to the back image to navigate back to the main activity
        backImageView = findViewById(R.id.back_image_view);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivityIntent);

                //ensure that current activity is closed so no in the back stack
                finish();
            }
        });

        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveContact();
            }
        });



    }

    private void saveContact() {

        firstNameEdit = findViewById(R.id.first_name_ti);
        lastNameEdit = findViewById(R.id.last_name);
        emailEdit = findViewById(R.id.email);
        phoneNumberEdit = findViewById(R.id.phone_number);
        addressEdit = findViewById(R.id.address);


        // get user inputed text and convert to string with getText() and toString() methods
        firstName = firstNameEdit.getText().toString();
        lastName = lastNameEdit.getText().toString();
        email = emailEdit.getText().toString();
        phoneNumber2 = phoneNumberEdit.getText().toString();
        address = addressEdit.getText().toString();

        // validate if the text fields are empty or not.

        // if empty set an error message to the edit text views
        if (TextUtils.isEmpty(firstName)) {
            firstNameEdit.setError("Please enter First Name");
        } else if (TextUtils.isEmpty(lastName)) {
            lastNameEdit.setError("Please enter last name");
        } else if (TextUtils.isEmpty(email)) {
            emailEdit.setError("Please enter email");
        } else if (TextUtils.isEmpty(phoneNumber2)) {
            phoneNumberEdit.setError("Please enter phone number");
        } else if (TextUtils.isEmpty(address)) {
            addressEdit.setError("Please enter Address");
        } else {
            // calling method to add data to Realm database..
            addDataToDatabase(firstName, lastName, email, phoneNumber2, address);
            Toast.makeText(this, "Person added to database..", Toast.LENGTH_SHORT).show();

            // empty out the edit text views so user doesnt have to delete previous data next time when creating a contact

            firstNameEdit.setText("");
            lastNameEdit.setText("");
            emailEdit.setText("");
            phoneNumberEdit.setText("");
            addressEdit.setText("");
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            startActivity(mainActivityIntent);
            finish();
        }
    }

    private void addDataToDatabase(String firstName, String lastName, String email, String phoneNumber2, String address) {

        // create a Person/Contact class to be passed to the realm database
        final Person person = new Person();

        //get  id of the contact which we are storing.
        Number id = realm.where(Person.class).max("id");


        // create a variable for id.
        long nextId;

        // validate if id is null or not.
        if (id == null) {

            // set id to 1 if id is null
            nextId = 1;
        } else {

            // increment by 1 if not empty
            nextId = id.intValue() + 1;
        }
        // set the user inputed data onto the person/contact object
        person.setId(nextId);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmail(email);
        person.setPhoneNumber(phoneNumber2);
        person.setAddress(address);

        // Execute a realm transaction to store data in realm database.
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                // copy person/contact class to realm database.
                realm.copyToRealm(person);
            }
        });
    }
}



