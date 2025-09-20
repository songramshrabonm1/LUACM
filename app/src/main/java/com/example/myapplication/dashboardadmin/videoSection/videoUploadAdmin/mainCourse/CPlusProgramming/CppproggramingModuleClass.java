package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming;

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
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.AdapterCModuleClasses;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CproggramingModuleClass;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CprogrammingModelClasses;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CprogrammingModuleClasses;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CppproggramingModuleClass extends AppCompatActivity {

    ImageButton backButton ;
    TextView videoTitle , ModuleTitle , InstructorName ;
    String Moduleid ;
    AppCompatButton AddingCls ;
    RecyclerView recyclerView;
    String UserType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cppproggraming_module_class);

        UserType = getIntent().getStringExtra("UserType");

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
                    Intent intent = new Intent(CppproggramingModuleClass.this, CppprogrammingModuleClasses.class);
                    intent.putExtra("id", Moduleid);
                    startActivity(intent);
//                startActivity(new Intent(CproggramingModuleClass.this, CppprogrammingModuleClasses.class ));

//                Toast.makeText(CppproggramingModuleClass.this, "Adding class Button " , Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            AddingCls.setVisibility(View.INVISIBLE);

        }



    }

    private void loadClassess() {
        ArrayList<CppprogrammingModelClasses> classessCategory = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("CPlusProgramming")
                .child(Moduleid)
                .child("Classes")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        classessCategory.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            recyclerView.setLayoutManager(new LinearLayoutManager(CppproggramingModuleClass.this));
                            CppprogrammingModelClasses model = ds.getValue(CppprogrammingModelClasses.class);
                            classessCategory.add(model);
                        }

                        AdapterCppModuleClasses adapterCppModuleClasses = new AdapterCppModuleClasses(classessCategory , CppproggramingModuleClass.this , UserType);
//                        AdapterCppModuleClasses adapterCppModuleClasses = new AdapterCppModuleClasses(classessCategory , CppproggramingModuleClass.class);
//                        AdapterCppModuleClasses adapterCModuleClasses = new AdapterCppModuleClasses(classessCategory , CppproggramingModuleClass.this);
                        recyclerView.setAdapter(adapterCppModuleClasses);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}