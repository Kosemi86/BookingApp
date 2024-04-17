package com.example.test1.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.test1.Model.Database;
import com.example.test1.R;

import java.io.IOException;
import java.util.List;

public class MainBooking_page extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Database dbHelper;
    private EditText message;
    private ImageView addPhotoImageView,accountIconImageView;
    private Bitmap selectedImageBitmap;
    private ImageView homeIconImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_booking_page);

        // Initialize views
        Spinner spinnerBarbers = findViewById(R.id.spinnerBarbers);
        message = findViewById(R.id.messageEditText);
        homeIconImageView = findViewById(R.id.homeIconImageView);
        accountIconImageView = findViewById(R.id.accountIconImageView);
        addPhotoImageView = findViewById(R.id.addPhotoImageView);
        Button bookNowButton = findViewById(R.id.bookNowButton);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        // Initialize Database
        dbHelper = new Database(this);

        // Populate Spinner with barber names
        List<String> barberNames = dbHelper.getAllBarberNames();
        ArrayAdapter<String> barberAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, barberNames);
        barberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBarbers.setAdapter(barberAdapter);


        homeIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the user dashboard activity
                Intent intent = new Intent(MainBooking_page.this, UserDash.class);
                startActivity(intent);
            }
        });

        accountIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the user dashboard activity
                Intent intent = new Intent(MainBooking_page.this, HistoryPage.class);
                startActivity(intent);
            }
        });





        // Set click listener for add photo ImageView
        addPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch intent to pick an image from the gallery
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });


        // Inside the onClick() method of your bookNowButton.setOnClickListener()
        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected barber ID
                int selectedBarberPosition = spinnerBarbers.getSelectedItemPosition();
                // Assuming you have a method to get barber ID based on position
                long barberId = getBarberId(selectedBarberPosition);

                // Get user input from EditText
                String appointmentDetails = message.getText().toString().trim(); // Corrected line

                // Ensure barberId is valid
                if (barberId != 0 && !appointmentDetails.isEmpty()) {
                    // Get rating from RatingBar if needed
                    float appointmentRating = ratingBar.getRating(); // Assuming you have a rating bar

                    // Insert appointment into database
                    dbHelper.insertAppointment(String.valueOf(barberId), String.valueOf(appointmentRating), appointmentDetails);

                    // Optionally, you can reset the form after booking
                    message.setText("");
                    addPhotoImageView.setImageBitmap(null);

                    // Inflate the success confirmation layout
                    LayoutInflater inflater = LayoutInflater.from(MainBooking_page.this);
                    View successLayout = inflater.inflate(R.layout.confirmation, null);

                    // Create a dialog to display the success layout
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainBooking_page.this);
                    builder.setView(successLayout);
                    AlertDialog dialog = builder.create();

                    // Find the Continue button in the success layout and set a click listener
                    Button continueButton = successLayout.findViewById(R.id.continueButton);
                    continueButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Dismiss the dialog
                            dialog.dismiss();
                        }
                    });

                    // Show the dialog
                    dialog.show();
                } else {
                    // Show a message to the user indicating that barber ID or appointment details are missing
                    Toast.makeText(MainBooking_page.this, "Please select a barber and enter appointment details.", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            System.out.println("Selected image URI: " + data.getData());
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Do something with the selected image bitmap, such as displaying it in an ImageView
            addPhotoImageView.setImageBitmap(selectedImageBitmap);
        }
    }

    // Method to get barber ID based on spinner position
    private long getBarberId(int position) {
        return position + 1;
    }
}
