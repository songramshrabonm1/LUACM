package com.example.myapplication.dashboardadmin.routinepart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

public class RoutineShow extends AppCompatActivity {


    ImageButton backButton ;
    TextView TextViewEditBatch;
    ImageView image ;

    String Batch = "" , Url = "" , Section = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_show);


        TextViewEditBatch = findViewById(R.id.TextViewEditBatch);
        backButton = findViewById(R.id.backBtnnc);
        image = findViewById(R.id.imageViewss);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        Batch = getIntent().getStringExtra("Batch");
        Url = getIntent().getStringExtra("Url");
        Section = getIntent().getStringExtra("Section");

        System.out.println("Url: "+ Url);
        TextViewEditBatch.setText("Batch : "+ Batch + " - " + Section);


        Glide.with(RoutineShow.this)
                .load(Url)
                .into(image);



    }
}