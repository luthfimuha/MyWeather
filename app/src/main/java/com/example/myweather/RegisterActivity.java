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
import com.example.myweather.util.MyHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText et_username, et_password;
    Button btn_register, btn_toLogin;
    UserDaoImpl udi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        udi = new UserDaoImpl(RegisterActivity.this);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_register = findViewById(R.id.btn_register);
        btn_toLogin = findViewById(R.id.btn_toLogin);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (et_username.getText().length() == 0 || et_password.getText().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Fill the form ", Toast.LENGTH_SHORT).show();
                } else {
                    String s1 = et_username.getText().toString().toLowerCase();
                    String s2 = et_password.getText().toString();

                    if (udi.validateUsername(s1)) {
                        User user = new User(s1,s2);
                        udi.addUser(user);
                        Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                        i.putExtra("username",s1);
                        startActivity(i);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Username already exists, try another. ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btn_toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });


    }
}