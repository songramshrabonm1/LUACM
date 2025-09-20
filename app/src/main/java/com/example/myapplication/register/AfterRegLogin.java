package com.example.myapplication.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AfterRegLogin extends AppCompatActivity {

    private String rname = "";
    private String remail  = "";
    private String rpassword= "" ;
    private String rstdId = "";
    private String rbatch = "";

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView backSignUpPage ;
    private AppCompatButton AfterReglogin;
    private EditText emaill, passwordd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_reg_login);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));


         rname = getIntent().getStringExtra("Name");
//         rname = toString().trim();
         remail = getIntent().getStringExtra("email");
//         remail = toString().trim();
         rpassword = getIntent().getStringExtra("password");
//         rpassword = toString().trim();
         rstdId = getIntent().getStringExtra("stdId");
         rbatch = getIntent().getStringExtra("batchs");

        System.out.println("Remail : "+ remail);
        System.out.println("RPass: "+rpassword);

        firebaseAuth = FirebaseAuth.getInstance();

        emaill = findViewById(R.id.email_id);
        passwordd = findViewById(R.id.password);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        AfterReglogin = findViewById(R.id.buttonLogin);
        backSignUpPage = findViewById(R.id.newUserId);





        AfterReglogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateData();
            }
        });

        backSignUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkingUserPresentOrNot();

//                finish();
            }
        });
    }


    String email =  "" , pass = "";
    private void validateData() {
        email = emaill.getText().toString().trim();
        pass = passwordd.getText().toString().trim();

        System.out.println("email: "+email);
        System.out.println("pass:  "+pass);

        remail = firebaseAuth.getCurrentUser().getEmail();
//        rpassword = firebaseAuth.getCustomAut

        System.out.println("Remail : "+ remail);
        System.out.println("RPass: "+rpassword);


        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Input email" , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Input Password" , Toast.LENGTH_SHORT).show();
        }else if(!email.equals(remail)){
            Toast.makeText(this, "Email Is not match" , Toast.LENGTH_SHORT).show();
        }else{
            LoginAccount();
        }
    }

    private void LoginAccount() {
        progressDialog.setMessage("Verified Your Account...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(firebaseAuth.getCurrentUser().isEmailVerified()){
                            progressDialog.dismiss();
                            Toast.makeText(AfterRegLogin.this, "Your Account Is Verified" , Toast.LENGTH_SHORT).show();
                            uploadInformationDATABASES();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(AfterRegLogin.this, "Your account is not verified" , Toast.LENGTH_SHORT).show();
                            checkingUserPresentOrNot();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AfterRegLogin.this, "Faild to verification ", Toast.LENGTH_SHORT).show();
                        checkingUserPresentOrNot();

                        e.printStackTrace();
                    }
                });
    }


    private void checkingUserPresentOrNot() {
        if(firebaseAuth.getCurrentUser().isEmailVerified()) {
        }else{
            AuthCredential authCredential = EmailAuthProvider.getCredential(remail, rpassword);
            firebaseAuth.getCurrentUser().reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        firebaseAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(AfterRegLogin.this, "Deleted Your Account , Again register By your email", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(AfterRegLogin.this, RegisterActivity.class));
                                finish();
                            }
                        });
                    }
                }
            });
        }
    }

    private void uploadInformationDATABASES() {
        progressDialog.setMessage("Upload Information Into Database please wait");
        progressDialog.dismiss();

        long timestamp = System.currentTimeMillis();
        String UniqueId = firebaseAuth.getUid();
        System.out.println("UniqueId: "+ UniqueId);

        HashMap<String, Object> hashMap = new HashMap<>(); // HashMap তৈরি করা, যা ব্যবহারকারী তথ্য সংরক্ষণ করবে।
        hashMap.put("uid", UniqueId);
        hashMap.put("email", remail);
        hashMap.put("Name", rname);
        hashMap.put("Batch" , rbatch);
        hashMap.put("StudentId", rstdId);
        hashMap.put("ProfileImage", "");
        hashMap.put("userType", "USER");
        hashMap.put("timestamp", timestamp);
        hashMap.put("passs",rpassword);

        String safeEmail = remail.replace(".", ",");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users"); // "Users" নোডের রেফারেন্স প্রাপ্ত করা।
        ref.child(safeEmail)
//        ref.child(UniqueId) //// UID ব্যবহার করে নতুন নোড তৈরি।
                .setValue(hashMap)// HashMap ডেটা হিসেবে সেট করা।
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(AfterRegLogin.this, "account created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AfterRegLogin.this, LoginActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AfterRegLogin.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }
}