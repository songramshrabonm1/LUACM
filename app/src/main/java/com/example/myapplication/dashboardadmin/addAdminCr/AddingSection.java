package com.example.myapplication.dashboardadmin.addAdminCr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.register.AfterRegLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;

public class AddingSection extends AppCompatActivity {

    private TextView selectrule , textViewss ;
    private String Rule = "" , typee = "";
    private EditText name , emaill , phonee ;
    private AppCompatButton upload ;
    private FirebaseAuth firebaseAuth;


    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_section);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loadinggg");
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();



        typee = getIntent().getStringExtra("Type");
        typee = typee.toString().toUpperCase();

        textViewss = findViewById(R.id.setTEXT);
        textViewss.setText(getIntent().getStringExtra("Type"));


        if(typee.equals("CRADDADMINADD")) {
            selectrule = findViewById(R.id.Select_Rule);
            selectrule.setVisibility(View.VISIBLE);
            selectrule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectRulee();
                }
            });
        }else{
            selectrule = findViewById(R.id.Select_Rule);
            selectrule.setVisibility(View.GONE);

        }

        name = findViewById(R.id.nameee);
        emaill = findViewById(R.id.Emails);
        phonee = findViewById(R.id.phnNumber);
        upload = findViewById(R.id.UploadControlling);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDATAINPUT();
            }
        });




    }

   public String Nname = "" , email = "" , Mobile = "" , pass = "" , savedPassword = "";
    ;
    boolean newAccountCreated = false;
    private void validateDATAINPUT() {
        Nname = name.getText().toString().trim();
        email = emaill.getText().toString().trim();
        Mobile = phonee.getText().toString().trim();
        pass = email + Mobile;




        if(TextUtils.isEmpty(Nname)){
            Toast.makeText(AddingSection.this, "Set Name" , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(email)){
            Toast.makeText(AddingSection.this, "Set Email" , Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(Mobile) && (Mobile.length() == 11 || (Mobile.length()==14 && Mobile.startsWith("+880")))){

            Toast.makeText(AddingSection.this, "Set Mobile number  Correctly" , Toast.LENGTH_SHORT).show();

        }else if(typee.equals("CRADDADMINADD") && TextUtils.isEmpty(Rule)){
            Toast.makeText(AddingSection.this, "Set Rule" , Toast.LENGTH_SHORT).show();

        }else{
            uploadDatabase();
        }
    }

    private void uploadDatabase() {
        progressDialog.setMessage("Uploading Information....");
        progressDialog.show();
        String tp = "";
        HashMap<String , Object> hashMap = new HashMap<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AdminAddingSection");
        long time = System.currentTimeMillis();
        if(typee.equals("CRADDADMINADD")){
            if(Rule.equals("ADMIN")){
                Rule = "ADMIN";
                hashMap.put("ADMIN" ,"ADMIN" );
                hashMap.put("CR" , "NO");

            }else if(Rule.equals("CR")){
                Rule = "CR";
                hashMap.put("ADMIN" ,"NO");
                hashMap.put("CR" , "CR");
            }else if(Rule.equals("ADMIN_OR_CR")){
                hashMap.put("ADMIN" , "ADMIN");
                hashMap.put("CR", "CR");

                //prothom check korbe ADMIN ki na tarpor check korbe CR ki na tarpor o na hole pdf routine jst dekhte parbe
            }

            tp = "CRADDADMINADD";
            hashMap.put("name",Nname);
            hashMap.put("email",email);
            hashMap.put("Mobile", Mobile);
            hashMap.put("Timestamp" , time);
        }else{
            tp = "ADDEMAILREGISTERSTUDENT";
            hashMap.put("name",Nname);
            hashMap.put("email",email);
            hashMap.put("Mobile", Mobile);
            hashMap.put("Timestamp" , time);
            hashMap.put("ADMIN", "NO");
            hashMap.put("CR", "NO");
            Rule = "USSERTYPE";

        }



        checkAlreadyExist(new checkCompletionCallBack() {
            @Override
            public void onCheckComplete(boolean data) {
                if(data == true){
                    UploadedUserDATABASE();

                }else{
                    String safeEmail = email.replace("." , ",");

                    DatabaseReference UpdateInformationUser  = FirebaseDatabase.getInstance().getReference("Users");
                    UpdateInformationUser.child(safeEmail)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        HashMap<String, Object> updateHashMap = new HashMap<>();
                                        updateHashMap.put("userType", Rule);
                                        UpdateInformationUser.child(safeEmail).updateChildren(updateHashMap)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(AddingSection.this , "Set User Type" + Rule , Toast.LENGTH_SHORT).show();

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(AddingSection.this , "Set User Type Failed" , Toast.LENGTH_SHORT).show();

                                                    }
                                                });

                                    }else{
                                        UploadedUserDATABASE();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                }
            }
        });

        ref.child(tp)
                .child(""+email.replace("." , ","))
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(AddingSection.this , "Uploading Succefully..." , Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AddingSection.this , "Uploading not Succefully..." , Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void UploadedUserDATABASE() {
        progressDialog.setMessage("STORE INFORMATION");
        progressDialog.show();
        long timestamp = System.currentTimeMillis();
        String UniqueId = firebaseAuth.getUid();
        System.out.println("UniqueId: "+ UniqueId);

        HashMap<String, Object> hashMap1 = new HashMap<>(); // HashMap তৈরি করা, যা ব্যবহারকারী তথ্য সংরক্ষণ করবে।
        hashMap1.put("uid", UniqueId);
        hashMap1.put("email", email);
        hashMap1.put("Name", Nname);
        hashMap1.put("Batch" , "");
        hashMap1.put("StudentId", "");
        hashMap1.put("ProfileImage", "");
        hashMap1.put("userType",Rule );
        hashMap1.put("timestamp", timestamp);
        hashMap1.put("passs", pass);

        String safeEmail = email.replace("." , ",");
        DatabaseReference newUserSaveRefUsers = FirebaseDatabase.getInstance().getReference("Users");
        newUserSaveRefUsers.child(safeEmail)
                .setValue(hashMap1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(AddingSection.this, "Save information Database" , Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddingSection.this, "Save information Database is not successful" , Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void checkAlreadyExist(checkCompletionCallBack callBack) {
        String  previousEamil = firebaseAuth.getCurrentUser().getEmail();

        String safeEmail = previousEamil.replace(".",",");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(safeEmail).child("passs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                     savedPassword = ""+snapshot.getValue();
                    Toast.makeText(AddingSection.this, "Saved Password: " + savedPassword, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddingSection.this, "No password found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddingSection.this, "Failed to retrieve password!", Toast.LENGTH_SHORT).show();
            }
        });

        if(savedPassword.isEmpty()){
            callBack.onCheckComplete(false);

        }
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseAuth.getInstance().signOut();
                        firebaseAuth.signInWithEmailAndPassword(previousEamil, savedPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(AddingSection.this, " Again sign in successfully" , Toast.LENGTH_SHORT).show();
                            }
                        });
                        callBack.onCheckComplete(task.isSuccessful());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onCheckComplete(false);
                    }
                });

    }
    private void selectRulee() {
        String [] ruleee = {"ADMIN" , "CR" , "ADMIN_OR_CR"} ;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Rule")
                .setItems(ruleee, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Rule = ruleee[i];
                        selectrule.setText(Rule);
                    }
                }).show();
    }
}