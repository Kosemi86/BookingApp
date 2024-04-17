package com.example.test1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test1.Model.Appointment;
import com.example.test1.Model.Database;
import com.example.test1.R;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<Appointment> {
    private Database dbHelper;
    private Context context;

    public HistoryAdapter(Context context, List<Appointment> appointments, Database dbHelper) {
        super(context, 0, appointments);
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Appointment appointment = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_history, parent, false);
        }

        // Lookup view for data population
        TextView textViewBarberName = convertView.findViewById(R.id.textViewBarberName);
        TextView textViewDetails = convertView.findViewById(R.id.textViewDetails);
        ImageView btnDeleteAppointment = convertView.findViewById(R.id.btnDeleteAppointment);
        ImageView btnChangeAppointment = convertView.findViewById(R.id.btnChangeAppointment);
        String barberName = dbHelper.getBarberNameById(String.valueOf(appointment.getBarberId()));
        // Populate the data into the template view using the data object
        textViewBarberName.setText("Barber Name: " + barberName);
        textViewDetails.setText("Details: " + appointment.getAppointmentDetails());

        // Set click listener for the delete button
        btnDeleteAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete appointment from the database
                dbHelper.deleteAppointment(appointment.getId());
                // Remove the item from the list view
                remove(appointment);
                // Notify the adapter that the data set has changed
                notifyDataSetChanged();
            }
        });

        // Set click listener for the change appointment button
        btnChangeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the dialog layout
                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_description, null);

                // Initialize views
                EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);

                // Set current description
                editTextDescription.setText(appointment.getAppointmentDetails());

                // Create AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialogView)
                        .setTitle("Update Description")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Get updated description
                                String updatedDescription = editTextDescription.getText().toString().trim();
                                // Update appointment in the database
                                dbHelper.updateAppointmentDescription(appointment.getId(), updatedDescription);
                                // Notify the adapter that the data set has changed
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                // Show dialog
                builder.create().show();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
