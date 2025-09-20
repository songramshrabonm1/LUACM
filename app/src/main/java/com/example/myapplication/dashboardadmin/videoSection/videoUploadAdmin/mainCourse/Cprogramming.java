package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
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
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Conceptual.ModelConceptualModule;
import com.example.myapplication.dashboarduser.ModelPdfUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cprogramming extends AppCompatActivity {

     ImageButton backButtonImage;
    private AppCompatButton ModuleCategoryUploadedVideo ;
    private EditText searchModule;
    private ArrayList moduleCategoryList;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private AdapterCModule adapterCModule;
    private TextView choseSection;
    private String UserType = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cprogramming);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));



        firebaseAuth = FirebaseAuth.getInstance();
        ModuleCategoryUploadedVideo = findViewById(R.id.addUploadedcategoryBtn);
        loadUserName();

        recyclerView = findViewById(R.id.category_List);
        searchModule = findViewById(R.id.search_Et);
        choseSection = findViewById(R.id.choseSection);


        UserType = getIntent().getStringExtra("UserType");

//        UserType = UserType.toUpperCase().trim();
        System.out.println("Visivility set korar age UserType : "+ UserType);

        if(UserType.equals("USER")){
            ModuleCategoryUploadedVideo.setVisibility(View.INVISIBLE);
        }else{
            ModuleCategoryUploadedVideo.setVisibility(View.VISIBLE);

        }




        searchModule.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        adapterCModule.getFilter().filter(charSequence);
                    }catch (Exception e){
                        Toast.makeText(Cprogramming.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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

        loadCategoryModuleCprogramming();

//        UserType = UserType.toUpperCase().trim();
        System.out.println("button e dukar age  : "+ UserType);

        if(UserType.equals("ADMIN")) {
            System.out.println("button e dukesi UserType : "+ UserType);

            ModuleCategoryUploadedVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("button karjokor howar age  : "+ UserType);

                    startActivity(new Intent(Cprogramming.this, CprogrammingUploadCategory.class));
//                finish();
                }
            });
        }




    }

    private void loadUserName() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getCurrentUser().getEmail().replace(".",","))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String Name = ""+ snapshot.child("Name").getValue();
                        choseSection.setText("Welcome Back " + Name+" , Ready For Your C Programming Lesson ? ");
//                        UserType = ""+snapshot.child("userType").getValue();
//                        UserType = UserType.toUpperCase().trim();
//                        System.out.println("UserType : "+ UserType);

//                        if(UserType.equals("USER")){
//                            ModuleCategoryUploadedVideo.setVisibility(View.INVISIBLE);
//                        }else{
//                            ModuleCategoryUploadedVideo.setVisibility(View.VISIBLE);
//
//                        }
                        ModuleCategoryUploadedVideo.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void loadCategoryModuleCprogramming() {
        ArrayList<ModelCModule> cmodulesCategory = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("CProgramming")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cmodulesCategory.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            recyclerView.setLayoutManager(new LinearLayoutManager(Cprogramming.this));
                            ModelCModule modelc =  ds.getValue(ModelCModule.class);
                            cmodulesCategory.add(modelc);
                        }

                        adapterCModule = new AdapterCModule(Cprogramming.this,cmodulesCategory , UserType);
                        recyclerView.setAdapter(adapterCModule);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}