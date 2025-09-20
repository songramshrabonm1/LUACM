package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class CprogrammingModuleClasses extends AppCompatActivity {

    AppCompatButton addDriveLinkBtn;
    ImageButton backButton;
    EditText driveLinkText , className;
    String id ;
    ProgressDialog progressDialog ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cprogramming_module_classes);

        id = getIntent().getStringExtra("id");

        progressDialog = new ProgressDialog(CprogrammingModuleClasses.this);
        progressDialog.setTitle("Loading....");
        progressDialog.setCanceledOnTouchOutside(false);


        backButton = findViewById(R.id.backBtnnc);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        driveLinkText = findViewById(R.id.googleDriveLink);
        className = findViewById(R.id.ClassNumber);

        addDriveLinkBtn = findViewById(R.id.AddClassDriveLink);
        addDriveLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CprogrammingModuleClasses.this);
                builder.setIcon(R.drawable.baseline_add_to_drive_24);
                builder.setTitle("Check Drive Link")
                                .setMessage("Are You Sure Your Drive Link Work Perfectly...")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                validateData();
                                            }
                                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();

            }
        });
    }

    String driveLink = "" , classNumber = "";
    private void validateData() {
        driveLink = driveLinkText.getText().toString().trim();
        classNumber = className.getText().toString().trim();


        if(TextUtils.isEmpty(driveLink)){
            Toast.makeText(CprogrammingModuleClasses.this, "Your Drive Link Edit Text Empty . It was mentioned at the beginning to repeatedly check whether your drive link is accurate or not." , Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(classNumber)){
            Toast.makeText(CprogrammingModuleClasses.this, "Your Class Edit TExt Empty" , Toast.LENGTH_SHORT).show();
        }else{
            UploadedDriveLinkDatabase();
        }

    }

    private void UploadedDriveLinkDatabase() {
        progressDialog.setMessage("Uploaded Classes Drive Ling....");
        progressDialog.show();

        long Timestamp = System.currentTimeMillis();
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("DriveLink", driveLink);
        hashMap.put("Classs", classNumber);
        hashMap.put("Timestamp" , Timestamp);
        hashMap.put("id" , ""+Timestamp);
        hashMap.put("ModuleId", ""+id);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("CProgramming")
                .child(id)
                .child("Classes")
//                .child(""+System.currentTimeMillis()) আর কোন সময় এভাবে Timestamp child এর মধ্যে add করব না যদি সেটি পরবর্তীতে আইডির জন্যে সেট করি । কারণ এভাবে দুইবার সেট করলে দুইটা আলাদা আলাদা টাইম সেট হয়ে জয় এর ফলে সেম না হওয়ার জন্যে আমরা এগুলোকে ধরে ফেচ করতি সমসম্যা  হয়ে যাবে ।
                .child(""+Timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(CprogrammingModuleClasses.this, "Uploaded Class Drive Link Successfully" , Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(CprogrammingModuleClasses.this, ""+e.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                });

    }
}