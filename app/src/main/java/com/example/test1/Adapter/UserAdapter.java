package com.example.test1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test1.Model.Database;
import com.example.test1.Model.User;
import com.example.test1.R;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

    private Database dbHelper;
    public UserAdapter(Context context, int user_details_item, List<User> users, Database dbHelper) {
        super(context, 0, users);
        this.dbHelper = new Database(this.getContext()); // Initialize dbHelper

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_details_item, parent, false);
        }

        ImageButton btnDeleteItem = convertView.findViewById(R.id.btnDeleteItem);
        btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null)
                    dbHelper.deleteUser(user.getId());
                remove(user);
                notifyDataSetChanged();
                Toast.makeText(getContext(), "Delete button clicked for item " + position, Toast.LENGTH_SHORT).show();
            }
        });

        TextView userNameTextView = convertView.findViewById(R.id.userNameTextView);
        TextView userEmailTextView = convertView.findViewById(R.id.userEmailTextView);
        TextView userMobileTextView = convertView.findViewById(R.id.userMobileTextView);
        TextView userPasswordTextView = convertView.findViewById(R.id.userPasswordTextView);

        if (user != null) {
            userNameTextView.setText("Name: " + user.getUsername());
            userEmailTextView.setText("Email: " + user.getEmail());
            userMobileTextView.setText("Mobile: " + user.getPhoneNumber());
            userPasswordTextView.setText("Password: " + user.getPassword()); // Displaying hashed password
        }

        return convertView;
    }
}
