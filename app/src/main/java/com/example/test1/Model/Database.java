package com.example.test1.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DBName = "barber_app";
    private static final String PREF_USER_LOGGED_IN = "user_logged_in";
    private static final int DBVersion = 27;

    private static final String TABLE_USERS =
            "CREATE TABLE IF NOT EXISTS Users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT ," +
                    "password TEXT ," +
                    "email TEXT ," +
                    "phone_number TEXT )";


    private static final String TABLE_BARBERS =
            "CREATE TABLE IF NOT EXISTS Barbers (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT ," +
                    "phone_number TEXT ," +
                    "email TEXT ," +
                    "bio TEXT)";

        private static final String TABLE_APPOINTMENTS =
                "CREATE TABLE IF NOT EXISTS Appointments (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "barber_id INTEGER NOT NULL," +
                        "appointment_Rating TEXT NOT NULL," +
                        "appointment_Details TEXT NOT NULL," +
                        "FOREIGN KEY (barber_id) REFERENCES Barbers(id))";

    private static final String TABLE_USER_CREDENTIALS =
            "CREATE TABLE IF NOT EXISTS UserCredentials (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "username TEXT NOT NULL," +
                    "password TEXT NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES Users(id))";


    public Database(Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USERS);
        db.execSQL(TABLE_BARBERS);
        db.execSQL(TABLE_APPOINTMENTS);
        db.execSQL(TABLE_USER_CREDENTIALS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing tables and recreate them
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Barbers");
        db.execSQL("DROP TABLE IF EXISTS Appointments");
        db.execSQL("DROP TABLE IF EXISTS UserCredentials");

        onCreate(db);
    }


    public List<User> getAllUsers() {
        SQLiteDatabase db = getReadableDatabase();
        List<User> users = new ArrayList<>();
        String[] columns = {"id", "username", "password", "email", "phone_number"}; // Add all user columns

        Cursor cursor = db.query("Users", columns, null, null, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    long userId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                    String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                    String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                    String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));

                    User user = new User(userId, username, password, email, phoneNumber);
                    users.add(user);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            // Consider closing the database in a more controlled manner
            // db.close(); // Depending on your application flow and requirements
        }

        return users;
    }







    public void insertAppointment(String barberId, String appointmentRating, String appointmentDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            // Remove user_id from the ContentValues

            values.put("barber_id", barberId);
            values.put("appointment_Rating", appointmentRating);
            values.put("appointment_Details", appointmentDetails);

            // Insert into Appointments table
            db.insert("Appointments", null, values);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    public List<Appointment> getAllAppointments() {
        SQLiteDatabase db = getReadableDatabase();
        List<Appointment> appointments = new ArrayList<>();

        String[] columns = {"id", "barber_id", "appointment_Rating", "appointment_Details"}; // Add all appointment columns

        Cursor cursor = db.query("Appointments", columns, null, null, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    long appointmentId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                    long barberId = cursor.getLong(cursor.getColumnIndexOrThrow("barber_id"));
                    String appointmentRating = cursor.getString(cursor.getColumnIndexOrThrow("appointment_Rating"));
                    String appointmentDetails = cursor.getString(cursor.getColumnIndexOrThrow("appointment_Details"));

                    // Create an Appointment object with retrieved details
                    Appointment appointment = new Appointment(appointmentId, barberId, appointmentRating, appointmentDetails);
                    appointments.add(appointment);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            // Consider closing the database in a more controlled manner
            // db.close(); // Depending on your application flow and requirements
        }

        return appointments;
    }


    public void deleteAppointment(long appointmentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            // Delete the appointment from the Appointments table
            db.delete("Appointments", "id=?", new String[]{String.valueOf(appointmentId)});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public String getBarberNameById(String barberId) {
        SQLiteDatabase db = getReadableDatabase();
        String barberName = null;

        // Query the Barbers table to retrieve the barber name by ID
        Cursor cursor = db.query("Barbers", new String[]{"name"}, "id=?", new String[]{barberId}, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                barberName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            }
        } finally {
            cursor.close();
        }

        return barberName;
    }



    public void updateAppointmentDescription(long appointmentId, String newDetails) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put("appointment_Details", newDetails);

            // Update the appointment details in the Appointments table
            db.update("Appointments", values, "id=?", new String[]{String.valueOf(appointmentId)});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }




    public void addBarber(String name, String phoneNumber, String email, String bio) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            // Insert into TABLE_BARBERS
            ContentValues barberValues = new ContentValues();
            barberValues.put("name", name);
            barberValues.put("phone_number", phoneNumber);
            barberValues.put("email", email);
            barberValues.put("bio", bio);

            long barberId = db.insert("Barbers", null, barberValues);



            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }



    public List<String> getAllBarberNames() {
        SQLiteDatabase db = getReadableDatabase();
        List<String> barberNames = new ArrayList<>();

        Cursor cursor = db.query("Barbers", new String[]{"name"}, null, null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String barberName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    barberNames.add(barberName);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        return barberNames;
    }


    public void updateUserDetails(String username, String email, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("phone_number", phoneNumber);

        try {
            // Update the user details in the Users table
            db.update("Users", values, "username=?", new String[]{username});
        } catch (SQLiteException e) {
            // Handle any exceptions here
        } finally {
            db.close();
        }
    }



    public void addUser(String username, String password, String email, String phone_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            // Insert into Users table
            ContentValues userValues = new ContentValues();
            userValues.put("username", username);
            userValues.put("password", password);
            userValues.put("email", email);
            userValues.put("phone_number", phone_number);

            long userId = db.insert("Users", null, userValues);

            // Insert into UserCredentials table
            ContentValues credentialsValues = new ContentValues();
            credentialsValues.put("user_id", userId);
            credentialsValues.put("username", username);
            credentialsValues.put("password", password);

            db.insert("UserCredentials", null, credentialsValues);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    public boolean checkCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String[] columns = {"id"};
            String selection = "username=? AND password=?";
            String[] selectionArgs = {username, password};

            cursor = db.query("UserCredentials", columns, selection, selectionArgs, null, null, null);
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public void endSession(Context context) {
        // Get SharedPreferences instance
        SharedPreferences preferences = context.getSharedPreferences("userSession", Context.MODE_PRIVATE);
        // Set logged-in status to false
        preferences.edit().putBoolean(PREF_USER_LOGGED_IN, false).apply();
    }





    public void deleteUser(long userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            // Delete from Users table
            db.delete("Users", "id=?", new String[]{String.valueOf(userId)});

            // Delete from UserCredentials table
            db.delete("UserCredentials", "user_id=?", new String[]{String.valueOf(userId)});

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }



}
