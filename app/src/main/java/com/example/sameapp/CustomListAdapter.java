package com.example.sameapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.sameapp.dao.UserDao;
import com.example.sameapp.models.Contact;
import com.example.sameapp.models.User;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<Contact> {
    LayoutInflater inflater;
    private ContactAppDB db;
    private UserDao userDao;

    public CustomListAdapter(Context ctx, ArrayList<Contact> contactArrayList) {
        super(ctx, R.layout.custom_list_item, contactArrayList);
        this.db = Room.databaseBuilder(ctx.getApplicationContext(), ContactAppDB.class, "ContactsDB")
                .allowMainThreadQueries().build();

        this.userDao = db.userDao();
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Contact contact = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_list_item, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.profile_image);
        TextView userName = (TextView) convertView.findViewById(R.id.user_name);
        TextView lastMsg = (TextView) convertView.findViewById(R.id.last_massage);
        TextView time = (TextView) convertView.findViewById(R.id.time);

        userName.setText(contact.getContactID());
        lastMsg.setText(contact.getLastMassage());
        time.setText(contact.getLastMassageSendingTime());

        String userNameId = userName.getText().toString();
        // get the user to restore his profile picture:
        User user = userDao.get(userNameId);
        String profileImage = user.getPictureId();
        Uri profileUri = Uri.parse(profileImage);

        //imageView.setImageURI(profileUri);
        imageView.setImageURI(null);

        return convertView;
    }
}
