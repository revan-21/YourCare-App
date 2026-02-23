package com.example.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    // Constructor
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry1 = "CREATE TABLE users (username TEXT, email TEXT, password TEXT)";
        sqLiteDatabase.execSQL(qry1);

        String qry2 = "CREATE TABLE cart (username TEXT, product TEXT, price FLOAT, otype TEXT)";
        sqLiteDatabase.execSQL(qry2);

        String qry3 = "CREATE TABLE orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "fullname TEXT, " +
                "address TEXT, " +
                "pincode TEXT, " +
                "contact TEXT, " +
                "type TEXT, " +
                "datetime TEXT, " +
                "amount FLOAT, " +
                "status TEXT)";
        sqLiteDatabase.execSQL(qry3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS cart");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS orders");
        onCreate(sqLiteDatabase);
    }

    // -------------------------------
    // User Methods
    // -------------------------------
    public void register(String username, String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("email", email);
        cv.put("password", password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, cv);
        db.close();
    }

    public int login(String username, String password) {
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});
        if (c.moveToFirst()) result = 1;
        c.close();
        db.close();
        return result;
    }

    // -------------------------------
    // Cart Methods
    // -------------------------------
    public void addCart(String username, String product, float price, String otype) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("product", product);
        cv.put("price", price);
        cv.put("otype", otype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("cart", null, cv);
        db.close();
    }

    public int checkCart(String username, String product) {
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM cart WHERE username=? AND product=?", new String[]{username, product});
        if (c.moveToFirst()) result = 1;
        c.close();
        db.close();
        return result;
    }

    public void removeCart(String username, String otype) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart", "username=? AND otype=?", new String[]{username, otype});
        db.close();
    }

    public ArrayList<String> getCartData(String username, String otype) {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM cart WHERE username=? AND otype=?", new String[]{username, otype});
        if (c.moveToFirst()) {
            do {
                String product = c.getString(1);
                String price = c.getString(2);
                arr.add(product + "$" + price);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return arr;
    }

    public void removeSingleCartItem(String username, String product, String otype) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart", "username=? AND product=? AND otype=?", new String[]{username, product, otype});
        db.close();
    }

    // -------------------------------
    // Order Methods
    // -------------------------------
    public void addOrder(String username, String fullname, String address, String contact, int pincode, String date, String time, float amount, String type) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("fullname", fullname);
        values.put("address", address);
        values.put("pincode", String.valueOf(pincode));
        values.put("contact", contact);
        values.put("type", type);
        values.put("datetime", date + " " + time);
        values.put("amount", amount);
        values.put("status", "pending");
        db.insert("orders", null, values);
        db.close();
    }

    public ArrayList<String> getOrderData(String username) {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM orders WHERE username = ?", new String[]{username});
        if (c.moveToFirst()) {
            do {
                // Format: fullname$address$pincode$contact$type$datetime$amount$status
                String data =
                        c.getString(c.getColumnIndexOrThrow("fullname")) + "$" +
                                c.getString(c.getColumnIndexOrThrow("address")) + "$" +
                                c.getString(c.getColumnIndexOrThrow("pincode")) + "$" +
                                c.getString(c.getColumnIndexOrThrow("contact")) + "$" +
                                c.getString(c.getColumnIndexOrThrow("type")) + "$" +
                                c.getString(c.getColumnIndexOrThrow("datetime")) + "$" +
                                c.getFloat(c.getColumnIndexOrThrow("amount")) + "$" +
                                c.getString(c.getColumnIndexOrThrow("status"));
                arr.add(data);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return arr;
    }

    // ✅ NEW HELPER METHOD: Delete order by fullname (used in OrderDetailsActivity)
    public void deleteOrderByFullname(String username, String fullname) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("orders", "username=? AND fullname=?", new String[]{username, fullname});
        db.close();
    }

    // ✅ NEW METHOD: Check if appointment already exists
    public int checkAppointmentExists(String username, String fullname, String address, String contact, String date, String time) {
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM orders WHERE username=? AND fullname=? AND address=? AND contact=? AND datetime=? AND type=?",
                new String[]{username, fullname, address, contact, date + " " + time, "appointment"}
        );
        if (c.moveToFirst()) result = 1;
        c.close();
        db.close();
        return result;
    }
}
