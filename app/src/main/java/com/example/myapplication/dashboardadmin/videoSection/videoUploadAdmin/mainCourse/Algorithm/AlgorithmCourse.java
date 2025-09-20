package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm;

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

public class AlgorithmCourse extends AppCompatActivity {


    private ImageButton backButtonImage;
    private AppCompatButton ModuleCategoryUploadedVideo ;
    private EditText searchModule;
    private ArrayList moduleCategoryList;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private TextView choseSection;
    private AdapterAlgoModule adapterAlgoModule;
    private String UserType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm_course);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));


        UserType = getIntent().getStringExtra("UserType");

        recyclerView = findViewById(R.id.category_List);
        searchModule = findViewById(R.id.search_Et);
        firebaseAuth = FirebaseAuth.getInstance();
        choseSection = findViewById(R.id.choseSection);
        loadUserName();


        searchModule.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    adapterAlgoModule.getFilter().filter(charSequence);
                }catch (Exception e){
                    Toast.makeText(AlgorithmCourse.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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

        if(UserType.equals("ADMIN")) {
            ModuleCategoryUploadedVideo.setVisibility(View.VISIBLE);
            ModuleCategoryUploadedVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(AlgorithmCourse.this, AlgorithmUploadCategory.class));

//                startActivity(new Intent(CPlusProgramming.this, CpluseprogrammingUploadCategory.class));
//                finish();
                }
            });
        }else{
            ModuleCategoryUploadedVideo.setVisibility(View.INVISIBLE);
        }

    }

    private void loadUserName() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String Name = ""+snapshot.child("Name").getValue();
                        choseSection.setText("Welcome Back " +Name + " , Ready for Your Algorithm Course ?");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void loadCategoryModuleCppprogramming() {
        ArrayList<ModelAlgoModule> cmodulesCategory = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("Algo")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cmodulesCategory.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            recyclerView.setLayoutManager(new LinearLayoutManager(AlgorithmCourse.this));
                            ModelAlgoModule modelAlgo =  ds.getValue(ModelAlgoModule.class);
                            cmodulesCategory.add(modelAlgo);
                        }

                        adapterAlgoModule = new AdapterAlgoModule(AlgorithmCourse.this , cmodulesCategory , UserType);
                        recyclerView.setAdapter(adapterAlgoModule);
//                        adapterCppModule = new AdapterCppModule(CPlusProgramming.this, cmodulesCategory);
//                        recyclerView.setAdapter(adapterCppModule);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}