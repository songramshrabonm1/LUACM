package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScrenn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screnn);


        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScrenn.this,MainActivity.class));
//


//                startActivity(new Intent(SplashActivity.this,Calculator.class));
//                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                if(currentUser == null){
//                    startActivity(new Intent(SplashScrenn.this,MainActivity.class));
//
//
//                }else{
//                    startActivity(new Intent(SplashScrenn.this,StartingPage.class));
//
//                }
//                startActivity(new Intent(SplashActivity.this,StopWatch.class));

                finish();
            }
        },1000);
    }
}