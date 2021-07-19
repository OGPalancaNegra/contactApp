package com.example.contactapp.database;

import android.app.Application;

import com.example.contactapp.model.Person;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmDb extends Application {


    @Override
    public void onCreate() {
        super.onCreate();


        // initialize realm database.
        Realm.init(this);

        //  set realm configuration
        RealmConfiguration config =
                new RealmConfiguration.Builder()

                        // write data to database on ui thread.
                        .allowWritesOnUiThread(true)

                        // delete realm if migration is needed.
                        .deleteRealmIfMigrationNeeded()
                        // call method to build.
                        .build();

        // set configuration to our realm database.
        Realm.setDefaultConfiguration(config);
    }



}
