package com.example.myapplication.dashboardadmin.problemsloving.ratingWise;

import static androidx.constraintlayout.motion.widget.TransitionBuilder.validate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Platform;

import java.util.HashMap;

public class EditCategories extends AppCompatActivity {

    private ImageButton backButton;
    private EditText rating , vjudgeLink , password;
    private AppCompatButton UpdateddRatingButton ;
    private TextView choseSection;
    String platformName , link, ratings , passwordd;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_categories);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));


        progressDialog = new ProgressDialog(EditCategories.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        backButton = findViewById(R.id.BackButtonRatingUpload);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        link = getIntent().getStringExtra("links");
        platformName = getIntent().getStringExtra("platformName");
        ratings = getIntent().getStringExtra("rating");
        passwordd = getIntent().getStringExtra("password");




        rating = findViewById(R.id.ratinggs);
        vjudgeLink = findViewById(R.id.vjudgelink);
        password = findViewById(R.id.Vjudge_PasswordSet);
        UpdateddRatingButton = findViewById(R.id.UpdateddRating);
        choseSection = findViewById(R.id.choseSection);

        rating.setText(ratings);
        vjudgeLink.setText(link);
        password.setText(passwordd);
        choseSection.setText(platformName +"Edit");




        UpdateddRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInputValue();
            }
        });





    }

    String write = "" , linkUpdated = "" , UpdatedPassword = "";
    private void validateInputValue() {
        write = rating.getText().toString().trim();
        linkUpdated = vjudgeLink.getText().toString().trim();
        UpdatedPassword = password.getText().toString().trim();

        if(TextUtils.isEmpty(write)){
            Toast.makeText(EditCategories.this, "Update the value" , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(linkUpdated)){
            Toast.makeText(EditCategories.this, "Update the Link..." ,Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(UpdatedPassword)){
            Toast.makeText(EditCategories.this, "Update the password",Toast.LENGTH_SHORT).show();
        }else{
            updatedInputValueIntoDb();
        }
    }

    private void updatedInputValueIntoDb() {

//        String write = "" , linkUpdated = "" , UpdatedPassword = "";

        HashMap<String , Object> hashMap    = new HashMap<>();
        hashMap.put("rating" ,write );
        hashMap.put("link" , linkUpdated);
        hashMap.put("password" , UpdatedPassword);


        progressDialog.setMessage("Updating.....");
        progressDialog.show();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProblemSet");
        ref.child(""+ platformName)
                .child(ratings)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(EditCategories.this , "This rating already exist so,Updated Database successfully " , Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(EditCategories.this , "This rating already exist , Updated Database is not successfull"+e.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });
    }

}