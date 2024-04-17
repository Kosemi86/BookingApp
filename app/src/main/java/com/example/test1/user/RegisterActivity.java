package com.example.test1.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.Admin.AdminLogin;
import com.example.test1.Model.Database;
import com.example.test1.R;
import com.example.test1.user.LoginActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {
    private EditText mName;
    private EditText mPassword;
    private EditText Mnumber;
    private EditText mEmail;
    private TextView Msignin;

    private TextView txtAdmin;
    private SharedPreferences sharedPreferences;
    private Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        mName = findViewById(R.id.etName);
        mPassword = findViewById(R.id.etPassword);
        Mnumber = findViewById(R.id.etMobileNumber);
        mEmail = findViewById(R.id.etEmail);
        Msignin = findViewById(R.id.tvLoginHint);
        txtAdmin = findViewById(R.id.txtAdmin);

        dbHelper = new Database(this); // Initialize dbHelper
        sharedPreferences = getSharedPreferences("userSession", Context.MODE_PRIVATE);

        // Onclick intent to login page
        Msignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        txtAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, AdminLogin.class);
                startActivity(intent);
            }
        });

        // Add user to the database when the user is registered DONE
        Button registerButton = findViewById(R.id.btnRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();

            }
        });
    }
    private void registerUser() {
        String name = mName.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String number = Mnumber.getText().toString().trim();
        String email = mEmail.getText().toString().trim();

        // Hash the password
        String hashedPassword = hashPassword(password);

        // Add user to the database with the hashed password
        dbHelper.addUser(name, hashedPassword, email, number);

        // Save user details in SharedPreferences
        saveUserSession(name, hashedPassword, email, number);

        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void saveUserSession(String username, String password, String email, String phoneNumber) {
        // Save the user details in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password); // Note: This is not recommended for storing passwords securely
        editor.putString("email", email);
        editor.putString("phoneNumber", phoneNumber);
        editor.apply();
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