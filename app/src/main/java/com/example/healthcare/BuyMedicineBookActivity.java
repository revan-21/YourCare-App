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

public class BuyMedicineBookActivity extends AppCompatActivity {

    EditText edname, edaddress, edcontact, edpincode;
    Button btnBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_book);

        edname = findViewById(R.id.editTextBMBFullname);
        edaddress = findViewById(R.id.editTextBMBAddress);
        edcontact = findViewById(R.id.editTextBMBContact);
        edpincode = findViewById(R.id.editTextBMBPincode);
        btnBooking = findViewById(R.id.buttonBMBBooking);

        Intent intent = getIntent();
        final String priceStr = intent.getStringExtra("price");  // get price string
        final String date = intent.getStringExtra("date");       // get date string

        // Convert price string to float and make final for inner class
        float tempPrice = 0;
        if (priceStr != null && priceStr.contains(":")) {
            try {
                tempPrice = Float.parseFloat(priceStr.split(":")[1].trim());
            } catch (NumberFormatException e) {
                tempPrice = 0;
            }
        }
        final float price = tempPrice;

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edname.getText().toString();
                String address = edaddress.getText().toString();
                String contact = edcontact.getText().toString();
                String pincodeStr = edpincode.getText().toString();

                if (name.isEmpty() || address.isEmpty() || contact.isEmpty() || pincodeStr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                    return;
                }

                int pincode = 0;
                try {
                    pincode = Integer.parseInt(pincodeStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid pincode", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username", "");

                Database db = new Database(getApplicationContext(), "healthcare", null, 1);

                db.addOrder(
                        username,
                        name,
                        address,
                        contact,
                        pincode,
                        date,
                        "", // time left empty
                        price,
                        "medicine"
                );

                db.removeCart(username, "medicine");

                Toast.makeText(getApplicationContext(), "Your booking is done successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(BuyMedicineBookActivity.this, HomeActivity.class));
                finish();
            }
        });
    }
}
