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
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Conceptual.ConceptualSessionAdmin;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.DailyContest.probleSolvingUpload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VideoHomePart extends AppCompatActivity {

    ImageButton backBtn;

    AppCompatButton mainCrsBtnContinue1 , probleSolvingBtn , conceptualBtn ;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    String UserType  = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_home_part);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));


        backBtn = findViewById(R.id.BackButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onBackPressed();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        loadUser();
        UserType = UserType.toUpperCase().trim();

        System.out.println("UserType : "+ UserType);





        //        ********************FIRST BUTTON*****************
        mainCrsBtnContinue1 = findViewById(R.id.continue_buttonFundamental);
        mainCrsBtnContinue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserType = UserType.toUpperCase().trim();
                System.out.println("main Course Button e UserType : " + UserType);

                if(UserType.equals("ADMIN")){
                    startActivity(new Intent(VideoHomePart.this, MainHomeCourseSection.class));

                }else{

                    startActivity(new Intent(VideoHomePart.this, MainHomeCourseSection.class));


//                    startActivity(new Intent(VideoHomePart.this , MainHomeCourseSection.class));
                }
//                finish();
            }
        });
        //        ********************FIRST BUTTON*****************



        //        ********************Codeforces Second BUTTON*****************
        probleSolvingBtn = findViewById(R.id.continue_buttonCodeForcestwo);
        probleSolvingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserType = UserType.toUpperCase().trim();

                System.out.println("Problem Course Button e UserType : " + UserType);

                if(UserType.equals("ADMIN")){
                    Intent intent = new Intent(VideoHomePart.this , probleSolvingUpload.class);
                    intent.putExtra("UserType", UserType);
                    startActivity(intent);
//                    startActivity(new Intent(VideoHomePart.this, probleSolvingUpload.class));

                }else if(UserType.equals("USER")){
                    Toast.makeText(VideoHomePart.this , "You Are Not Registered Student" , Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(VideoHomePart.this, probleSolvingUpload.class);
                    intent.putExtra("UserType", UserType);
                    startActivity(intent);

//                    startActivity(new Intent(VideoHomePart.this, probleSolvingUpload.class));
//                    startActivity(new Intent(VideoHomePart.this , MainHomeCourseSection.class));
                }

//                startActivity(new Intent(VideoHomePart.this, probleSolvingUpload.class));
//                finish();

            }
        });
        //        ********************Codeforces Second BUTTON*****************



        //        ********************{Problem solving Button third BUTTON*****************
        conceptualBtn = findViewById(R.id.continue_buttonCodechefthree);
        conceptualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(VideoHomePart.this, ConceptualSessionAdmin.class));
//                finish();

                UserType = UserType.toUpperCase().trim();

                System.out.println("conceptual Course Button e UserType : " + UserType);

                if(UserType.equals("ADMIN")){
                    Intent intent = new Intent(VideoHomePart.this, ConceptualSessionAdmin.class);
                    intent.putExtra("UserType",UserType);
                    startActivity(intent);
//                    startActivity(new Intent(VideoHomePart.this, ConceptualSessionAdmin.class));

                }else{
                    Intent intent = new Intent(VideoHomePart.this, ConceptualSessionAdmin.class);
                    intent.putExtra("UserType",UserType );
                    startActivity(intent);
//                    getcontext().startActivity(intent);
//                    startActivity(new Intent(VideoHomePart.this, ConceptualSessionAdmin.class));
//                    startActivity(new Intent(VideoHomePart.this , MainHomeCourseSection.class));
                }

            }
        });
        //        ********************Problem solving third BUTTON*****************


    }

    private void loadUser() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getCurrentUser().getEmail().replace(".", ","))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserType = ""+snapshot.child("userType").getValue();

                        System.out.println("LoadUser Er Vitore  "+ UserType);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}