package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.AdapterCppModuleClasses;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.CppproggramingModuleClass;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.CppprogrammingModelClasses;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.CppprogrammingModuleClasses;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AlgoModuleClass extends AppCompatActivity {

    ImageButton backButton ;
    TextView videoTitle , ModuleTitle , InstructorName ;
    String Moduleid ;
    AppCompatButton AddingCls ;
    RecyclerView recyclerView;
    private String UserType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algo_module_class);


        UserType= getIntent().getStringExtra("UserType");
        backButton = findViewById(R.id.backBtnnc);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Moduleid = getIntent().getStringExtra("id"); // এই টা  আসতেছে AdapterCModule class থেকে ।
        loadClassess();

        videoTitle = findViewById(R.id.videoTitle);
        ModuleTitle = findViewById(R.id.moduleNamee);
        InstructorName = findViewById(R.id.InstructorName);
        recyclerView = findViewById(R.id.addingclassRecyclerView);

        videoTitle.setText(getIntent().getStringExtra("VideoTitle"));
        ModuleTitle.setText(getIntent().getStringExtra("module"));
        InstructorName.setText(getIntent().getStringExtra("instructorName"));


        AddingCls = findViewById(R.id.addingCls);
        if(UserType.equals("ADMIN")) {
            AddingCls.setVisibility(View.VISIBLE);
            AddingCls.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AlgoModuleClass.this, AlgoModuleClasses.class);
                    intent.putExtra("id", Moduleid);
                    startActivity(intent);

//                Toast.makeText(CppproggramingModuleClass.this, "Adding class Button " , Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            AddingCls.setVisibility(View.INVISIBLE);
        }


    }

    private void loadClassess() {
        ArrayList<AlgoModelClasses> classessCategory = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("Algo")
                .child(Moduleid)
                .child("Classes")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        classessCategory.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            recyclerView.setLayoutManager(new LinearLayoutManager(AlgoModuleClass.this));
                            AlgoModelClasses model = ds.getValue(AlgoModelClasses.class);
                            classessCategory.add(model);
                        }
                        AdapterAlgoModuleClasses adapterAlgoModuleClasses = new AdapterAlgoModuleClasses(classessCategory, AlgoModuleClass.this , UserType);
                        recyclerView.setAdapter(adapterAlgoModuleClasses);
//                        AdapterCppModuleClasses adapterCppModuleClasses = new AdapterCppModuleClasses(classessCategory , AlgoModuleClass.this);

//                        recyclerView.setAdapter(adapterCppModuleClasses);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}