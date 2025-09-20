package com.example.myapplication.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.AdminDashboardHome;
import com.example.myapplication.dashboardadmin.DashboardAdmin;
import com.example.myapplication.dashboarduser.UserDashboardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView newUser , forgetPaswords  ;
    private Button login;
    private EditText emaill, passwordd;
    private   boolean flag = false    ;
    private String DeviceId = "";
    private Button backButtonLoginss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));




        firebaseAuth = FirebaseAuth.getInstance();

        emaill = findViewById(R.id.email_id);
        passwordd = findViewById(R.id.password);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        login = findViewById(R.id.buttonLogin);
        forgetPaswords = findViewById(R.id.forgetPasword);

        backButtonLoginss = findViewById(R.id.backButtonLogin);
        backButtonLoginss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        newUser = findViewById(R.id.newUserId);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//                finish();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        forgetPaswords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPass();
            }
        });


    }



    private String email = "", password = "";





    private void validateData() {

        email = emaill.getText().toString();
        password = passwordd.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(LoginActivity.this, "Invalid email address ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "enter Password  ", Toast.LENGTH_SHORT).show();
        }
        else {
            loginAccountInFirebase(email, password);
        }


    }



    private void loginAccountInFirebase(String email, String password) {
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
//                        checkUser();
                        deviceId();

                    }else{
                        Toast.makeText(LoginActivity.this, "This email is not verified" , Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void forgetPass() {
        if(emaill.getText().toString().isEmpty()){
            emaill.setError("Please Enter Email id");
        }else{
            firebaseAuth.sendPasswordResetEmail(emaill.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Password Reset email sent your email" , Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    private void checkUser() {

        progressDialog.setMessage("Checking User....");
        progressDialog.show();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();// বর্তমান  ব্যবহারকারী প্রাপ্ত করা হচ্ছে।

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");// "Users" নোডের রেফারেন্স প্রাপ্ত করা।
        String safeEmail = email.replace("." , ",");

        ref.child(safeEmail)// ব্যবহারকারীর UID ব্যবহার করে ডেটা খুঁজে পাওয়া।
                .addListenerForSingleValueEvent(new ValueEventListener() { // একক মানের জন্য শ্রোতা যোগ করা হয়েছে।
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {// ডেটা পরিবর্তন হলে ডাকা হয়।
                        progressDialog.dismiss();
                        String userType = "" + snapshot.child("userType").getValue(); // snapshot থেকে আমরা Data গুলো পাব . usertype child থেকে ডাটা টা  নিয়ে আসতেছি।
                        userType = userType.toString().toUpperCase();
//                        if (userType.equals("USER")) {
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                            finish();
//                        } else if (userType.equals("ADMIN")) {
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                            finish();
//                        }else{
//                            Toast.makeText(LoginActivity.this, "You are Not Valid User", Toast.LENGTH_SHORT).show();
//                        }
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    private void deviceId() {

        String DeviceId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                .child(email.replace('.', ','))
                .child("Devices");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean IsDevice = false;
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(ds.getKey().equals(DeviceId)){
                        IsDevice = true;
                        break;
                    }
                }
                if(IsDevice){
                    checkUser();
                }else{
                    if(snapshot.getChildrenCount() >= 1){
                        String OlderKey = snapshot.getKey();
                        ref.child(OlderKey).removeValue();
                    }

                    ref.child(DeviceId).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                                 if(task.isSuccessful()){
                                     Toast.makeText(LoginActivity.this, "Change Your Devices Id", Toast.LENGTH_SHORT).show();

                                     checkUser();
                                 }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "Failed to update device list", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}