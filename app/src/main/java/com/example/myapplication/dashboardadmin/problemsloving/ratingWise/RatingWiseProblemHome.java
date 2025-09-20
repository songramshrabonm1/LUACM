package com.example.myapplication.dashboardadmin.problemsloving.ratingWise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication.R;

public class RatingWiseProblemHome extends AppCompatActivity {

    private ImageButton backButton ;
    private AppCompatButton codeforces , codechef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_wise_problem_home);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));


        backButton = findViewById(R.id.BackButtonRating);
        codechef=findViewById(R.id.codechefUploadedVideo);
        codeforces = findViewById(R.id.codeforcesRating);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        codeforces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(RatingWiseProblemHome.this, createRatingWise.class);
                intent.putExtra("PlatformName", "CODEFORCES");
                startActivity(intent);
            }
        });

        codechef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(RatingWiseProblemHome.this, createRatingWise.class);
                intent.putExtra("PlatformName", "CODECHEF");
                startActivity(intent);
            }
        });



    }
}