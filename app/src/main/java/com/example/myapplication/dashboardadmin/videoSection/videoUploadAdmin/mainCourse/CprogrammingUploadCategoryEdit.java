package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CprogrammingUploadCategoryEdit extends AppCompatActivity {


    ImageButton backButton ;
    AppCompatButton updateBtn;
    String id ;
    ProgressDialog progressDialog;
    EditText module, videoTitle , totalVideoCnt, InstructorName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cprogramming_upload_category_edit);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading....");
        progressDialog.setCanceledOnTouchOutside(false);


        backButton = findViewById(R.id.backBtnnc);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        id = getIntent().getStringExtra("id");
        String Module = getIntent().getStringExtra("module");
        String VideoTitle = getIntent().getStringExtra("VideoTitle");
        String TotalVideoCnt = getIntent().getStringExtra("TotalVideoCount");
        String Instructor = getIntent().getStringExtra("instructorName");


        module = findViewById(R.id.moduleet);
        videoTitle = findViewById(R.id.videoTitle_Et);
        totalVideoCnt = findViewById(R.id.totalVideoCount);
        InstructorName = findViewById(R.id.instructorName);


        module.setText(Module);
        videoTitle.setText(VideoTitle);
        totalVideoCnt.setText(TotalVideoCnt);
        InstructorName.setText(Instructor);


        updateBtn = findViewById(R.id.UpdateModuleBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatedData();
            }
        });


    }

    String UpdateModule = "" , VideoTitle = "", UpdateTotalVideoCnt = "", UpdateInstructorName = "";
    private void validatedData() {
        UpdateModule = module.getText().toString().trim();
        VideoTitle = videoTitle.getText().toString().trim();
        UpdateTotalVideoCnt = totalVideoCnt.getText().toString().trim();
        UpdateInstructorName = InstructorName.getText().toString().trim();


        if(TextUtils.isEmpty(UpdateModule)){
            Toast.makeText(CprogrammingUploadCategoryEdit.this, "Update Module" , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(VideoTitle)){
            Toast.makeText(CprogrammingUploadCategoryEdit.this, "Update Video Title" , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(UpdateTotalVideoCnt)){
            Toast.makeText(CprogrammingUploadCategoryEdit.this, "Update Total Video count" , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(UpdateInstructorName)){
            Toast.makeText(CprogrammingUploadCategoryEdit.this, "Update Instructor Name" , Toast.LENGTH_SHORT).show();

        }else{
            UpdateDatabase();
        }
    }

    private void UpdateDatabase() {
        progressDialog.setMessage("Updateing Module...");
        progressDialog.show();

        HashMap<String , Object> hashMap = new HashMap<>();

        hashMap.put("InstructorName",UpdateInstructorName );
        hashMap.put("TotalVideo",UpdateTotalVideoCnt);
        hashMap.put("VideoTitle",VideoTitle);
        hashMap.put("Module",UpdateModule);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("CProgramming")
                .child(id)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(CprogrammingUploadCategoryEdit.this, "Updating Module succefully..." , Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(CprogrammingUploadCategoryEdit.this, ""+e.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                });

    }
}