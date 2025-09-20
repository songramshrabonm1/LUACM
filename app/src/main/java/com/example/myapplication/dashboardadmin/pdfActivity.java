package com.example.myapplication.dashboardadmin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class pdfActivity extends AppCompatActivity {
//    private TextView BookCategory;
    private EditText descriptioNEdittext ,sessionYear, BookCategory, SessionName , SemesterExamination;
    private EditText BookTitle;
    private ImageButton attachButton, backButton;
    private AppCompatButton submit;
    private static final int PDF_PiCK_CODE = 1000;

    //আমরা যখণ internal storage থেকে pdf pick করবো তখন একটা uri link generate হবে
    private Uri pdfUri = null;

    // ক্যাটেগরির জন্য অ্যারে লিস্ট
    private ArrayList<ModelCategory> categoryArrayList ;
    private  ArrayList<String> categoryTitleArrayList , categoryIdArrayList;

    // FirebaseAuth এবং ProgressDialog এর অবজেক্ট
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);// XML লেআউট সেট করা হচ্ছে

        //******************************* XML ফাইল থেকে UI ইনিশিয়ালাইজ*******************************
        descriptioNEdittext = findViewById(R.id.description_Et);//Batch
        BookTitle = findViewById(R.id.book_Et); // Teacher Name
        //******************************* XML ফাইল থেকে UI ইনিশিয়ালাইজ*******************************


        // Firebase এর অবজেক্ট তৈরি
        firebaseAuth = FirebaseAuth.getInstance();
        // ক্যাটেগরি লোড করা হচ্ছে (১)
        loadPdfcategory();


//        *********************PROGRESS DIALOGUE OBJECT CREATE START************************
        progressDialog = new ProgressDialog(this);// প্রগ্রেস ডায়ালগ তৈরি
        progressDialog.setTitle("Please Wait....");// প্রগ্রেস ডায়ালগে শিরোনাম
        progressDialog.setCanceledOnTouchOutside(false);// টাচ করলে ডায়ালগ বন্ধ হবে না
//      *********************PROGRESS DIALOGUE OBJECT CREATE END************************






//        ******************************BUTTON INITIALIZE START***************************
        sessionYear = findViewById(R.id.years);
        submit = findViewById(R.id.submit_btn);
        backButton = findViewById(R.id.back_btnn);
        attachButton = findViewById(R.id.attach_Btn);
        BookCategory = findViewById(R.id.BookCategory);//eitai question er part
        SessionName = findViewById(R.id.FallSummer);
        SemesterExamination = findViewById(R.id.FinalORMid);
        // ব্যাক বাটনে ক্লিক ইভেন্ট
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();// ব্যাক বাটনে ক্লিক করলে আগের Activity তে ফিরে যাবে
//                pdfActivity.super.getOnBackPressedDispatcher();
//                startActivity(new Intent(pdfActivity.this, DashboardAdmin.class));
//                finish();
//                onBackPressed();
                // Back button প্রেস করলে কি হবে তা নির্ধারণ করা
                getOnBackPressedDispatcher().onBackPressed();

            }
        });

//        OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();
//        dispatcher.addCallback(this, new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                Toast.makeText(pdfActivity.this, "Onbackpressed", Toast.LENGTH_SHORT).show();
//            }
//        });


        // ক্যাটেগরি সিলেক্ট করার ডায়ালগ(প্রথমে এটার কাজ করেছি) (২)
        BookCategory.setOnClickListener(new View.OnClickListener() { //eitai question er category
            @Override
            public void onClick(View v) {
                categoryPickDialog(); // ক্যাটেগরি পিক ডায়ালগ দেখানো হবে এই মেথডে (২)
            }
        });

        // সাবমিট বাটনে ক্লিক ইভেন্ট । এখানে ডাটা ফাইয়ারব্যাসে এবং স্টোরেজ এ  পাঠানো হবে (৪)
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();// ডেটা যাচাই করা হবে
            }
        });

        // পিডিএফ অ্যাটাচ বাটনে ক্লিক ইভেন্ট এখানে পিডিএফ সিলেক্ট করা হবে (৩)
        attachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfPickIntent();// পিডিএফ পিক করার ইন্টেন্ট 1st kaj
            }
        });

