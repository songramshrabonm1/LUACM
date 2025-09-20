package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Conceptual;

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
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.AlgoModuleClassesEdit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ConceptualModuleClassesEdit extends AppCompatActivity {

    AppCompatButton updatedDriveLinkBtn ;
    EditText driveLink , classNamee;
    ImageButton backButtonn;
    ProgressDialog progressDialog;
    String Classid , moduleId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conceptual_module_classes_edit);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading....");
        progressDialog.setCanceledOnTouchOutside(false);

        moduleId = getIntent().getStringExtra("ModuleId");
        Classid = getIntent().getStringExtra("id");


        System.out.println("ModuleID" + moduleId);
        System.out.println("Class Id " + Classid);

        driveLink = findViewById(R.id.googleDriveLink);
        classNamee = findViewById(R.id.ClassNumber);

        classNamee.setText(getIntent().getStringExtra("classNumber") );
        driveLink.setText(getIntent().getStringExtra("driveLink") );



        backButtonn = findViewById(R.id.backBtnnc);
        backButtonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        updatedDriveLinkBtn = findViewById(R.id.AddClassDriveLink);
        updatedDriveLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ConceptualModuleClassesEdit.this);
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

    String updatedDriveLink = "", updatedClassName = "";
    private void validateData() {
        updatedDriveLink = driveLink.getText().toString().trim();
        updatedClassName = classNamee.getText().toString().trim();

        if(TextUtils.isEmpty(updatedDriveLink)){
            Toast.makeText(ConceptualModuleClassesEdit.this, "Drive link empty" , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(updatedClassName)){
            Toast.makeText(ConceptualModuleClassesEdit.this, "class Name empty" , Toast.LENGTH_SHORT).show();
        }else{
            updateDatabaseDriveLink();
        }
    }

    private void updateDatabaseDriveLink() {
        progressDialog.setMessage("Uploaded Classes Drive Ling....");
        progressDialog.show();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("DriveLink", updatedDriveLink);
        hashMap.put("Classs", updatedClassName);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("Conceptual")
                .child(moduleId)
                .child("Classes")
                .child(Classid)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(ConceptualModuleClassesEdit.this, "Updated Drive Link And Class Number Successfully" , Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ConceptualModuleClassesEdit.this, ""+e.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                });
    }
}