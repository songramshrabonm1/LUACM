package com.example.myapplication.dashboardadmin.routinepart;

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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UploadRoutine extends AppCompatActivity {

     ImageButton backButton;
    private RecyclerView recyclerView;
    private EditText searchText ;
    private ArrayList<ModelRoutine> arrayListBatch;
    private AppCompatButton uploadRoutineButton;
    AdapterRoutine  adapterRoutine ;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    String User = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_routine);
        backButton = findViewById(R.id.backBtnnccc);
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getOnBackPressedDispatcher().onBackPressed();
//            }
//        });

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));



        recyclerView = findViewById(R.id.RecyclerViewBatch);
        firebaseAuth = FirebaseAuth.getInstance();
//        String userType = firebaseAuth.getUid();

        usersType();

        loadBatchCategory();


//        SEARCH
        searchText = findViewById(R.id.search_Et);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    adapterRoutine.getFilter().filter(charSequence);
                }catch (Exception e){
                    Toast.makeText(UploadRoutine.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        uploadRoutineButton =  findViewById(R.id.UploadRoutineBtnAppcompact);


                uploadRoutineButton.setVisibility(View.VISIBLE);
                uploadRoutineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(UploadRoutine.this, UploadRoutineButtonAdmin.class));

                }
            });



    }



    private void loadBatchCategory() {
        arrayListBatch = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("RoutineCheck");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListBatch.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    recyclerView.setLayoutManager(new LinearLayoutManager(UploadRoutine.this));
                    ModelRoutine rotuine = ds.getValue(ModelRoutine.class);
                    arrayListBatch.add(rotuine);

                }
                adapterRoutine = new AdapterRoutine(UploadRoutine.this, arrayListBatch , User);
                recyclerView.setAdapter(adapterRoutine);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void usersType() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(UploadRoutine.this, "No user logged in!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getCurrentUser().getEmail().replace(".", ","))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User = ""+snapshot.child("userType").getValue();
                        User = User.toString().toUpperCase();
                        System.out.println("Users : "+ User);
                        if(User.equals("ADMIN_OR_CR") || User.equals("ADMIN") || User.equals("CR")){
//                        if(User.equals("USER")){
                            uploadRoutineButton.setVisibility(View.VISIBLE);
                        }else{
                            uploadRoutineButton.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}