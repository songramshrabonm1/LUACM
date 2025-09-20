package com.example.myapplication.dashboardadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;

public class PdfEditActivity extends AppCompatActivity {

    String bookId;
    ProgressDialog progressDialog ;
    ImageButton backBtn;
    Button UpdateBtn ;
    EditText questionCategory  , courseTeacherName , Batch , SemesterExamination , session, Year  ;
    private ArrayList<String> categoryTitleArrayList , categoryIdArrayList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_edit);

        //        ***************INTENT ER THEKE BOOKID TA INTENT KORE NICHI*****************
        bookId = getIntent().getStringExtra("bookId");
        //        ***************INTENT ER THEKE BOOKID TA INTENT KORE NICHI*****************

        Log.d("PdfEditActivity", "bookId: " + bookId); // Debugging

        if (bookId == null) {
            Toast.makeText(this, "Error: No book ID provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadCategory();//এইটা দরকার হয় আমরা alertDialog এর মধ্যে যে ক্যাটাগরিগুলো দেখতে পরি তার জন্য আমরা প্রথম ডেটাবেসের এর ক্যাটাগরি নোড থেকে ক্যাটাগরি গুলো লোড করে নিয়ে আসছি তারপর alertDialgo এ সেট করে দিব।
        loadBookinfo();
        courseTeacherName = findViewById(R.id.book_Et);
        Batch = findViewById(R.id.description_Et);
        SemesterExamination = findViewById(R.id.FinalORMid);
        session =findViewById(R.id.FallSummer);
        Year = findViewById(R.id.years);

        //***************PROGRESSDIALOG PART START******************
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        //***************PROGRESSDIALOG PART START******************

        //***********************QUESTION CATEGORY PART START******************
        questionCategory = findViewById(R.id.BookCategory);
        questionCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryDialog();
            }
        });
        //***********************QUESTION CATEGORY PART END******************




        // ******************************* BACKBUTTON PART START *******************************
         backBtn = findViewById(R.id.back_btnn);
         backBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onBackPressed();
             }
         });
        // ******************************* BACKBUTTON PART END *******************************


        //        ************************************UPDATE BUTTON PART START****************************************
        UpdateBtn = findViewById(R.id.update_pdfBTN);
        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
        //        ************************************UPDATE BUTTON PART END****************************************
    }

    private String selectCategoryId = "", selectCAtegoryTitle = "";


    private void loadBookinfo() {

        if (bookId == null) {
            Toast.makeText(this, "Error: Book ID is null", Toast.LENGTH_SHORT).show();
            return;
        }


        DatabaseReference refBook = FirebaseDatabase.getInstance().getReference("Books");
        refBook.child(bookId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String category = ""+snapshot.child("Category").getValue();
                        String CourseTeacher = ""+snapshot.child("TeacherName").getValue();
                        String BatchString = ""+snapshot.child("Batch").getValue();
                        String SemesterExam = ""+snapshot.child("SemesterExam").getValue();
                        String sessionName = ""+snapshot.child("SessionName").getValue();
                        String Yearr = ""+snapshot.child("Year").getValue();

                        courseTeacherName.setText(CourseTeacher);
                        Batch.setText(BatchString);
                        SemesterExamination.setText(SemesterExam);
                        session.setText(sessionName);
                        Year.setText(Yearr);
                        questionCategory.setText(category);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    // ********************************************** This is The UPDATE PART START **********************************************
    private String UpdatecrsTeacherName = "", UpdateBatch = "" , updateSemesterExamination = "" , updateSessionName = "" , UpdateYear = "";
    private void validateData() {

        UpdatecrsTeacherName = courseTeacherName.getText().toString().trim();
        UpdateBatch = Batch.getText().toString().trim();
        updateSemesterExamination = SemesterExamination.getText().toString().trim();
        updateSessionName = session.getText().toString().trim();
        UpdateYear = Year.getText().toString().trim();

        if(TextUtils.isEmpty(UpdatecrsTeacherName)){
            Toast.makeText(PdfEditActivity.this, "Update Course Teacher Name Empty",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(UpdateBatch)){
            Toast.makeText(PdfEditActivity.this, "Update Batch Empty", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(updateSemesterExamination)){
            Toast.makeText(PdfEditActivity.this, "Update semester Exam Empty ",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(updateSessionName)){
            Toast.makeText(PdfEditActivity.this, "Update Session", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(UpdateYear)){
            Toast.makeText(PdfEditActivity.this, "Update Year", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(selectCAtegoryTitle)){
            Toast.makeText(PdfEditActivity.this,"Pick Category" ,Toast.LENGTH_SHORT).show();
        }
        else{
            updatePdf();
        }
    }
    private void updatePdf() {
        progressDialog.setMessage("Updating Question Pdf");
        progressDialog.show();

        HashMap<String, Object> hashMap = new HashMap<>();
        //যেগুলো আপডেট করব সেগুলো এখানে নিব ।

        hashMap.put("TeacherName", "" + UpdatecrsTeacherName);
        hashMap.put("Batch", "" + UpdateBatch);
        hashMap.put("SemesterExam", ""+updateSemesterExamination);
        hashMap.put("SessionName" , ""+ updateSessionName);
        hashMap.put("categoryId", "" + selectCategoryId);
        hashMap.put("Year" , ""+UpdateYear);
        hashMap.put("Category" , ""+ selectCAtegoryTitle);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child(bookId)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(PdfEditActivity.this, "Book Info Update...", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(PdfEditActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    // ********************************************** This is The UPDATE PART END **********************************************





    //***********************************************QUESTION ALERTDIALOG BUILDER SET QUESTION START**************************************************

    private void loadCategory() {
        System.out.println("This is a loadCategory Method....");

        categoryIdArrayList = new ArrayList<>();
        categoryTitleArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Category");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryIdArrayList.clear();
                categoryTitleArrayList.clear();

                for(DataSnapshot ds : snapshot.getChildren()){
                    String categoryId = ""+ds.child("id").getValue();
                    String categoryTitle = ""+ds.child("category").getValue();

                    categoryTitleArrayList.add(categoryTitle);
                    categoryIdArrayList.add(categoryId);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void categoryDialog(){
        System.out.println("This is a CategoryDialog Method....");

        String[] categoriesArrays = new String[categoryTitleArrayList.size()];
        for(int i= 0 ; i<categoryTitleArrayList.size() ; i++){
            categoriesArrays[i] = categoryTitleArrayList.get(i);
            System.out.println("categoriesArrays : " + categoriesArrays[i]);

        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Category")
                .setItems(categoriesArrays, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectCAtegoryTitle = categoryTitleArrayList.get(which);
                        selectCategoryId = categoryIdArrayList.get(which);
                        questionCategory.setText(selectCAtegoryTitle);
                    }
                }).show();
    }
    //***********************************************QUESTION ALERTDIALOG BUILDER SET QUESTION START**************************************************

}