package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LabTestBookActivity extends AppCompatActivity {

    EditText edFullname, edAddress, edPincode, edContact;
    Button btnBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_book);

        // Bind XML views
        edFullname = findViewById(R.id.editTextBMBFullname);
        edAddress = findViewById(R.id.editTextBMBAddress);
        edPincode = findViewById(R.id.editTextBMBPincode);
        edContact = findViewById(R.id.editTextBMBContact);
        btnBooking = findViewById(R.id.buttonBMBBooking);

        // Retrieve Intent data
        Intent intent = getIntent();
        final String price = intent.getStringExtra("price") != null ? intent.getStringExtra("price") : "0";
        final String date = intent.getStringExtra("date") != null ? intent.getStringExtra("date") : "";
        final String time = intent.getStringExtra("time") != null ? intent.getStringExtra("time") : "";

        // Get current username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        final String username = sharedPreferences.getString("username", "");

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname = edFullname.getText().toString().trim();
                String address = edAddress.getText().toString().trim();
                String pincode = edPincode.getText().toString().trim();
                String contact = edContact.getText().toString().trim();

                if (fullname.isEmpty() || address.isEmpty() || pincode.isEmpty() || contact.isEmpty()) {
                    Toast.makeText(LabTestBookActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Database db = new Database(getApplicationContext(), "healthcare", null, 1);

                    // ✅ 1. Insert booking record
                    db.addOrder(
                            username,
                            fullname,
                            address,
                            pincode,
                            Integer.parseInt(contact),
                            "Lab Test Booking",
                            date + " " + time,
                            Float.parseFloat(price),
                            "booked"
                    );

                    // ✅ 2. Remove all previous lab test cart items
                    db.removeCart(username, "lab");

                    Toast.makeText(getApplicationContext(), "Your Lab Test Booking is Successful!", Toast.LENGTH_LONG).show();

                    // ✅ 3. Go back to HomeActivity
                    Intent intent = new Intent(LabTestBookActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Booking failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
