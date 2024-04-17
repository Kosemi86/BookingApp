package com.example.test1.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.test1.R;
import com.example.test1.user.LoginActivity;
import com.example.test1.user.RegisterActivity;

public class AdminDash extends AppCompatActivity {

    CardView CardUser;
    CardView CardHistory;
    CardView CardBarber;
    CardView CardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);

        CardUser = findViewById(R.id.CardUser);
        CardHistory = findViewById(R.id.CardHistory);
        CardBarber = findViewById(R.id.CardBarber);
        CardLogout = findViewById(R.id.CardLogout);

        CardUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(AdminDash.this, userDetails.class);
            startActivity(intent);
            finish();
            }
        });


        CardHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //views all recent bookings
            }
        });



        CardBarber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a FragmentTransaction to replace the current fragment with the new one
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // Replace the current fragment with the new one
                transaction.replace(R.id.fragmentContainer, new AddBarber());

                // If you want to add the transaction to the back stack, you can use:
                // transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });


        CardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logout
            }
        });
    }


}