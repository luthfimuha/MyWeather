package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class BackgroundChangeActivity extends AppCompatActivity {

    int result;
    int imgid[] = {
            R.mipmap.background1,
            R.mipmap.background2,
            R.mipmap.background3,
            R.mipmap.background4,
            R.mipmap.background5,
            R.mipmap.background6,
            R.mipmap.background7,
            R.mipmap.background8,
            R.mipmap.background9,
    };

    ImageButton img01, img02, img03, img04, img05, img06, img07, img08, img09;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_background_change);

        img01 = findViewById(R.id.img01);
        img02 = findViewById(R.id.img02);
        img03 = findViewById(R.id.img03);
        img04 = findViewById(R.id.img04);
        img05 = findViewById(R.id.img05);
        img06 = findViewById(R.id.img06);
        img07 = findViewById(R.id.img07);
        img08 = findViewById(R.id.img08);
        img09 = findViewById(R.id.img09);

        checkImg ci = new checkImg();
        img01.setOnClickListener(ci);
        img02.setOnClickListener(ci);
        img03.setOnClickListener(ci);
        img04.setOnClickListener(ci);
        img05.setOnClickListener(ci);
        img06.setOnClickListener(ci);
        img07.setOnClickListener(ci);
        img08.setOnClickListener(ci);
        img09.setOnClickListener(ci);

    }

    class checkImg implements View.OnClickListener{
        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.img01:
                    result = imgid[0];
                    break;
                case R.id.img02:
                    result = imgid[1];
                    break;
                case R.id.img03:
                    result = imgid[2];
                    break;
                case R.id.img04:
                    result = imgid[3];
                    break;
                case R.id.img05:
                    result = imgid[4];
                    break;
                case R.id.img06:
                    result = imgid[5];
                    break;
                case R.id.img07:
                    result = imgid[6];
                    break;
                case R.id.img08:
                    result = imgid[7];
                    break;
                case R.id.img09:
                    result = imgid[8];
                    break;
            }

            Intent i = new Intent(BackgroundChangeActivity.this, MainActivity.class);
            i.putExtra("imgid", result);
            BackgroundChangeActivity.this.setResult(321,i);
            BackgroundChangeActivity.this.finish();
        }

    }
}