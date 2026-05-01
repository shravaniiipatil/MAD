package com.example.digitalbusinesscard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnCreate, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreate = findViewById(R.id.btnCreate);
        btnView = findViewById(R.id.btnView);

        btnCreate.setOnClickListener(v ->
                startActivity(new Intent(this, CreateCardActivity.class)));

        btnView.setOnClickListener(v ->
                startActivity(new Intent(this, ViewCardsActivity.class)));
    }
}