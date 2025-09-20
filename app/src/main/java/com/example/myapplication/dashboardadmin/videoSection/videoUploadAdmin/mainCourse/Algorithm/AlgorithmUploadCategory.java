package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm;

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
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.CpluseprogrammingUploadCategory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AlgorithmUploadCategory extends AppCompatActivity {

    private AppCompatButton ModuleCreateBtn;
    private ImageButton backButtonC;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    EditText module, videoTitle , TodayTotalVide , InstructorName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm_upload_category);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Upload Module");
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth = FirebaseAuth.getInstance();

        backButtonC = findViewById(R.id.backBtnnc);
        backButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        module = findViewById(R.id.moduleet);
        videoTitle = findViewById(R.id.videoTitle_Et);
        TodayTotalVide = findViewById(R.id.totalVideoCount);
        InstructorName = findViewById(R.id.instructorName);
        ModuleCreateBtn = findViewById(R.id.CreateModuleBtn);

        ModuleCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDatat();
            }
        });
    }
    String ModuleName = "" , videoTitleName ="", TodayTotalVideoCount = "" , InstructorNAme = "" ;
    private void validateDatat() {

        ModuleName = module.getText().toString().trim();
        videoTitleName = videoTitle.getText().toString().trim();
        TodayTotalVideoCount = TodayTotalVide.getText().toString().trim();
        InstructorNAme = InstructorName.getText().toString().trim();

        if(TextUtils.isEmpty(ModuleName)){
            Toast.makeText(AlgorithmUploadCategory.this, "Enter The Module Name" , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(videoTitleName)){
            Toast.makeText(AlgorithmUploadCategory.this, "Enter The Video Title" , Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(TodayTotalVideoCount)){
            Toast.makeText(AlgorithmUploadCategory.this, "Enter The Total Video Number" , Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(InstructorNAme)){
            Toast.makeText(AlgorithmUploadCategory.this, "Enter The Instructor Name" , Toast.LENGTH_SHORT).show();
        }else{
            UploadedModuleDatabase();
        }
    }

    private void UploadedModuleDatabase() {

        progressDialog.setMessage("UpLoading Module");
        progressDialog.show();

        long timeStamp = System.currentTimeMillis();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Module" , ModuleName);
        hashMap.put("VideoTitle", videoTitleName);
        hashMap.put("TotalVideo", TodayTotalVideoCount);
        hashMap.put("InstructorName", InstructorNAme);
        hashMap.put("timeStamp", timeStamp);
        hashMap.put("id",""+timeStamp);
        hashMap.put("uid", firebaseAuth.getUid());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Video");
        reference.child("Algo")
                .child(""+timeStamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(AlgorithmUploadCategory.this, "Uploaded Module Successfully ", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AlgorithmUploadCategory.this,  ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

}