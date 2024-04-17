package com.example.test1.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test1.Model.Database;
import com.example.test1.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;


    private Button cirLoginButton;

    private Database dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new Database(this);


        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        cirLoginButton = findViewById(R.id.cirLoginButton);


        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if (isValidLogin(username, password)) {

                    // Navigate to the UserDash activity
                    Intent intent = new Intent(LoginActivity.this, UserDash.class);
                    startActivity(intent);
                    finish(); // Finish the LoginActivity to prevent going back
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid login", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private boolean isValidLogin(String username, String password) {
        // Hash the entered password for comparison
        String hashedPassword = hashPassword(password);

        // Check credentials with the hashed password
        return dbHelper.checkCredentials(username, hashedPassword);
    }



    // Method to hash the password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert hashed bytes to a hexadecimal string
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
