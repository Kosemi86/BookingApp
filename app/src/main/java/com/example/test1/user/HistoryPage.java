package com.example.test1.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test1.Adapter.HistoryAdapter;
import com.example.test1.Model.Appointment;
import com.example.test1.Model.Database;
import com.example.test1.R;

import java.util.List;

public class HistoryPage extends AppCompatActivity {

    private Database dbHelper;
    private ListView historyListView;
    private TextView noAppointmentsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);

        // Initialize views
        historyListView = findViewById(R.id.historyListView);
        noAppointmentsTextView = findViewById(R.id.noAppointmentsTextView);

        // Initialize Database
        dbHelper = new Database(this);

        // Retrieve all appointments from the database
        List<Appointment> appointments = dbHelper.getAllAppointments();

        if (appointments.isEmpty()) {
            // Show the placeholder text
            noAppointmentsTextView.setVisibility(View.VISIBLE);
            historyListView.setVisibility(View.GONE);
        } else {
            // Hide the placeholder text
            noAppointmentsTextView.setVisibility(View.GONE);
            historyListView.setVisibility(View.VISIBLE);

            // Create a custom adapter to populate the ListView
            HistoryAdapter adapter = new HistoryAdapter(this, appointments, dbHelper);

            // Set the adapter to the ListView
            historyListView.setAdapter(adapter);
        }
    }
}
