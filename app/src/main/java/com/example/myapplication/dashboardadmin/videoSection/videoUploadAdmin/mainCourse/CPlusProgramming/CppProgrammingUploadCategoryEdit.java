package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CprogrammingUploadCategoryEdit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CppProgrammingUploadCategoryEdit extends AppCompatActivity {


    private ImageButton backButton ;
    private AppCompatButton updateBtn;
    private String id ;
    private ProgressDialog progressDialog;
    private EditText module, videoTitle , totalVideoCnt, InstructorName ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpp_programming_upload_category_edit);


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
            Toast.makeText(CppProgrammingUploadCategoryEdit.this, "Update Module" , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(VideoTitle)){
            Toast.makeText(CppProgrammingUploadCategoryEdit.this, "Update Video Title" , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(UpdateTotalVideoCnt)){
            Toast.makeText(CppProgrammingUploadCategoryEdit.this, "Update Total Video count" , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(UpdateInstructorName)){
            Toast.makeText(CppProgrammingUploadCategoryEdit.this, "Update Instructor Name" , Toast.LENGTH_SHORT).show();

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
        ref.child("CPlusProgramming")
                .child(id)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(CppProgrammingUploadCategoryEdit.this, "Updating Module succefully..." , Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(CppProgrammingUploadCategoryEdit.this, ""+e.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                });
    }
}