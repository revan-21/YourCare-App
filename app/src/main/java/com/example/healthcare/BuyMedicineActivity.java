package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyMedicineActivity extends AppCompatActivity {

    // Medicine packages: {Name, "", "", "", Price}
    private String[][] packages = {
            {"Uprise-D3 1000IU Capsule", "", "", "", "50"},
            {"HealthVit Chromium Picolinate 200mcg Capsule", "", "", "", "305"},
            {"Vitamin B Complex Capsules", "", "", "", "448"},
            {"Inlife Vitamin E Wheat Germ Oil Capsule", "", "", "", "539"},
            {"Dolo 650 Tablet", "", "", "", "30"},
            {"Crocin 650 Advance Tablet", "", "", "", "50"},
            {"Strepsils Medicated Lozenges for Sore Throat", "", "", "", "40"},
            {"Tata 1mg Calcium + Vitamin D3", "", "", "", "30"},
            {"Feronia -XT Tablet", "", "", "", "130"}
    };

    // Details for each package
    private String[] package_details = {
            "Building and keeping the bones & teeth strong\nReducing Fatigue/stress and muscular pains\nBoosting immunity and increasing resistance against infection",
            "Chromium is an essential trace mineral that plays an important role in helping insulin regulate blood sugar",
            "Provides relief from vitamin B deficiencies\nHelps in formation of red blood cells\nMaintains healthy nervous system",
            "It promotes health as well as skin benefit.\nIt helps reduce skin blemish and pigmentation.\nIt acts as safeguard the skin from the harsh UVA and UVB sun rays.",
            "Dolo 650 Tablet helps relieve pain and fever by blocking the release of certain chemical messengers",
            "Helps relieve fever and bring down a high temperature\nSuitable for people with a heart condition or high blood pressure",
            "Relieves the symptoms of a bacterial throat infection and soothes the recovery process\nProvides a warm and comforting feeling during sore throat",
            "Reduces the risk of calcium deficiency, Rickets, and Osteoporosis\nPromotes mobility and flexibility of joints",
            "Helps to reduce the iron deficiency due to chronic blood loss or low intake of iron"
    };

    HashMap<String, String> item;
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;
    ListView lst;
    Button btnBack, btnGoToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

        lst = findViewById(R.id.listViewBM);
        btnBack = findViewById(R.id.buttonBMDBack);
        btnGoToCart = findViewById(R.id.buttonBMDAddToCart);

        btnGoToCart.setOnClickListener(view -> {
            startActivity(new Intent(BuyMedicineActivity.this, CartBuyMedicineActivity.class));
        });

        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(BuyMedicineActivity.this, HomeActivity.class));
            finish();
        });

        list = new ArrayList<>();

        for (int i = 0; i < packages.length; i++) {
            item = new HashMap<>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", "Total Cost: " + packages[i][4] + "/-");
            list.add(item);
        }

        sa = new SimpleAdapter(
                this,
                list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e}
        );

        lst.setAdapter(sa);

        lst.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent it = new Intent(BuyMedicineActivity.this, BuyMedicineDetailsActivity.class);
            it.putExtra("text1", packages[i][0]);
            it.putExtra("text2", package_details[i]);
            it.putExtra("text3", packages[i][4]);
            startActivity(it);
        });
    }
}
