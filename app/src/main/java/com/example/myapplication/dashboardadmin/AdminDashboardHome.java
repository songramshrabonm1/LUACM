package com.example.myapplication.dashboardadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.addAdminCr.SetAdminCrHome;
import com.example.myapplication.dashboardadmin.problemsloving.ProblemSolvingMainHome;
import com.example.myapplication.dashboardadmin.routinepart.FreeClassRoomHome;
import com.example.myapplication.dashboardadmin.routinepart.SearchingFreeClassRoom;
import com.example.myapplication.dashboardadmin.routinepart.UploadRoutine;
import com.example.myapplication.dashboardadmin.routinepart.UploadRoutineButtonAdmin;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.VideoHomePart;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboardHome extends AppCompatActivity {

    Button logout, pdfbtn, videobtn , routine , problemsSolving, controllingSections , freeclass;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard_home);


         pdfbtn = findViewById(R.id.pdfButton);
         logout = findViewById(R.id.log);

         logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                  FirebaseAuth.getInstance().signOut();
//                  finish();
                 startActivity(new Intent(AdminDashboardHome.this, MainActivity.class));
                 finish();
             }
         });

        pdfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardHome.this, DashboardAdmin.class));
//                finish();
            }
        });

        videobtn = findViewById(R.id.videoBtn);
        videobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardHome.this, VideoHomePart.class));
//                finish();
            }
        });

        routine = findViewById(R.id.routine);
        routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardHome.this, UploadRoutine.class));
            }
        });

        problemsSolving = findViewById(R.id.ProbleSolvingPlatformplatformBtn);
        problemsSolving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardHome.this, ProblemSolvingMainHome.class));
            }
        });
        controllingSections = findViewById(R.id.controllingSection);
        controllingSections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardHome.this, SetAdminCrHome.class));
            }
        });
        freeclass = findViewById(R.id.rtnbtn);
        freeclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardHome.this, FreeClassRoomHome.class));
            }
        });
    }
}