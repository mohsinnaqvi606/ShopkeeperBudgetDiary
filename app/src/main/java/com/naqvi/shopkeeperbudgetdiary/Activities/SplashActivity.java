package com.naqvi.shopkeeperbudgetdiary.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;

import com.naqvi.shopkeeperbudgetdiary.R;
import com.naqvi.shopkeeperbudgetdiary.Utils.SharedPreference;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    //Duration of wait
    int SPLASH_TIME_OUT = 3000;
    SharedPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        pref = new SharedPreference(this);
        setLanguage(pref.get_Language());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    void setLanguage(String language) {
        if (language != "No Language Found") {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLocale(locale);
            } else {
                config.locale = locale;
            }
            getResources().updateConfiguration(config,
                    getResources().getDisplayMetrics());
        }
    }

}