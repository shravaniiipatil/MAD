package com.example.digitalbusinesscard;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class CreateCardActivity extends AppCompatActivity {

    EditText etName, etPhone, etEmail, etCompany;
    Button btnSave;
    DBHelper dbHelper;

    private static final String CHANNEL_ID = "card_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        // Initialize views
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etCompany = findViewById(R.id.etCompany);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new DBHelper(this);

        btnSave.setOnClickListener(v -> saveCard());
    }

    private void saveCard() {

        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String company = etCompany.getText().toString().trim();

        // ✅ Validation
        if (TextUtils.isEmpty(name)) {
            etName.setError("Enter Name");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Enter Phone");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Enter Email");
            return;
        }

        // ✅ Insert into database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("email", email);
        cv.put("company", company);

        long result = db.insert("cards", null, cv);

        if (result == -1) {
            Toast.makeText(this, "❌ Failed to Save", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "✔ Card Saved", Toast.LENGTH_SHORT).show();

        // ✅ Notification
        showNotification();

        // ✅ Move to Display Screen
        Intent i = new Intent(this, DisplayCardActivity.class);
        i.putExtra("name", name);
        i.putExtra("phone", phone);
        i.putExtra("email", email);
        i.putExtra("company", company);
        startActivity(i);

        // Optional: clear fields
        clearFields();
    }

    private void showNotification() {

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Create channel (for Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Card Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            nm.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Business Card")
                .setContentText("Saved Successfully")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setAutoCancel(true);

        nm.notify(1, builder.build());
    }

    private void clearFields() {
        etName.setText("");
        etPhone.setText("");
        etEmail.setText("");
        etCompany.setText("");
    }
}