package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CproggramingModuleClass extends AppCompatActivity {

    ImageButton backButton ;
    TextView videoTitle , ModuleTitle , InstructorName ;
    String Moduleid ;
    AppCompatButton AddingCls ;
    RecyclerView recyclerView;
    private String UserType = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cproggraming_module_class);

        getWindow().setStatusBarColor(Color.parseColor("#141526"));

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
        AddingCls = findViewById(R.id.addingCls);

        videoTitle.setText(getIntent().getStringExtra("VideoTitle"));
        ModuleTitle.setText(getIntent().getStringExtra("module"));
        InstructorName.setText(getIntent().getStringExtra("instructorName"));

        UserType = getIntent().getStringExtra("UserType");


        if(UserType.equals("ADMIN")){
            AddingCls.setVisibility(View.VISIBLE);
        }else{
            AddingCls.setVisibility(View.INVISIBLE);
        }

        if(UserType.equals("ADMIN")) {
            AddingCls.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CproggramingModuleClass.this, CprogrammingModuleClasses.class);
                    intent.putExtra("id", Moduleid);
                    startActivity(intent);
//                startActivity(new Intent(CproggramingModuleClass.this, CprogrammingModuleClasses.class ));

//                Toast.makeText(CproggramingModuleClass.this, "Adding class Button " , Toast.LENGTH_SHORT).show();
                }
            });
        }






    }

    private void loadClassess() {
        ArrayList<CprogrammingModelClasses> classessCategory = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("CProgramming")
                .child(Moduleid)
                .child("Classes")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        classessCategory.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            recyclerView.setLayoutManager(new LinearLayoutManager(CproggramingModuleClass.this));
                            CprogrammingModelClasses model = ds.getValue(CprogrammingModelClasses.class);
                            classessCategory.add(model);
                        }
                        AdapterCModuleClasses adapterCModuleClasses = new AdapterCModuleClasses(classessCategory , CproggramingModuleClass.this , UserType);
                        recyclerView.setAdapter(adapterCModuleClasses);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}