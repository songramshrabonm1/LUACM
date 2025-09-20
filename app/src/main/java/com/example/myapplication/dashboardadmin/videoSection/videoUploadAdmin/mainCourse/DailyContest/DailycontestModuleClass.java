package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.DailyContest;

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
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.AdapterAlgoModuleClasses;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.AlgoModelClasses;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.AlgoModuleClass;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.AlgoModuleClasses;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DailycontestModuleClass extends AppCompatActivity {

    ImageButton backButton ;
    TextView videoTitle , ModuleTitle , InstructorName ;
    String Moduleid ;
    AppCompatButton AddingCls ;
    RecyclerView recyclerView;
    private String UserType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailycontest_module_class);

//        UserType
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


        InstructorName.setText(getIntent().getStringExtra("InstructorNames"));
        ModuleTitle.setText(getIntent().getStringExtra("ContestName"));
        videoTitle.setText(getIntent().getStringExtra("PlatformName")+" "+ getIntent().getStringExtra("DeivisionName"));




        AddingCls = findViewById(R.id.addingCls);
        if(UserType.equals("ADMIN")) {
            AddingCls.setVisibility(View.VISIBLE);
            AddingCls.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DailycontestModuleClass.this, DailyContestModuleClasses.class);
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

        ArrayList<DailyModelClasses> classessCategory = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("DailyContest")
                .child(Moduleid)
                .child("Classes")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        classessCategory.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            recyclerView.setLayoutManager(new LinearLayoutManager(DailycontestModuleClass.this));
                            DailyModelClasses model = ds.getValue(DailyModelClasses.class);
                            classessCategory.add(model);
                        }
                        AdapterDailyContestModuleClasses adapterDailyContestModuleClasses = new AdapterDailyContestModuleClasses(classessCategory, DailycontestModuleClass.this , UserType);
                        recyclerView.setAdapter(adapterDailyContestModuleClasses);
//                        AdapterAlgoModuleClasses adapterAlgoModuleClasses = new AdapterAlgoModuleClasses(classessCategory, AlgoModuleClass.this);
//                        recyclerView.setAdapter(adapterAlgoModuleClasses);
//
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}