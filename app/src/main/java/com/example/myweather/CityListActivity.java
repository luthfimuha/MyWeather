package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CityListActivity extends AppCompatActivity {

    List<String> citylist = new ArrayList<String>();

    ListView city_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_city_list);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        citylist = (List<String>) bundle.getSerializable("citylist");
        Log.e("from citylist:",""+citylist);

        city_lv = findViewById(R.id.city_lv);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.city_list_item, citylist);
        city_lv.setAdapter(adapter);

        city_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CityListActivity.this, MainActivity.class);

                intent.putExtra("city", citylist.get(i));
                CityListActivity.this.setResult(654,intent);
                CityListActivity.this.finish();

            }
        });


    }
}