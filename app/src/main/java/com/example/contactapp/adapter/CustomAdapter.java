package com.example.contactapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.contactapp.R;
import com.example.contactapp.model.Person;

import java.util.ArrayList;



public class CustomAdapter extends BaseAdapter {


    Context c;
    ArrayList<Person> contacts;

    public CustomAdapter(Context c, ArrayList<Person> contacts){
        this.c = c;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item, parent, false);

        TextView firstNameTV;

        firstNameTV = view.findViewById(R.id.first_name_tv);

        Person p = (Person) this.getItem(position);

        firstNameTV.setText(p.getFirstName());


        return view;
    }
}
