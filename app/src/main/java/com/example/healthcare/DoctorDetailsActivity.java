package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {

    private String[][] doctor_details1 = {
            {"Doctor Name: Ajit Saste", "Hospital Address: Pimpri", "Exp: 5yrs", "Mobile No:9898989898", "680"},
            {"Doctor Name: Prasad Pawar", "Hospital Address: Nigdi", "Exp: 15yrs", "Mobile No:7898989898", "980"},
            {"Doctor Name: Swapnil Kale", "Hospital Address: Pune", "Exp: 8yrs", "Mobile No:8898989898", "300"},
            {"Doctor Name: Deepak Deshmukh", "Hospital Address: Chinchwad", "Exp: 6yrs", "Mobile No:9898000000", "500"},
            {"Doctor Name: Ashok Panda", "Hospital Address: Katraj", "Exp: 7yrs", "Mobile No:7798989898", "800"}
    };

    private String[][] doctor_details2 = {
            {"Doctor Name: Ramesh Patil", "Hospital Address: Wakad", "Exp: 10yrs", "Mobile No:9998989898", "750"},
            {"Doctor Name: Suresh Kumar", "Hospital Address: Baner", "Exp: 12yrs", "Mobile No:9798989898", "950"},
            {"Doctor Name: Neha Kulkarni", "Hospital Address: Aundh", "Exp: 7yrs", "Mobile No:8898989898", "650"},
            {"Doctor Name: Vishal Jadhav", "Hospital Address: Pimple Saudagar", "Exp: 9yrs", "Mobile No:9898000000", "850"},
            {"Doctor Name: Ashwini Giri", "Hospital Address: Kothrud", "Exp: 8yrs", "Mobile No:7798989898", "900"}
    };

    private String[][] doctor_details3 = {
            {"Doctor Name: Sneha Shah", "Hospital Address: Shivaji Nagar", "Exp: 5yrs", "Mobile No:9898989898", "700"},
            {"Doctor Name: Rahul Patankar", "Hospital Address: Deccan", "Exp: 15yrs", "Mobile No:7898989898", "1000"},
            {"Doctor Name: Pooja Desai", "Hospital Address: Pune", "Exp: 8yrs", "Mobile No:8898989898", "750"},
            {"Doctor Name: Sandeep Joshi", "Hospital Address: Chinchwad", "Exp: 6yrs", "Mobile No:9898000000", "550"},
            {"Doctor Name: Anjali More", "Hospital Address: Katraj", "Exp: 7yrs", "Mobile No:7798989898", "850"}
    };

    private String[][] doctor_details4 = {
            {"Doctor Name: Deepa Mehta", "Hospital Address: Pimpri", "Exp: 5yrs", "Mobile No:9898989898", "680"},
            {"Doctor Name: Arjun Rane", "Hospital Address: Nigdi", "Exp: 15yrs", "Mobile No:7898989898", "980"},
            {"Doctor Name: Mahesh Kale", "Hospital Address: Pune", "Exp: 8yrs", "Mobile No:8898989898", "300"},
            {"Doctor Name: Shubham Deshmukh", "Hospital Address: Chinchwad", "Exp: 6yrs", "Mobile No:9898000000", "500"},
            {"Doctor Name: Kiran Patil", "Hospital Address: Katraj", "Exp: 7yrs", "Mobile No:7798989898", "800"}
    };

    private String[][] doctor_details5 = {
            {"Doctor Name: Ajit Saste", "Hospital Address: Pimpri", "Exp: 5yrs", "Mobile No:9898989898", "680"},
            {"Doctor Name: Prasad Pawar", "Hospital Address: Nigdi", "Exp: 15yrs", "Mobile No:7898989898", "980"},
            {"Doctor Name: Swapnil Kale", "Hospital Address: Pune", "Exp: 8yrs", "Mobile No:8898989898", "300"},
            {"Doctor Name: Deepak Deshmukh", "Hospital Address: Chinchwad", "Exp: 6yrs", "Mobile No:9898000000", "500"},
            {"Doctor Name: Ashok Panda", "Hospital Address: Katraj", "Exp: 7yrs", "Mobile No:7798989898", "800"}
    };

    TextView tv;
    Button btn;
    String[][] doctor_details = {};
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tv = findViewById(R.id.textViewDDTitle);
        btn = findViewById(R.id.buttonBMDBack);

        Intent it = getIntent();
        String title = it.getStringExtra("title");

        tv.setText(title);

        if (title.compareTo("Family Physicians") == 0)
            doctor_details = doctor_details1;
        else if (title.compareTo("Dietician") == 0)
            doctor_details = doctor_details2;
        else if (title.compareTo("Dentist") == 0)
            doctor_details = doctor_details3;
        else if (title.compareTo("Surgeon") == 0)
            doctor_details = doctor_details4;
        else
            doctor_details = doctor_details5;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));
            }
        });

        list = new ArrayList<>();

        for (int i = 0; i < doctor_details.length; i++) {
            HashMap<String, String> item = new HashMap<>();
            item.put("line1", doctor_details[i][0]);
            item.put("line2", doctor_details[i][1]);
            item.put("line3", doctor_details[i][2]);
            item.put("line4", doctor_details[i][3]);
            item.put("line5", "Cons Fees: " + doctor_details[i][4] + "/-");
            list.add(item);
        }

        sa = new SimpleAdapter(
                this,
                list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e}
        );

        ListView lst = findViewById(R.id.editTestLTTestMultiLine);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(DoctorDetailsActivity.this, BookAppointmentActivity.class);
                it.putExtra("text1", title);
                it.putExtra("text2", doctor_details[i][0]);
                it.putExtra("text3", doctor_details[i][1]);
                it.putExtra("text4", doctor_details[i][3]);
                it.putExtra("text5", doctor_details[i][4]);
                startActivity(it);
            }
        });
    }
}
