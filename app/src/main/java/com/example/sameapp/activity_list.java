package com.example.sameapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.sameapp.dao.ContactDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class activity_list extends AppCompatActivity {

    private ContactAppDB db;
    private ContactDao contactDao;
    private ArrayList<Contact> contacts;

    ListView listView;
    CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        db= Room.databaseBuilder(getApplicationContext(),ContactAppDB.class,"ContactsDB")
                .allowMainThreadQueries().build();

        contactDao = db.contactDao();

        // add new contact:
        FloatingActionButton addContact = findViewById(R.id.addContactButton);

        addContact.setOnClickListener(view ->{
            Intent i = new Intent(this,ContactFormActivity.class);
            startActivity(i);
        });

        contacts = new ArrayList<Contact>();

        listView = findViewById(R.id.list_view);
        adapter = new CustomListAdapter(getApplicationContext(), contacts);

        listView.setAdapter(adapter);
        listView.setClickable(true);

        listView.setOnItemLongClickListener((adapterView, view, i, l)-> {
            //contacts.remove(i);
            Contact contact = contacts.remove(i);
            contactDao.delete(contact);
            adapter.notifyDataSetChanged();
            return true;
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);

                intent.putExtra("userName", contacts.get(i).getContactID());
             //   intent.putExtra("profilePicture", contacts.get(i).getProfilePicture());

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        contacts.clear();
        contacts.addAll(contactDao.index());
        adapter.notifyDataSetChanged();
    }
}