package com.example.digitalbusinesscard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_card);

        TextView tvName = findViewById(R.id.tvName);
        TextView tvPhone = findViewById(R.id.tvPhone);
        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvCompany = findViewById(R.id.tvCompany);
        Button btnShare = findViewById(R.id.btnShare);

        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String email = getIntent().getStringExtra("email");
        String company = getIntent().getStringExtra("company");

        tvName.setText(name);
        tvPhone.setText(phone);
        tvEmail.setText(email);
        tvCompany.setText(company);

        btnShare.setOnClickListener(v -> {
            String data = "Name: " + name +
                    "\nPhone: " + phone +
                    "\nEmail: " + email +
                    "\nCompany: " + company;

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, data);

            startActivity(Intent.createChooser(share, "Share via"));
        });
    }
}