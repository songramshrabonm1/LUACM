package com.example.myapplication.dashboardadmin.routinepart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SeeBlockClass extends AppCompatActivity {

    private ImageButton backBtn ;
    private EditText searchBtn;
    private RecyclerView blockClassRecyclerView ;
    private String day , time ;
    ProgressDialog progressDialog;
    AdapterBlockClass adapterBlockClass ;
    public com.example.myapplication.dashboardadmin.routinepart.FilterBlockClass filter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_block_class);

        backBtn = findViewById(R.id.backBtnnc);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchBtn = findViewById(R.id.search_Et);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("This is loading");
        progressDialog.setCanceledOnTouchOutside(false);

        day = getIntent().getStringExtra("day");
        time = getIntent().getStringExtra("time");
        blockClassRecyclerView = findViewById(R.id.classessBlock);

        loadBlockClass();

        searchBtn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapterBlockClass.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void loadBlockClass() {
        ArrayList<ModelFreeClassShow> blockArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("FreeClassroom");

        ref.child(day)
                .child(time)
                .child("classRoom")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        blockArrayList.clear();

                                        for (DataSnapshot ds : snapshot.getChildren()) {
//                                            if(ds.getValue().equals("BLOCK")) {
                                            String classType = ""+ds.child("classType").getValue(String.class);
                                            if(classType.equals("BLOCK")){

                                                blockClassRecyclerView.setLayoutManager(new LinearLayoutManager(SeeBlockClass.this));
                                                ModelFreeClassShow modelFreeClassShow = ds.getValue(ModelFreeClassShow.class);

                                                blockArrayList.add(modelFreeClassShow);
                                            }
                                        }
                                        adapterBlockClass = new AdapterBlockClass(blockArrayList, SeeBlockClass.this);
                                        blockClassRecyclerView.setAdapter(adapterBlockClass);


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

    }
}