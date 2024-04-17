package com.example.test1.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.test1.Model.Database;
import com.example.test1.R;

public class UserDash extends AppCompatActivity {

    private TextView usernameTextView;
    private TextView emailTextView;
    private Button buttonSignout;
    private Button ButtonInfo;
    private Button MainPage;

    private Database dbHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash);

        dbHelper = new Database(this);
        sharedPreferences = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        MainPage = findViewById(R.id.MainBooking);
        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.EmailTxT);
        buttonSignout = findViewById(R.id.btnSignout);
        ButtonInfo = findViewById(R.id.btnInfo);

        // Get the username from SharedPreferences and display it
        String username = getUsernameFromSession();
        usernameTextView.setText("Username: " + username);

        // Get the Email from SharedPreferences and display it
        String email = getEmailFromSession();
        emailTextView.setText("Email: " + email);


        buttonSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear user session and update session status in SharedPreferences
                dbHelper.endSession(UserDash.this);
                Toast.makeText(UserDash.this, "Log out Successful", Toast.LENGTH_SHORT).show();

                // Navigate back to the login screen
                Intent intent = new Intent(UserDash.this, LoginActivity.class);
                startActivity(intent);
                finish(); // This finishes the current activity (UserDash) and goes back to the previous activity (login)
            }
        });

        Button btnHistory = findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the HistoryPage activity
                Intent intent = new Intent(UserDash.this, HistoryPage.class);
                startActivity(intent);
            }
        });

        MainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the login screen
                Intent intent = new Intent(UserDash.this, MainBooking_page.class);
                startActivity(intent);
                finish(); // This finishes the current activity (UserDash) and goes back to the previous activity (login)
            }
        });


        ButtonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent = new Intent(UserDash.this, Userdetail.class);
             startActivity(intent);
             finish();

            }
        });
    }

    private String getUsernameFromSession() {
        // Retrieve the stored username from SharedPreferences
        return sharedPreferences.getString("username", "N/A");

    }

    private String getEmailFromSession() {
        // Retrieve the stored username from SharedPreferences
        return sharedPreferences.getString("email", "N/A");
    }
}