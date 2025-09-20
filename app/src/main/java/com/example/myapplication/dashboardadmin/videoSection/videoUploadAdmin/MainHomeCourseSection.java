package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.AlgorithmCourse;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.CPlusProgramming;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Cprogramming;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Datastructure.Ds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainHomeCourseSection extends AppCompatActivity {

    ImageButton backBtn;
    AppCompatButton cProgramming, cplus, Dstructure , Algo;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    String UserType = "" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_course_section);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));



        backBtn = findViewById(R.id.BackButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        loadUser();
        UserType = UserType.toUpperCase().trim();




        cProgramming = findViewById(R.id.cUploadedVideo);
        cProgramming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainHomeCourseSection.this, Cprogramming.class));

                UserType = UserType.toUpperCase().trim();
                System.out.println("main Course Button e UserType : " + UserType);

                if(UserType.equals("ADMIN")){
                    Intent intent = new Intent(MainHomeCourseSection.this, Cprogramming.class);
                    intent.putExtra("UserType", UserType);
                    startActivity(intent);
//                    startActivity(new Intent(MainHomeCourseSection.this, Cprogramming.class));

                }else{
                    Intent intent = new Intent(MainHomeCourseSection.this, Cprogramming.class);
                    intent.putExtra("UserType", UserType);
                    startActivity(intent);
                }

//                finish();
            }
        });

        cplus = findViewById(R.id.cplusUploadedVideo);
        cplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainHomeCourseSection.this, CPlusProgramming.class));
//                finish();
                UserType = UserType.toUpperCase().trim();
                System.out.println("main Course Button e UserType : " + UserType);

                 Intent intent = new Intent(MainHomeCourseSection.this, CPlusProgramming.class);
                 intent.putExtra("UserType", UserType);
                 startActivity(intent);

//                    startActivity(new Intent(MainHomeCourseSection.this, CPlusProgramming.class));

//                if(UserType.equals("ADMIN")){
//
//                }else{
//
//                }
            }
        });

        Dstructure = findViewById(R.id.DataStructureBtn);
        Dstructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainHomeCourseSection.this, Ds.class));
//                finish();

                UserType = UserType.toUpperCase().trim();
                System.out.println("main Course Button e UserType : " + UserType);

                Intent intent = new Intent(MainHomeCourseSection.this, Ds.class);
                intent.putExtra("UserType", UserType);
                startActivity(intent);

//                if(UserType.equals("ADMIN")){
//                    startActivity(new Intent(MainHomeCourseSection.this, Ds.class));
//
//                }else{
//
//                }
            }
        });

        Algo = findViewById(R.id.algoBtn);
        Algo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainHomeCourseSection.this, AlgorithmCourse.class));
//                finish();

                UserType = UserType.toUpperCase().trim();
                System.out.println("main Course Button e UserType : " + UserType);

                Intent intent = new Intent(MainHomeCourseSection.this, AlgorithmCourse.class);
                intent.putExtra("UserType", UserType);
                startActivity(intent);

//                if(UserType.equals("ADMIN")){
//                    startActivity(new Intent(MainHomeCourseSection.this, AlgorithmCourse.class));
//                }else{
//
//                }
            }
        });





    }

//    private void loadUser() {
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
//        ref.child(firebaseAuth.getCurrentUser().getUid())
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        UserType = ""+snapshot.child("userType").getValue();
//
//                        System.out.println("LoadUser Er Vitore  "+ UserType);
//
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//    }

    private void loadUser() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null) {
            // **যদি ইউজার লগ ইন না থাকে, তাহলে সরাসরি Login পেজে পাঠাবে**
            Toast.makeText(this, "Please log in first!", Toast.LENGTH_SHORT).show();
            finish();  // **এই Activity বন্ধ করে দেবে**
            return;
        }

        String userId = currentUser.getEmail().replace(".",",");
        DatabaseReference ref = firebaseDatabase.getReference("Users").child(userId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserType = snapshot.child("userType").getValue(String.class);
                    if (UserType != null) {
                        UserType = UserType.toUpperCase().trim();
                        System.out.println("UserType Loaded: " + UserType);
                    }
                } else {
                    System.out.println("User data not found in database.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database Error: " + error.getMessage());
            }
        });
    }

}