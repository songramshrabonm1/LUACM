package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Conceptual;

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
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.AdapterAlgoModule;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.AlgorithmCourse;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.AlgorithmUploadCategory;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.ModelAlgoModule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConceptualSessionAdmin extends AppCompatActivity {


    private ImageButton backButtonImage;
    private AppCompatButton ModuleCategoryUploadedVideo ;
    private EditText searchModule;
    private ArrayList moduleCategoryList;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private TextView choseSection;
    private String UserType = "";
        private AdapterConceptualModule adapterConceptualModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conceptual_session_admin);

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
                    adapterConceptualModule.getFilter().filter(charSequence);
//                    adapterAlgoModule.getFilter().filter(charSequence);
                }catch (Exception e){
                    Toast.makeText(ConceptualSessionAdmin.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    startActivity(new Intent(ConceptualSessionAdmin.this, ConceptualUploadCategory.class));

//                startActivity(new Intent(CPlusProgramming.this, CpluseprogrammingUploadCategory.class));
//                finish();
                }
            });
        }else{
            ModuleCategoryUploadedVideo.setVisibility(View.INVISIBLE);
        }
    }


    private void loadUserName() {
        if (firebaseAuth.getCurrentUser() == null) {
            Toast.makeText(this, "User not logged in. Please log in first!", Toast.LENGTH_SHORT).show();
            finish(); // **Activity বন্ধ করে দেবে, যাতে ক্র্যাশ না হয়**
            return;
        }

        String userId = firebaseAuth.getCurrentUser().getEmail().replace("." , ",");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String Name = snapshot.child("Name").getValue(String.class);
                    choseSection.setText("Welcome Back " + Name + " , Ready for Your Algorithm Course ?");
                } else {
                    choseSection.setText("Welcome Back, User!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ConceptualSessionAdmin.this, "Error loading user: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void loadCategoryModuleCppprogramming() {
        ArrayList<ModelConceptualModule> cmodulesCategory = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("Conceptual")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cmodulesCategory.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            recyclerView.setLayoutManager(new LinearLayoutManager(ConceptualSessionAdmin.this));
                            ModelConceptualModule modelConceptualModule =  ds.getValue(ModelConceptualModule.class);
                            cmodulesCategory.add(modelConceptualModule);
                        }
                        adapterConceptualModule = new AdapterConceptualModule(ConceptualSessionAdmin.this, cmodulesCategory , UserType) ;
                        recyclerView.setAdapter(adapterConceptualModule);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


}