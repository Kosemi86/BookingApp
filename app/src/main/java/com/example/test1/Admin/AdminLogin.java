package com.example.test1.Admin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.test1.R;


public class AdminLogin extends AppCompatActivity {

    private Button adminLogin;
    private EditText adminUsername;
    private EditText adminPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminUsername = findViewById(R.id.adminUsername); // assuming you have an EditText for admin username
        adminPassword = findViewById(R.id.adminPassword); // assuming you have an EditText for admin password
        adminLogin = findViewById(R.id.adminLogin);

        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered username and password
                String enteredUsername = adminUsername.getText().toString();
                String enteredPassword = adminPassword.getText().toString();

                // Check if the entered username and password match the admin credentials
                if (isValidAdmin(enteredUsername, enteredPassword)) {
                    Toast.makeText(AdminLogin.this, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminLogin.this, AdminDash.class);
                    startActivity(intent);
                    finish(); // finish current activity to prevent going back to login screen
                } else {
                    Toast.makeText(AdminLogin.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Check if the entered username and password match the admin credentials
    private boolean isValidAdmin(String username, String password) {
        // Check against your actual admin credentials
        return "Admin".equals(username) && "Admin".equals(password);
    }
}
