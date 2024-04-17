package com.example.test1.Admin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.test1.Model.Database;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.test1.R;


public class AddBarber extends Fragment {

    private EditText editTextName, editTextPhoneNumber, editTextEmail, editTextBio;
    private Button addButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addbarber, container, false);

        editTextName = view.findViewById(R.id.BaberName);
        editTextPhoneNumber = view.findViewById(R.id.BaberNumber);
        editTextEmail = view.findViewById(R.id.BarberEmail);
        editTextBio = view.findViewById(R.id.BarberBio);
        addButton = view.findViewById(R.id.AddBarber);

        // Set OnClickListener for the addButton
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from EditText fields
                String name = editTextName.getText().toString();
                String phoneNumber = editTextPhoneNumber.getText().toString();
                String email = editTextEmail.getText().toString();
                String bio = editTextBio.getText().toString();

                // Get the context from the View
                Context context = v.getContext();
                // Call the addBarber method from your database helper
                Database dbHelper = new Database(context);

                dbHelper.addBarber(name, phoneNumber, email, bio);

                // Optional: Show a message to the user indicating success or failure
                // For example, you can use Toast
                 Toast.makeText(context, "Barber added successfully!", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
