package com.example.myapplication.dashboardadmin.problemsloving;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
//import com.example.myapplication.dashboardadmin.problemsloving.AnotherProblem.AnotherProblemSectionHome;
//import com.example.myapplication.dashboardadmin.problemsloving.categoryWise.categoryWiseProblem;
//import com.example.myapplication.dashboardadmin.problemsloving.luAcm.LuAcmHome;
import com.example.myapplication.dashboardadmin.problemsloving.ratingWise.RatingWiseProblemHome;
import com.example.myapplication.dashboardadmin.problemsloving.ratingWise.createRatingWise;

public class ProblemSolvingMainHome extends AppCompatActivity {

    private AppCompatButton categoryWise , RatingWiseBtn , LuAcm  , AnotherBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_solving_main_home);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));

        categoryWise = findViewById(R.id.Categorywist);
        categoryWise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Categorywist
                Intent intent = new Intent(ProblemSolvingMainHome.this, createRatingWise.class);
                intent.putExtra("PlatformName","CATEGORYWISE");
                startActivity(intent);
//                startActivity(new Intent(ProblemSolvingMainHome.this, categoryWiseProblem.class));

            }
        });

        RatingWiseBtn = findViewById(R.id.RatingWise);
        RatingWiseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProblemSolvingMainHome.this, RatingWiseProblemHome.class));
            }
        });

        LuAcm = findViewById(R.id.LuAcm);
        LuAcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(ProblemSolvingMainHome.this, LuAcmHome.class));
                Intent intent = new Intent(ProblemSolvingMainHome.this, createRatingWise.class);
                intent.putExtra("PlatformName","LUACM");
                startActivity(intent);

            }
        });

        AnotherBtn = findViewById(R.id.AnotherBtn);
        AnotherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProblemSolvingMainHome.this, createRatingWise.class);
                intent.putExtra("PlatformName","EXTRA");
                startActivity(intent);
//                startActivity(new Intent(ProblemSolvingMainHome.this, AnotherProblemSectionHome.class));

            }
        });




    }
}