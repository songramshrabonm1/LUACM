package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Datastructure;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.AdapterCppModule;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.CPlusProgramming;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.CpluseprogrammingUploadCategory;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.ModelCppModule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Ds extends AppCompatActivity {


    private ImageButton backButtonImage;
    private AppCompatButton ModuleCategoryUploadedVideo ;
    private EditText searchModule;
    private ArrayList moduleCategoryList;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private AdapterDSModule adapterDSModule;
    private TextView choseSection ;
    private String UserType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));


        UserType = getIntent().getStringExtra("UserType");


        recyclerView = findViewById(R.id.category_List);
        searchModule = findViewById(R.id.search_Et);
        firebaseAuth = FirebaseAuth.getInstance();
        choseSection = findViewById(R.id.choseSection);



        loadUserName();
        loadCategoryModuleCppprogramming();

        searchModule.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                     adapterDSModule.getFilter().filter(charSequence);
                }catch (Exception e){
                    Toast.makeText(Ds.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    startActivity(new Intent(Ds.this, DsprogrammingUploadCategory.class));

//                startActivity(new Intent(CPlusProgramming.this, CpluseprogrammingUploadCategory.class));
//                finish();
                }
            });
        }else{
            ModuleCategoryUploadedVideo.setVisibility(View.INVISIBLE);
        }
    }
    private void loadCategoryModuleCppprogramming() {
        ArrayList<ModelDsModule> DsmodulesCategory = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("DataStructure")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DsmodulesCategory.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            recyclerView.setLayoutManager(new LinearLayoutManager(Ds.this));
                            ModelDsModule modelc =  ds.getValue(ModelDsModule.class);
                            DsmodulesCategory.add(modelc);
                        }

                        adapterDSModule = new AdapterDSModule(Ds.this, DsmodulesCategory , UserType);
//                        adapterCModule = new AdapterCppModule(CPlusProgramming.this,cmodulesCategory);
                        recyclerView.setAdapter(adapterDSModule);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    //*****************************SETUSER NAME**************************
    private void loadUserName(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name =""+ snapshot.child("Name").getValue();
                        choseSection.setText("Welcome back "+ name +", ready for your Data Structure lesson?");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    //*****************************SETUSER NAME**************************
}