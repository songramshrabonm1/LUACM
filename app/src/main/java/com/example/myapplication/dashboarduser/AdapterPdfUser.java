package com.example.myapplication.dashboarduser;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.ModelPdf;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.joanzapata.pdfview.PDFView;

import java.io.File;
import java.util.ArrayList;

public class AdapterPdfUser extends RecyclerView.Adapter<AdapterPdfUser.HolderPdfUser> implements Filterable {
    private Context context;
    public ArrayList<ModelPdfUser> pdfArrayList, filterList;
    private FilterPdfUser filter;
    File tempFile;
    private ProgressDialog progressDialog;



    public AdapterPdfUser(Context context, ArrayList<ModelPdfUser> pdfArrayList) {
        this.context = context;
        this.pdfArrayList = pdfArrayList;
        this.filterList = pdfArrayList;


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @NonNull
    @Override
    public AdapterPdfUser.HolderPdfUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pdf_user, parent, false);
        HolderPdfUser vh = new HolderPdfUser(view);
        return vh;
    }



    @Override
    public void onBindViewHolder(@NonNull HolderPdfUser holder, int position) {
        ModelPdfUser model = pdfArrayList.get(position);

        String semesterExam = model.getSemesterExam() ;
        String sessionName = model.getSessionName();
        String Year = model.getYear();
        String TeacherName = model.getTeacherName();
        String Batchh = model.getBatch();

        semesterExam = semesterExam.toUpperCase().trim();

        System.out.println("semsterExam : "+ semesterExam);


        if(semesterExam.toUpperCase().contains("FINALMID")){
            holder.semesterExam.setText("Semester Mid & Final Examination");
        }
        else if(semesterExam.toUpperCase().contains("MIDFINAL")){
            holder.semesterExam.setText("Semester Mid & Final Examination");
        }
        else if(semesterExam.toUpperCase().contains("FINAL AND MID")){
            holder.semesterExam.setText("Semester Mid & Final Examination");
        }
        else if(semesterExam.toUpperCase().contains("MID AND FINAL")){
            holder.semesterExam.setText("Semester Mid & Final Examination");
        }
        else if(semesterExam.toUpperCase().contains("MIDANDFINAL")){
            holder.semesterExam.setText("Semester Mid & Final Examination");
        }
        else if(semesterExam.toUpperCase().contains("FINALANDMID")){
            holder.semesterExam.setText("Semester Mid & Final Examination");
        }
        else if(semesterExam.toUpperCase().contains("MID&FINAL")){
            holder.semesterExam.setText("Semester Mid & Final Examination");
        }
        else if(semesterExam.toUpperCase().contains("FINAL&MID")){
            holder.semesterExam.setText("Semester Mid & Final Examination");
        }
        else if(semesterExam.toUpperCase().contains("MID")){
            holder.semesterExam.setText("Semester Mid Examination");
        }else if(semesterExam.toUpperCase().contains("FINAL")){
            holder.semesterExam.setText("Semster Final Examination");
        }
        else{
            holder.semesterExam.setText("Semester Mid & Final Examination");
        }


        sessionName = sessionName.toUpperCase().trim();
        System.out.println("SessionName : "+ sessionName);
        if(sessionName.toUpperCase().contains("FALL")){
            holder.Ssession.setText("Fall-"+""+Year);
//            sessionName = "Fall - ";
        }else if(sessionName.toUpperCase().contains("SPRING")){
            holder.Ssession.setText("Spring-"+""+Year);
//            sessionName = "Spring - ";
        }else{
            holder.Ssession.setText("Summer-"+""+Year);
//            sessionName = "Summer - ";
        }

        holder.courseTeacherName.setText(TeacherName);

        holder.Batch.setText("Batch - "+ Batchh);
        loadPdfFromUrl(model, holder);


        holder.showPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tempFile != null && tempFile.exists() && tempFile.length() > 0) {
//                                 // FileProvider এর মাধ্যমে tempFile এর জন্য একটি Uri তৈরি করা হচ্ছে
                    Uri pdfUri = FileProvider.getUriForFile(
                            context,
                            context.getApplicationContext().getPackageName() + ".fileprovider",
                            tempFile
                    );
                    Intent intent = new Intent(Intent.ACTION_VIEW, pdfUri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    try {
                        context.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(context, "No app found to open PDF", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "PDF file is not available to open", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    //******************************************* LOAD PDF URL *******************************************
    private void loadPdfFromUrl(ModelPdfUser model, HolderPdfUser holder) {
        String pdfUrl = model.getUrl();
        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl);

        holder.progressBar.setVisibility(View.VISIBLE);

        tempFile = new File(context.getExternalFilesDir(null), "fileName"+ ".pdf"); //tempFile = new File(context.getExternalFilesDir(null), "fileName" + ".pdf");
//        ডাউনলোড করা PDF ফাইলটি একটি অস্থায়ী ফাইল (tempFile) হিসেবে ফোনের অভ্যন্তরীণ স্টোরেজে সংরক্ষণ করা হবে, যেখানে ফাইলের নাম fileName.pdf।

// Download the PDF file
//ref.getFile(tempFile).addOnSuccessListener(taskSnapshot -> {
//ref (Firebase Storage রেফারেন্স) থেকে ফাইলটি tempFile-এ ডাউনলোড করার জন্য getFile মেথড ব্যবহার করা হচ্ছে। যদি ডাউনলোড সফল হয়, তাহলে addOnSuccessListener কলব্যাক ট্রিগার হবে।
        ref.getFile(tempFile).addOnSuccessListener(taskSnapshot -> {

            holder.progressBar.setVisibility(View.GONE);
            // Check if the file is valid before loading it in PDFView
            if (tempFile.exists() && tempFile.length() > 0) {
                holder.pdfView.fromFile(tempFile)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .defaultPage(1)
                        .load();
            } else {
                holder.progressBar.setVisibility(View.GONE);
                Toast.makeText(context, "Downloaded file is empty or corrupted", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(e -> {
            holder.progressBar.setVisibility(View.GONE);
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
    //******************************************* LOAD PDF URL *******************************************

    @Override
    public int getItemCount() {
        return pdfArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new FilterPdfUser(filterList, this);
        }
        return filter;
    }
    class HolderPdfUser extends RecyclerView.ViewHolder {
        PDFView pdfView;
        ProgressBar progressBar;
        //        TextView titleTv, descriptionTv, categoryTv, sizeTv, dateTv;
        TextView semesterExam, Ssession, courseTeacherName, Batch, showPdf;
        ImageButton moreBtn;
        public HolderPdfUser(@NonNull View itemView) {

            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
            pdfView = itemView.findViewById(R.id.pdfview);
            semesterExam = itemView.findViewById(R.id.SemesterExamination);
            Ssession = itemView.findViewById(R.id.Session);
            courseTeacherName = itemView.findViewById(R.id.CourseTeacherName);
            Batch = itemView.findViewById(R.id.Batch);
            showPdf = itemView.findViewById(R.id.showPdf);
            moreBtn = itemView.findViewById(R.id.more_Btn);
//            relativeLayout = itemView.findViewById(R.id.pdfRl);
        }
    }
}
