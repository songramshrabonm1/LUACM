package com.example.myapplication.dashboardadmin.addAdminCr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class EditInformation extends AppCompatActivity {

   private  String name, type , Rule , email , timestamp , mbl ,cr , admin , valuesInfo ;
    private TextView setTEXTEDits , selectrule ;
    private EditText nameeeEdits, EmailsEdits , phnNumberEdits ;
    private ProgressDialog progressDialog ;
    private AppCompatButton updatee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);

        progressDialog = new ProgressDialog(EditInformation.this);
        progressDialog.setTitle("loading");
        progressDialog.dismiss();

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));



//        intent.putExtra("name",name);
//        intent.putExtra("email", email);
//        intent.putExtra("mbl", mbl);
//        intent.putExtra("timestamp",timestamp );
//        intent.putExtra("cr", cr);
//        intent.putExtra("admin",admin);
//        intent.putExtra("valueeInfo", valueeInfo);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        mbl = getIntent().getStringExtra("mbl");
        timestamp = getIntent().getStringExtra("timestamp");
        admin = getIntent().getStringExtra("admin");
        valuesInfo = getIntent().getStringExtra("valueeInfo");
        cr = getIntent().getStringExtra("cr");

        setTEXTEDits = findViewById(R.id.setTEXTEDit);
        setTEXTEDits.setText("UPDATE "+valuesInfo);
        //        CRADDADMINADD ADDEMAILREGISTERSTUDENT

        if(valuesInfo == "CRADDADMINADD"){
            selectrule = findViewById(R.id.Select_RuleEdit);
            selectrule.setVisibility(View.VISIBLE);
            if(cr == "CR" && admin == "ADMIN"){
                selectrule.setText("ADMIN_OR_CR");
            }else if(cr == "NO"){
                selectrule.setText("ADMIN");
            }else {
                selectrule.setText("CR");
            }
            selectRulee();
        }
        nameeeEdits = findViewById(R.id.nameeeEdit);
        nameeeEdits.setText(name);
        EmailsEdits = findViewById(R.id.EmailsEdit);
        EmailsEdits.setText(email);
        phnNumberEdits = findViewById(R.id.phnNumberEdit);


        updatee = findViewById(R.id.UploadControllingEdit);
        updatee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDATAINPUT();
            }
        });

    }

    private String Nname = "" , emailss = "" , Mobile = "";
    private void validateDATAINPUT() {
        Nname = nameeeEdits.getText().toString().trim();
        emailss = EmailsEdits.getText().toString().trim();
        Mobile = phnNumberEdits.getText().toString().trim();

        if(TextUtils.isEmpty(Nname)){
            Toast.makeText(EditInformation.this, "Set Name" , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(emailss)){
            Toast.makeText(EditInformation.this, "Set Email" , Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(Mobile) && (Mobile.length() == 11 || (Mobile.length()==14 && Mobile.startsWith("+88")))){

            Toast.makeText(EditInformation.this, "Set Mobile number  Correctly" , Toast.LENGTH_SHORT).show();

        }else if(valuesInfo.equals("CRADDADMINADD") && Rule == null){
            Toast.makeText(EditInformation.this, "Set Rule" , Toast.LENGTH_SHORT).show();

        }else{
            updateDATABASE();
        }
    }



    private void updateDATABASE() {
        progressDialog.setMessage("Uploading Information....");
        progressDialog.show();
        String tp = "";
        HashMap<String , Object> hashMap = new HashMap<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AdminAddingSection");
        long time = System.currentTimeMillis();
        if(valuesInfo.equals("CRADDADMINADD")){
            if(Rule == "ADMIN"){
                Rule = "ADMIN";
                hashMap.put("ADMIN" ,"ADMIN" );
                hashMap.put("CR" , "NO");

            }else if(Rule == "CR"){
                Rule = "CR";
                hashMap.put("ADMIN" ,"NO");
                hashMap.put("CR" , "CR");
            }else if(Rule == "ADMIN_OR_CR"){
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

        }
        String safeEmail = email.replace(".", ",");


        //UPDATE children  users er usertype
        DatabaseReference referenceUser = FirebaseDatabase.getInstance().getReference("Users");
        referenceUser.child(safeEmail)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    HashMap<String, Object> updateHashMap = new HashMap<>();
                                    updateHashMap.put("userType", Rule);

                                    referenceUser.child(safeEmail).updateChildren(updateHashMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(EditInformation.this , "Set User Type" + Rule , Toast.LENGTH_SHORT).show();

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(EditInformation.this , "Set User Type Failed" , Toast.LENGTH_SHORT).show();

                                                }
                                            });


                                }else{

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



        //update adding section
        ref.child(tp)
                .child(""+email.replace(".",","))
//                .child(timestamp)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(EditInformation.this , "Updating Succefully..." , Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(EditInformation.this , "Updating not Succefully..." , Toast.LENGTH_SHORT).show();

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