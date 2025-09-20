package com.example.myapplication.dashboardadmin;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CategoryAddActivity extends AppCompatActivity {

    //*******************************VARIABLE DECLARE********************************
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private AppCompatButton submitBtn ;
    private ImageButton backbtn ;
    private EditText categoryEditText;
    //*******************************VARIABLE DECLARE********************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        //*************************************Progress dialgoue*******************************
        progressDialog = new ProgressDialog(this); //progressDialogue এর অবজেক্ট ডিক্লেয়ার করা;this use করেছি কারণ এই Acitivity তে লোড হবে
        progressDialog.setTitle("please wait ...."); //টাইটেল সেট করে দিয়েছি
        progressDialog.setCanceledOnTouchOutside(false); // টার্চ করলে যাতে প্রগ্রেস ডাইলগ অফ না হয়ে যায়
        //*************************************Progress dialgoue*******************************

        //****************************FIREBASE***************************
        firebaseAuth = FirebaseAuth.getInstance(); // FirebaseAuth এর থেকে যেন unique id টা পাই তখন FirebaseAuth এর দরকার পরবে ।
        //****************************FIREBASE***************************

        //***********************************INITIALIZE*************************************
        submitBtn = findViewById(R.id.submit_btn);
        categoryEditText = findViewById(R.id.category_Et);
        backbtn = findViewById(R.id.bacck);
        //***********************************INITIALIZE*************************************

        //*******************************This button created for going to previous activity*******************************
        backbtn.setOnClickListener(new View.OnClickListener() { // ব্যাক বাটনের ক্লিক ইভেন্ট।
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryAddActivity.this,DashboardAdmin.class));
                finish();// Activity শেষ করে দিচ্ছি।
            }
        });
        //*******************************This button created for going to previous activity*******************************

        submitBtn.setOnClickListener(new View.OnClickListener() { // সাবমিট বাটনের ক্লিক ইভেন্ট।
            @Override
            public void onClick(View v) {
                validData();
            }
        });
    }

    String category = "";
    //*********************************EDITTEXT E VALUE ACHE KI NA TA CHECK KORTESI****************************
    private void validData() {
        category = categoryEditText.getText().toString().trim();//category edit text এ যা লিখেছি সেটা string এ convert করে নিচ্ছি।

        if(TextUtils.isEmpty(category)){ // TextUtils class এর সহয়তা নিয়ে আমরা category টা প্রথম check করে নিচ্ছি এটা empty আছে কি না ।
            Toast.makeText(this,"please enter this category ....",Toast.LENGTH_SHORT).show();
        }else {
            addCategoryFirebase(); // // ক্যাটেগরি ফাকা না থাকলে Firebase এ যোগ করার জন্য কল।
        }
    }
    //*********************************EDITTEXT E VALUE ACHE KI NA TA CHECK KORTESI****************************

    //****************************************************ADDCATEGORY FIREBASE****************************************************
    private void addCategoryFirebase() {
        progressDialog.setMessage("Adding Category");
        progressDialog.show();
        long timestamp = System.currentTimeMillis(); // একটি ইউনিক timestamp তৈরি করা।
        HashMap<String, Object> hashMap = new HashMap<>();  // ক্যাটেগরি ডেটা রাখার জন্য HashMap তৈরি। // HashMap হল key-value পেয়ারের একটি collection, যা ডেটা সঞ্চয় করতে ব্যবহৃত হয়।

        hashMap.put("id", "" + timestamp);
        hashMap.put("category", "" + category);
        hashMap.put("timestamp", timestamp);
        hashMap.put("uid", firebaseAuth.getUid()); // HashMap এ ব্যবহারকারীর UID যোগ করা।
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Category"); // Firebase Database এর reference।
        // Child node হিসেবে timestamp ব্যবহার।
        ref.child("" + timestamp)
                // HashMap এর ডেটা Firebase এ আপলোড।
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) { // সফল হলে কি হবে তা নির্দেশ।
                        progressDialog.dismiss();
                        Toast.makeText(CategoryAddActivity.this, "Category update", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CategoryAddActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        ////****************************************************ADDCATEGORY FIREBASE****************************************************


    }

}


