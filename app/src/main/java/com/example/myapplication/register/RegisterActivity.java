package com.example.myapplication.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.dashboarduser.UserDashboardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    //    private ActivityRegisterBinding binding;
    EditText namee, emaill, passwordd, cpassword , batch , studentId ;
    Button  signUp;
    private ImageButton backBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView backLogin ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));



        namee = findViewById(R.id.Name);
        emaill = findViewById(R.id.email_id);
        passwordd = findViewById(R.id.password);
        cpassword = findViewById(R.id.Reenterpassword);
        batch = findViewById(R.id.Batch);
        studentId = findViewById(R.id.stdId);



        backLogin = findViewById(R.id.backLogin);
        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                checkAnyMemberPresent();
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance(); //firebaseauth এর অবজেক্ট তৈরি করে ফেললাম


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);




        signUp = findViewById(R.id.buttonRegister);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validData();
            }
        });
    }



    private String Name = "", email = "", password = "", confirmPassworod = "" , batchs = "" , stdId = "";

    private void validData() {
        Name = namee.getText().toString().trim();
        email = emaill.getText().toString().trim();
        batchs = batch.getText().toString().trim();
        stdId = studentId.getText().toString().trim();

        password = passwordd.getText().toString();
        confirmPassworod = cpassword.getText().toString();

        if (TextUtils.isEmpty(Name)) {
            Toast.makeText(this, "Enter your Nmae ", Toast.LENGTH_SHORT).show();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email ", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(batchs) || batchs.length() > 2){
            Toast.makeText(this, "Enter Batch" , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(stdId)){
            Toast.makeText(this, "Enter Student Id" , Toast.LENGTH_SHORT);
        }
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();

        } else if (!password.equals(confirmPassworod)) {
            Toast.makeText(this, "Enter same password", Toast.LENGTH_SHORT).show();

        } else {
            createUserAccount();// যদি সব সঠিক হয়, createUserAccount মেথড কল করা।
        }


    }
    private void createUserAccount(){// createUserAccount মেথড, যা নতুন ব্যবহারকারী অ্যাকাউন্ট তৈরি করে।
        progressDialog.setMessage("Creating Account ...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password) // এইটার ভিতরে দুইটা parameter আছে , email আর password // // ইমেইল এবং পাসওয়ার্ড দিয়ে ব্যবহারকারী তৈরি।
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() { // সফল হলে এই কলব্যাক চলবে।
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        progressDialog.dismiss();
                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RegisterActivity.this, "Send Your email vefication mail...Please confirm ", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                Intent intent = new Intent(RegisterActivity.this, AfterRegLogin.class);
                                intent.putExtra("Name",Name) ;
                                intent.putExtra("email",email) ;
                                intent.putExtra("batchs",batchs) ;
                                intent.putExtra("stdId",stdId) ;
                                intent.putExtra("password" , password);
                                startActivity(intent);
                            }
                        });



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}