package com.example.myapplication.dashboarduser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.AdminDashboardHome;
import com.example.myapplication.dashboardadmin.problemsloving.ProblemSolvingMainHome;
import com.example.myapplication.dashboardadmin.routinepart.FreeClassRoomHome;
import com.example.myapplication.dashboardadmin.routinepart.SearchingFreeClassRoom;
import com.example.myapplication.dashboardadmin.routinepart.UploadRoutine;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.VideoHomePart;

public class UserDashboarHome extends AppCompatActivity {

    private Button pdfbtn , videobtn , routine , ProbleSolvingPlatformplatformBtn ,freeclassbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboar_home);

        pdfbtn = findViewById(R.id.pdfButton);

        pdfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboarHome.this, UserDashboardActivity.class));
//                finish();
            }
        });

        freeclassbtn = findViewById(R.id.rtnbtn);
        freeclassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboarHome.this, FreeClassRoomHome.class));
            }
        });

        videobtn = findViewById(R.id.videoBtn);
        videobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboarHome.this, VideoHomePart.class));

            }
        });

        routine = findViewById(R.id.routine);
        routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDashboarHome.this , UploadRoutine.class));
            }
        });

        ProbleSolvingPlatformplatformBtn = findViewById(R.id.ProbleSolvingPlatformplatformBtn);
        ProbleSolvingPlatformplatformBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDashboarHome.this, ProblemSolvingMainHome.class));

            }
        });

    }
}