//        ******************************BUTTON INITIALIZE START***************************

    }
    //এই তিনটা variable এর মাঝে user যে তিনটা Data দিবে(২ টা edittext,1 টা textView)সেগুলো এই তিনটা variable এ রাখবো(ইউজারের ইনপুট ডেটা রাখার জন্য ভ্যারিয়েবল)
    private  String sessionYears = "", TeacherName = "", Batch = "", sessionName = "" , semesterExama = "" ;
    private String selectedCategoryId , selectedCategoryTitle ;

    // ইউজারের ইনপুট ডেটা যাচাই করা হচ্ছে (৪)
    private void validateData() {

        TeacherName = BookTitle.getText().toString().trim();
        Batch = descriptioNEdittext.getText().toString().trim();
        sessionName = SessionName.getText().toString().trim();
        semesterExama = SemesterExamination.getText().toString().trim();
        sessionYears = sessionYear.getText().toString().trim();

        if (TextUtils.isEmpty(TeacherName)) {

            Toast.makeText(this, "Enter Teacher Name...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Batch)) {
            Toast.makeText(this, "Enter Descripiton", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(sessionName)) {
            Toast.makeText(this, "Enter SessionName", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(semesterExama)){
            Toast.makeText(pdfActivity.this, "Enter Semester Exam Textfield" , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(sessionYears)){
            Toast.makeText(pdfActivity.this, "Enter Session Year" , Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(selectedCategoryTitle)){
            Toast.makeText(this, "Pick category", Toast.LENGTH_SHORT).show();
        } else if (pdfUri == null) {
            Toast.makeText(this, "Pick Pdf", Toast.LENGTH_SHORT).show();
        } else {
            uploadPdfToStorage();
        }
    }

    //// পিডিএফ Firebase Storage এ আপলোড করা হচ্ছে (৫)
    private void uploadPdfToStorage() {
        progressDialog.setMessage("Uploading Pdf.....");// ProgressDialog এ আপলোডের মেসেজ সেট করা
        progressDialog.show();// প্রগ্রেস ডায়ালগ দেখানো

        long timestamp = System.currentTimeMillis();// বর্তমান টাইমস্ট্যাম্প নেয়া
        String filePathAndName = "Books/" + timestamp;// স্টোরেজ পাথ তৈরি করা হচ্ছে

        // ফায়ারবেস স্টোরেজে রেফারেন্স তৈরি
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        // পিডিএফ ফাইল আপলোড করা হচ্ছে
        storageReference.putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();// আপলোডের URL নেয়া কারণ আমরা তা realtime databse এ upload করবো
                        while (!uriTask.isSuccessful()) ;// URL সফল হলে অপেক্ষা করা
                             String uploadedPdfUrl = "" + uriTask.getResult();// পিডিএফের URL সংরক্ষণ করেছি uploadPdfUrl এ
                             uploadPdfinfoToDb(uploadedPdfUrl, timestamp); //// Realtime ডাটাবেসে পিডিএফ তথ্য আপলোড করা
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(pdfActivity.this, "PDF Load failed..." + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // পিডিএফের তথ্য Firebase Realtime Database এ আপলোড করা হচ্ছে (৬)
    private void uploadPdfinfoToDb(String uploadedPdfUrl, long timestamp) {
        progressDialog.setMessage("Uplading Pdf info ....");// তথ্য আপলোডের মেসেজ
        //এটি Firebase Authentication থেকে ইউজারের ইউনিক আইডেন্টিফায়ার (UID) গ্রহণ করে। UID হল প্রতিটি ইউজারের জন্য আলাদা একটি চিহ্ন, যা ইউজারকে সনাক্ত করতে ব্যবহৃত হয়।
        String uid = FirebaseAuth.getInstance().getUid(); // যে admin pdf upload দিবে তার unique id FirebaseAuth থেকে collect করা হচ্ছে

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", "" + uid);
        hashMap.put("timestamp", timestamp);// আমার Timestamp টাকেই  ক্যাটাগরি আইডি হিসেবে ব্যবহার করব । পরবর্তীতে যে জায়গাগুলোতে bookid নিয়েছে সেখানে আমি timestamp কে ব্যবহার করেছি
        hashMap.put("TeacherName", "" + TeacherName);
        hashMap.put("Batch", "" + Batch);
        hashMap.put("SessionName" , ""+ sessionName);
        hashMap.put("SemesterExam", ""+semesterExama);
        hashMap.put("categoryId", "" + selectedCategoryId);
        hashMap.put("Year" , ""+sessionYears);
        hashMap.put("Category" , ""+ selectedCategoryTitle);
        hashMap.put("url", "" + uploadedPdfUrl); //pdf এর যে url আমরা Storage এ upload দেয়ার পর collect করেছি তা realtime database এ upload করে দিয়েছি ।

        // এটি Firebase Realtime Database-এ "Books" নামে একটি নোডের রেফারেন্স তৈরি করে। এই নোডের অধীনে সমস্ত বইয়ের তথ্য সংরক্ষণ করা হবে।
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        // এটি "Books" নোডের অধীনে একটি নতুন সন্তান তৈরি করে, যার নাম টাইমস্ট্যাম্প হবে। এই সন্তানের অধীনে hashMap এর সমস্ত তথ্য সংরক্ষণ করা হবে।
        ref.child("" + timestamp)
                // Firebase Database এ তথ্য আপলোড ।
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(pdfActivity.this, "Successfully Included...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(pdfActivity.this, "Failed to Upload to db due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // ক্যাটেগরি Firebase থেকে লোড করা হচ্ছে (1)
    private void loadPdfcategory() {
        categoryIdArrayList = new ArrayList<>();
        categoryTitleArrayList = new ArrayList<>();

//এটি একটি DatabaseReference তৈরি করে, যা Firebase Realtime Database এর "Category" নামক একটি নোডের রেফারেন্স। এটি ওই নোডের ডেটা অ্যাক্সেস এবং আপডেট করার জন্য ব্যবহৃত হবে।
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Category"); // ক্যাটেগরি রেফারেন্স
//এটি addValueEventListener মেথড ব্যবহার করে একটি ValueEventListener যোগ করে। এই লিসেনারটি নোডের ডেটা পরিবর্তনের জন্য নজরদারি করে। অর্থাৎ, যখনই "Category" নোডের ডেটা পরিবর্তিত হবে, তখন onDataChange মেথডটি স্বয়ংক্রিয়ভাবে কল হবে।
        ref.addValueEventListener(new ValueEventListener() {// ডেটা পরিবর্তনের জন্য ইভেন্ট লিসেনার যোগ করা
            @Override
            //এই মেথডটি তখন কল হয় যখন ডেটা সফলভাবে লোড হয় বা পরিবর্তিত হয়। DataSnapshot snapshot এখানে বর্তমান ডেটা ফেচ করার জন্য ব্যবহৃত হয়।
            public void onDataChange(@NonNull DataSnapshot snapshot) {// ডেটা পরিবর্তিত হলে
//                categoryArrayList.clear();// পুরনো ক্যাটেগরি ক্লিয়ার
                categoryIdArrayList.clear();
                categoryTitleArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {// প্রতিটি ক্যাটেগরির জন্য লুপ

                    String categoryId = ""+ds.child("id").getValue();
                    String categoryTitle = ""+ds.child("category").getValue();
                    categoryTitleArrayList.add(categoryTitle);
                    categoryIdArrayList.add(categoryId);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {// ডেটা লোড ব্যর্থ হলে কিছু করার নেই । ব্যর্থ হলে এই মেথড লোড হবে ।
            }
        });
    }

//    private String selectedCategoryId , selectedCategoryTitle ;


    // ক্যাটেগরি পিক করার জন্য ডায়ালগ তৈরি (২)
    private void categoryPickDialog() {
        String[] categoriesArray = new String[categoryTitleArrayList.size()];// ক্যাটেগরি অ্যারে তৈরি তার মাঝে সবগুলো ক্যাটাগরি গুলো সেইভ করে রাখড়বো
        for (int i = 0; i < categoryTitleArrayList.size(); i++) {// ক্যাটেগরি লিস্টে লুপ চালাচ্ছি কারণ categoryArraylist এ যে ডাটা গুলো আছে সেগুলো categoryArray তে রাখছি
//            categoriesArray[i] = categoryArrayList.get(i).getCategory();
            categoriesArray[i] = categoryTitleArrayList.get(i);
        }
        //এটি একটি AlertDialog.Builder অবজেক্ট তৈরি করে। this নির্দেশ করে যে এটি বর্তমান অ্যাক্টিভিটি থেকে ডায়ালগটি তৈরি হচ্ছে।
        AlertDialog.Builder builder = new AlertDialog.Builder(this);// ডায়ালগ বিল্ডার তৈরি
//এই লাইনটি একটি তালিকা (list) তৈরি করে যা categoriesArray থেকে ক্যাটেগরি দেখাবে। যখন ইউজার একটি ক্যাটেগরি সিলেক্ট করবে, তখন এটি OnClickListener ইন্টারফেসের onClick মেথড কল করবে।
        builder.setTitle("Pick category")// ডায়ালগের শিরোনাম
                .setItems(categoriesArray, new DialogInterface.OnClickListener() {// ক্যাটেগরি তালিকা দেখান
                    @Override
                    public void onClick(DialogInterface dialog, int which) {  // ইউজার ক্যাটেগরি সিলেক্ট করলে
//                        String category = categoriesArray[which];//user যে category select করেছে তা এই variable এর মাঝে আছে ।
//                        BookCategory.setText(category); // সিলেক্ট করা ক্যাটেগরি TextView তে দেখানো বা সেট করেছি
                        selectedCategoryTitle = categoryTitleArrayList.get(which);
                        selectedCategoryId = categoryIdArrayList.get(which);
                        BookCategory.setText(selectedCategoryTitle); //question er name select hobe
                    }
                })
                .show();//এটি ডায়ালগটি দেখানোর জন্য ব্যবহার করা হয়। এই লাইনে সবকিছু সেট আপ করার পর ডায়ালগটি স্ক্রীনে প্রদর্শিত হয়।
    }

    // পিডিএফ পিক করার জন্য ইন্টেন্ট
    private void pdfPickIntent() { //(৩)
        Intent intent = new Intent();// নতুন ইন্টেন্ট তৈরি
        intent.setType("application/pdf");// পিডিএফ টাইপ সেট করা
        intent.setAction(Intent.ACTION_GET_CONTENT);// কন্টেন্ট পিক করার অ্যাকশন
        startActivityForResult(Intent.createChooser(intent, "selected Pdf"), PDF_PiCK_CODE);// পিডিএফ পিক করার জন্য স্টার্ট করা
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //আমরা যে pdf টা pick করেছি সেই pdf এর ডাটা টা এখানে চলে আসছে  Intent Type  এর DATA variable এর ভিতরে
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PDF_PiCK_CODE) {
//data variable এ pdf এর DATA চলে এসেছে তাই আমরা এখান থেকে .getData() করতে পেরেছি আড় pdfUri তে set করে দিয়েছি ।
                pdfUri = data.getData();
            }
        } else {
            Toast.makeText(pdfActivity.this, "Cancelled parking PDF", Toast.LENGTH_SHORT).show();
        }
    }
}