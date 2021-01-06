package com.example.myweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweather.dao.UserDaoImpl;
import com.example.myweather.entity.Weather;
import com.example.myweather.util.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity {

    TextView txt_city, txt_temp, txt_desc;
    EditText et_city;
    Button btn_background, btn_logout, btn_save, btn_list;
    ImageButton search, btn_mylocation;
    ImageView img_bg, img_icon;
    UserDaoImpl udi;
    Weather weather = new Weather();
    String logged_in_username;


    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        if ( hasLocationPermission() ) {
            updateWeatherByCurrentLocation();

        } else {
//            Toast.makeText(MainActivity.this,"Location Permission is not Granted", Toast.LENGTH_SHORT).show();
        }

        logged_in_username = getIntent().getStringExtra("username");

        Log.e("logged in:", ""+logged_in_username);

        udi = new UserDaoImpl(MainActivity.this);


        et_city = findViewById(R.id.et_city);
        search = findViewById(R.id.but_fetch);
        btn_mylocation = findViewById(R.id.but_mylocation);
        txt_city = findViewById(R.id.txt_city);
        txt_temp = findViewById(R.id.txt_temp);
        txt_desc = findViewById(R.id.txt_desc);
        img_bg = findViewById(R.id.img_bg);
        img_icon = findViewById(R.id.img_icon);
        btn_background = findViewById(R.id.btn_background);
        btn_logout = findViewById(R.id.btn_logout);
        btn_save = findViewById(R.id.btn_save);
        btn_list = findViewById(R.id.btn_list);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_city.getText().length()==0) {
                    Toast.makeText(MainActivity.this,"Enter city name", Toast.LENGTH_SHORT).show();
                } else {
                    String city = et_city.getText().toString();
                    updateWeatherByCity(city);
                    et_city.getText().clear();
                }
                closeKeyboard();

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = weather.getCity();

                if (logged_in_username != null) {
                    if (!udi.checkSavedCity(logged_in_username, city)) {
                        udi.saveCity(logged_in_username, city);
                        Toast.makeText(MainActivity.this,"City Saved.",Toast.LENGTH_SHORT).show();
                        btn_save.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    } else {
                        udi.removeCity(logged_in_username, city);
                        Toast.makeText(MainActivity.this,"City Removed",Toast.LENGTH_SHORT).show();
                        btn_save.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                    }
                } else {
                    Toast.makeText(MainActivity.this,"Please Log in.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("citylist",""+udi.getCitylist(logged_in_username));

                Intent i = new Intent(MainActivity.this, CityListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("citylist", (Serializable)udi.getCitylist(logged_in_username));
                i.putExtra("bundle", bundle);

                MainActivity.this.startActivityForResult(i,456);

            }
        });

        btn_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, BackgroundChangeActivity.class);
                MainActivity.this.startActivityForResult(i,123);
            }
        });

        btn_mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateWeatherByCurrentLocation();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }

    boolean hasLocationPermission () {
        //Check if the user has not granted permission...
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Prompt the user to grant permission...
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 0);
            return false;
        }
        //Return true if the permission is already granted
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Check the users response...
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //Start location services
            updateWeatherByCurrentLocation();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        try {
            Log.e("requestCode", ""+requestCode);
            Log.e("resultCode", ""+resultCode);
            if(resultCode == 321) {
                int imgid = intent.getIntExtra("imgid", R.mipmap.background9);
                img_bg.setImageResource(imgid);
            } else if (resultCode == 654) {
                String city = intent.getStringExtra("city");
                updateWeatherByCity(city);
//                String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid=7c14ae68ceac5fece02a4708e1242768";
//                new fetchWeather().execute(url);
                Log.e("chosen city:",""+city);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateWeatherByCity(String city){
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid=7c14ae68ceac5fece02a4708e1242768";
        new fetchWeather().execute(url);
    }

    public void updateWeatherByCurrentLocation(){
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location myLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (myLoc != null) {
                String url = "https://api.openweathermap.org/data/2.5/weather?lat="+myLoc.getLatitude()+"&lon="+myLoc.getLongitude()+"&units=metric&appid=7c14ae68ceac5fece02a4708e1242768";
                new fetchWeather().execute(url);
            }
        } catch (Exception e) {
            Toast.makeText(MainActivity.this,"Cannot Get Your Location", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    class fetchWeather extends AsyncTask<String, Void, Void> {
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Loading Weather..",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(String... arg0s) {


            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response

            String jsonStr = sh.makeServiceCall(arg0s[0]);

            if (jsonStr != null) {
                try {

                    JSONObject jsonObj = new JSONObject(jsonStr);

                    String city = jsonObj.getString("name");

                    Double temp = jsonObj.getJSONObject("main").getDouble("temp");

                    JSONObject conditionObj = jsonObj.getJSONArray("weather").getJSONObject(0);
                    int conditionID = conditionObj.getInt("id");
                    String description = conditionObj.getString("main");

                    weather.setCity(city);
                    weather.setTemperature(temp);
                    weather.setConditionID(conditionID);
                    weather.setDescription(description);

                    Log.e(TAG, "City :" + city + ", temp:" + temp + ", conditionID: " + conditionID + ", desc:" + description);



                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "City not found",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            txt_city.setText(weather.getCity());
            txt_desc.setText(weather.getDescription());
            txt_temp.setText(Double.toString(weather.getTemperature())+"° C");
            img_icon.setImageResource(weather.getWeatherIcon());

            if (logged_in_username != null) {
                if (udi.checkSavedCity(logged_in_username, weather.getCity())) {
                    btn_save.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                } else {
                    btn_save.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                }
            }
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



}
