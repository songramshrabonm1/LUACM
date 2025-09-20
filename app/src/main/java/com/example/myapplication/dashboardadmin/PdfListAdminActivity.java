package com.example.myapplication.dashboardadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PdfListAdminActivity extends AppCompatActivity {


    private ArrayList<ModelPdf> pdfarrayList;
    private AdapterPdfAdmin adapterPdfAdmin;
    private ImageButton bckBtn;
    private RecyclerView recyclerView;
    private EditText searchEtt    ;
    private TextView titleee;
    ProgressDialog progressDialog;


    private String categoryId, categoryTitle , UserType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_list_admin);

        //****************INTENT PART START & CATEGORY TITLE SET****************
//        intent দিয়ে আমরা উপরের টাইটেল টাকে  সেট করতে  পারব । মনে আমি যে ক্যাটাগরি টার ভিতরে ডুকবো সেটার নাম আসবে ।
        Intent intent = getIntent();
        categoryId = intent.getStringExtra("categoryId");
        categoryTitle = intent.getStringExtra("categoryTitle");
        UserType = intent.getStringExtra("UserType");

        titleee = findViewById(R.id.sub_title_tv);// category name set
        titleee.setText(categoryTitle);
        //****************INTENT PART END & CATEGORY TITLE SET****************


        //***************************BACK BUTTON PART START*******************************
        bckBtn = findViewById(R.id.back_btn);
        bckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //***************************BACK BUTTON PART END*******************************



        //***************************SEARCH EDITTEXT PART START**************************
        searchEtt = findViewById(R.id.search_Et);
        searchEtt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapterPdfAdmin.getFilter().filter(s);

                }catch (Exception e){
                    Toast.makeText(PdfListAdminActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //***************************SEARCH EDITTEXT PART START**************************



//        ******************************RECYCLERVIEW LOAD PDF PART START*********************
        recyclerView = findViewById(R.id.bookRv);
        // Setup RecyclerView layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadPdfList();
    }
    private void loadPdfList() {
        pdfarrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.orderByChild("categoryId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pdfarrayList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ModelPdf model = ds.getValue(ModelPdf.class);
                            if(model != null){
                                pdfarrayList.add(model);
                            }else{
                                Log.e("DATA_ERROR", "Null PDF data retrieved from Firebase");
                            }
                        }
                        // If the adapter is not set, set it now
                        if (adapterPdfAdmin == null) {
                            adapterPdfAdmin = new AdapterPdfAdmin(PdfListAdminActivity.this, pdfarrayList , UserType);
                            recyclerView.setAdapter(adapterPdfAdmin);
                        } else {
                            // Notify the adapter of changes
                            adapterPdfAdmin.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
//                ******************************RECYCLERVIEW LOAD PDF PART END*********************

    }
}