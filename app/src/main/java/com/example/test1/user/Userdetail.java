package com.example.test1.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test1.Model.Database;
import com.example.test1.R;

public class Userdetail extends AppCompatActivity {

    private EditText editTextUsername, editTextEmail, editTextPhoneNumber;
    private Button btnUpdate;
    private SharedPreferences sharedPreferences;
    private ImageView home;
    private Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetail);


        dbHelper = new Database(this);
        sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);
        home = findViewById(R.id.Home);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        btnUpdate = findViewById(R.id.buttonUpdate);

        // Retrieve user details from SharedPreferences
        String username = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString("email", "");
        String phoneNumber = sharedPreferences.getString("phone_number", "");

        // Set retrieved values to EditText fields
        editTextUsername.setText(username);
        editTextEmail.setText(email);
        editTextPhoneNumber.setText(phoneNumber);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Userdetail.this, UserDash.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get updated user details from EditText fields
                String newUsername = editTextUsername.getText().toString();
                String newEmail = editTextEmail.getText().toString();
                String newPhoneNumber = editTextPhoneNumber.getText().toString();

                // Update user details in the database
                dbHelper.updateUserDetails(newUsername, newEmail, newPhoneNumber);

                // Update user details in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", newUsername);
                editor.putString("email", newEmail);
                editor.putString("phone_number", newPhoneNumber);
                editor.apply();

                Toast.makeText(Userdetail.this, "User details updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
