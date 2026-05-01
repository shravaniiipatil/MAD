package com.example.digitalbusinesscard;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class ViewCardsActivity extends AppCompatActivity {

    ListView listView;
    DBHelper dbHelper;
    ArrayList<HashMap<String,String>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cards);

        listView = findViewById(R.id.listView);
        dbHelper = new DBHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        dataList = new ArrayList<>();

        Cursor c = dbHelper.getReadableDatabase()
                .rawQuery("SELECT * FROM cards", null);

        while(c.moveToNext()){
            HashMap<String,String> map = new HashMap<>();
            map.put("name", c.getString(1));
            map.put("phone", c.getString(2));
            dataList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                dataList,
                R.layout.list_item,
                new String[]{"name","phone"},
                new int[]{R.id.tvRowName, R.id.tvRowPhone}
        );

        listView.setAdapter(adapter);
    }
}