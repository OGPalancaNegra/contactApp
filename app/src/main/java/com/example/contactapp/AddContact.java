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
    Long phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // Realm.init(this);

        realm = Realm.getDefaultInstance();

        backImageView = findViewById(R.id.back_image_view);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivityIntent);
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

        firstName = firstNameEdit.getText().toString();
        lastName = lastNameEdit.getText().toString();
        email = emailEdit.getText().toString();
        phoneNumber2 = phoneNumberEdit.getText().toString();
        address = addressEdit.getText().toString();

        // validating the text fields if empty or not.
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
        // on below line we are creating
        // a variable for our modal class.
        final Person person = new Person();

        // on below line we are getting id for the course which we are storing.
        Number id = realm.where(Person.class).max("id");

        // on below line we ar
        // creating a variable for our id.
        long nextId;

        // validating if id is null or not.
        if (id == null) {
            // if id is null
            // we are passing it as 1.
            nextId = 1;
        } else {
            // if id is not null then
            // we are incrementing it by 1
            nextId = id.intValue() + 1;
        }
        // on below line we are setting the
        // data entered by user in our modal class.
        person.setId(nextId);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmail(email);
        person.setPhoneNumber(phoneNumber2);
        person.setAddress(address);

        // on below line we are calling a method to execute a transaction.
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // inside on execute method we are calling a method
                // to copy to real m database from our modal class.
                realm.copyToRealm(person);
            }
        });
    }
}



