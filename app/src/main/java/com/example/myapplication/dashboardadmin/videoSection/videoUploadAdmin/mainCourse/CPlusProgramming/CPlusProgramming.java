package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming;


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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CPlusProgramming extends AppCompatActivity {

    private ImageButton backButtonImage;
    private AppCompatButton ModuleCategoryUploadedVideo ;
    private EditText searchModule;
    private ArrayList moduleCategoryList;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private AdapterCppModule adapterCppModule;
    private TextView choseSection ;
    private String UserType;
//    private AdapterCPlusModule adapterCModule;

//    private AdapterCPlusModule adapterCModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cplus_programming);




        recyclerView = findViewById(R.id.category_List);
        searchModule = findViewById(R.id.search_Et);
        firebaseAuth = FirebaseAuth.getInstance();
        choseSection  = findViewById(R.id.choseSection);
        loadUserName();

        UserType = getIntent().getStringExtra("UserType");



        searchModule.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    adapterCppModule.getFilter().filter(charSequence);
                }catch (Exception e){
                    Toast.makeText(CPlusProgramming.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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

        loadCategoryModuleCppprogramming();

        ModuleCategoryUploadedVideo = findViewById(R.id.addUploadedcategoryBtn);
        if(UserType.equals("ADMIN")){
            ModuleCategoryUploadedVideo.setVisibility(View.VISIBLE);
        }else{
            ModuleCategoryUploadedVideo.setVisibility(View.INVISIBLE);
        }

        if(UserType.equals("ADMIN")){
            ModuleCategoryUploadedVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(CPlusProgramming.this, CpluseprogrammingUploadCategory.class));

//                startActivity(new Intent(CPlusProgramming.this, CpluseprogrammingUploadCategory.class));
//                finish();
                }
            });
        }
    }

    private void loadUserName() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String Name = ""+snapshot.child("Name").getValue();
                        choseSection.setText("Welcome Back " + Name + " , Ready For Your C++ Lesson ? ");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void loadCategoryModuleCppprogramming() {
        ArrayList<ModelCppModule> cmodulesCategory = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("CPlusProgramming")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cmodulesCategory.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            recyclerView.setLayoutManager(new LinearLayoutManager(CPlusProgramming.this));
                            ModelCppModule modelc =  ds.getValue(ModelCppModule.class);
                            cmodulesCategory.add(modelc);
                        }

                        adapterCppModule = new AdapterCppModule(CPlusProgramming.this, cmodulesCategory , UserType);
//                        adapterCModule = new AdapterCppModule(CPlusProgramming.this,cmodulesCategory);
                        recyclerView.setAdapter(adapterCppModule);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}