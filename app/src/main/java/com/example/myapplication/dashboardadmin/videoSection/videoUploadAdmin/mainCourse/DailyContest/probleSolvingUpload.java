package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.DailyContest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dashboarduser.ModelPdfUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class probleSolvingUpload extends AppCompatActivity {

    ImageButton backButtonImage;
    private AppCompatButton ModuleCategoryUploadedVideo ;
    private EditText searchModule;
    private ArrayList moduleCategoryList;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private AdapterContestModule adapterContestModule;
    private String UserType = "";
    private TextView choseSection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeforces_video_upload_admin);

        UserType = getIntent().getStringExtra("UserType");
        System.out.println("UserType ----->" + UserType);
        if(UserType.equals("USER")){
            Toast.makeText(probleSolvingUpload.this, "You are not register student", Toast.LENGTH_SHORT).show();
            finish();
        }
        recyclerView = findViewById(R.id.category_List);
        searchModule = findViewById(R.id.search_Et);
        firebaseAuth = FirebaseAuth.getInstance();
        choseSection = findViewById(R.id.choseSection);
        loadUserName();
        loadCategoryModuleCprogramming();





        searchModule.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    adapterContestModule.getFilter().filter(charSequence);
//                    adapterCModule.getFilter().filter(charSequence);
                }catch (Exception e){
                    Toast.makeText(probleSolvingUpload.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        backButtonImage = findViewById(R.id.BackButton);
        backButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        ModuleCategoryUploadedVideo = findViewById(R.id.addUploadedcategoryBtn);
        if(UserType.equals("ADMIN")) {
            ModuleCategoryUploadedVideo.setVisibility(View.VISIBLE);
            ModuleCategoryUploadedVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(probleSolvingUpload.this, ProblemSolvingUploadCategory.class));
//                startActivity(new Intent(probleSolvingUpload.this, CprogrammingUploadCategory.class));
//                finish();
                }
            });
        }else{
            ModuleCategoryUploadedVideo.setVisibility(View.INVISIBLE);
        }



    }

//    private void loadUserName() {
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
//        ref.child(firebaseAuth.getCurrentUser().getUid())
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        String Name = ""+ snapshot.child("Name").getValue();
//                        choseSection.setText("Welcome Back " + Name+" Problem Solving Contest Secton");
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//    }

    private void loadUserName() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null) {
            // **যদি ইউজার লগ ইন না থাকে, তাহলে সরাসরি Login পেজে পাঠাবে**
            Toast.makeText(this, "Please log in first!", Toast.LENGTH_SHORT).show();
            finish();  // **এই Activity বন্ধ করে দেবে**
            return;
        }

        String userId = currentUser.getEmail().replace(".",",");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String Name = snapshot.child("Name").getValue(String.class);
//                    choseSection.setText("Welcome Back " + Name + " Problem Solving Contest Section");
                } else {
//                    choseSection.setText("Welcome Back, User!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(probleSolvingUpload.this, "Error loading user: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadCategoryModuleCprogramming() {
        ArrayList<ModelDailyContestModule> modelDailyContestModulesCategories = new ArrayList<>();
//        ArrayList<ModelCModule> cmodulesCategory = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("DailyContest")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        cmodulesCategory.clear();
                        modelDailyContestModulesCategories.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            recyclerView.setLayoutManager(new LinearLayoutManager(probleSolvingUpload.this));
                            ModelDailyContestModule modelDailyContestModule =ds.getValue(ModelDailyContestModule.class);
                            modelDailyContestModulesCategories.add(modelDailyContestModule);
//                            ModelCModule modelc =  ds.getValue(ModelCModule.class);
//                            cmodulesCategory.add(modelc);
                        }

                        adapterContestModule = new AdapterContestModule( probleSolvingUpload.this , modelDailyContestModulesCategories , UserType) ;
                        recyclerView.setAdapter(adapterContestModule);

//                        adapterCModule = new AdapterCModule(probleSolvingUpload.this,cmodulesCategory);
//                        recyclerView.setAdapter(adapterCModule);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}