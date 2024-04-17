package com.example.test1.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.test1.Adapter.UserAdapter;
import com.example.test1.Model.Database;
import com.example.test1.Model.User;
import com.example.test1.R;

import android.widget.ListView;
import java.util.List;

public class userDetails extends AppCompatActivity {
    private Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_details);
        dbHelper = new Database(this); // Initialize dbHelper

        // Get all users from the database
        List<User> userList = dbHelper.getAllUsers();

        // Create a custom adapter to display user information
        UserAdapter adapter = new UserAdapter(this, R.layout.user_details_item, userList, dbHelper);

        // Set the adapter for the ListView
        ListView listView = findViewById(R.id.listViewUsers);
        listView.setAdapter(adapter);
    }
}