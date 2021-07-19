package com.example.contactapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.contactapp.adapter.CustomAdapter;
import com.example.contactapp.model.Person;
import com.example.contactapp.realmHelper.RealmHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.Realm;



public class MainActivity extends AppCompatActivity {


    Realm realm;
    ListView listView;
    RealmHelper realmHelper;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // create an instance of Realm
        realm = Realm.getDefaultInstance();


        // get required views from main act layout
        ImageView noContactImage = findViewById(R.id.no_contact_image);

        TextView contactText = findViewById(R.id.no_contact_text);

        FloatingActionButton fab = findViewById(R.id.fab);

        listView = findViewById(R.id.list_view);



        // set on click listener to the fab to navigate to the add Contact Activity
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContactNavigation();
            }
        });



        // create a Realm Helper class to get people object from realm database

        realmHelper = new RealmHelper(realm);
        realmHelper.selectFromDB();


        // instantiate previously created Custom Adapter class and pass in current activity as well as list of contacts

        final CustomAdapter adapter = new CustomAdapter(this, realmHelper.refresh());

        // set contact adapter to the list view

        listView.setAdapter(adapter);



        // set on item click listener to listener view to display the respected selected data onto  display activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(MainActivity.this, "You selected " + realmHelper.refresh().get(i).getFirstName(), Toast.LENGTH_SHORT).show();


                // create an Intent for main activity navigation
                Intent intent = new Intent(MainActivity.this, DisplayContact.class);

                // parse in user selected data to intent with putExtra and  get(position)  methods

                intent.putExtra("first_name", realmHelper.refresh().get(i).getFirstName());
                intent.putExtra("last_name", realmHelper.refresh().get(i).getLastName());
                intent.putExtra("email", realmHelper.refresh().get(i).getEmail());
                intent.putExtra("phone_number", realmHelper.refresh().get(i).getPhoneNumber());
                intent.putExtra("address", realmHelper.refresh().get(i).getAddress());


                startActivity(intent);


            }
        });

        // display no contact image as well as instructions if contact list empty else display list of contacts

        if (realmHelper.refresh().isEmpty()){
            listView.setVisibility(View.GONE);

        } else{
            contactText.setVisibility(View.GONE);

            noContactImage.setVisibility(View.GONE);
        }

    }


    public void addContactNavigation(){
        Intent addContactIntent = new Intent(getApplicationContext(), AddContact.class);
        startActivity(addContactIntent);
        finish();

    }
}