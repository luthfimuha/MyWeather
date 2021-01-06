package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myweather.dao.UserDaoImpl;
import com.example.myweather.entity.User;

public class LoginActivity extends AppCompatActivity {

    EditText et_username, et_password;
    Button btn_login, btn_toRegister;
    UserDaoImpl udi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        udi = new UserDaoImpl(LoginActivity.this);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_toRegister = findViewById(R.id.btn_toRegister);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s1 = et_username.getText().toString().toLowerCase();
                String s2 = et_password.getText().toString();

                if (udi.checkUser(s1, s2)) {

                    Toast.makeText(LoginActivity.this, "Login Successful ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("username",s1);
                    startActivity(i);

                } else {
                    Toast.makeText(LoginActivity.this, "Wrong username or password ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });

    }
}