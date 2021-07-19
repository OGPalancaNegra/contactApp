package com.example.contactapp.realmHelper;

import com.example.contactapp.model.Person;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {


    Realm realm;
    RealmResults<Person> people;

    public RealmHelper(Realm realm){
        this.realm = realm;

    }

    public void selectFromDB(){
        people = realm.where(Person.class).findAll();

    }

    public ArrayList<Person> refresh(){
        ArrayList<Person> listitem = new ArrayList<>();
        for (Person p: people){
            listitem.add(p);
        }
        return listitem;
    }
}
