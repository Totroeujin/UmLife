package com.example.umlife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashBackground extends AppCompatActivity {

    Handler h = new Handler();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_background);

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashBackground.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        },3000);

    }
}