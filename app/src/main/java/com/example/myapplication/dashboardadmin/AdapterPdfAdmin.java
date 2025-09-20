package com.example.myapplication.dashboardadmin;

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

public class AdapterPdfAdmin extends RecyclerView.Adapter<AdapterPdfAdmin.HolderPdfAdmin> implements Filterable {
    private Context context;
    public ArrayList<ModelPdf> pdfArrayList, filterList;
    private FilterPdfAdmin filter;
    File tempFile;
    private ProgressDialog progressDialog;
    private String UserType = "";


    public AdapterPdfAdmin(Context context, ArrayList<ModelPdf> pdfArrayList , String UserType) {
        this.context = context;
        this.pdfArrayList = pdfArrayList;
        this.filterList = pdfArrayList;
        this.UserType = UserType;


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @NonNull
    @Override
    public AdapterPdfAdmin.HolderPdfAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pdf_admin, parent, false);
        HolderPdfAdmin vh = new HolderPdfAdmin(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPdfAdmin holder, int position) {
        ModelPdf model = pdfArrayList.get(position);

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
//        loadPdfFromUrl(model, holder);

        holder.moreBtn.setVisibility(View.INVISIBLE);
        if(UserType.equals("ADMIN") || UserType.equals("CR") || UserType.equals("ADMIN_OR_CR")) {
            holder.moreBtn.setVisibility(View.VISIBLE);
            holder.moreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moreOptionsDialog(model, holder);
                }
            });
        }
    }

    private void moreOptionsDialog(ModelPdf model, HolderPdfAdmin holder) {
        String[] options = {"Edit", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Chose Options")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            String bookId = ""+model.getTimestamp();
                            System.out.println("bookId: "+bookId);
                            String BookUrl = model.getUrl();
                            System.out.println("BookUrl: "+BookUrl);
                            String BookTitle = model.getSemesterExam();
                            Intent intent = new Intent(context,PdfEditActivity.class);
                            intent.putExtra("bookId", bookId);
                            context.startActivity(intent);


                        }else if(which == 1){
                            deleteBook(model, holder);
                        }
                    }
                }).show();
    }
//    ****************************** DELETE QUESTION METHOD START *******************************
    private void deleteBook(ModelPdf model, HolderPdfAdmin holder) {
        String bookId = ""+model.getTimestamp();
        String BookUrl = model.getUrl();
        System.out.println("BookUrl: "+BookUrl);
        String semesterExam = model.getSemesterExam();
        String Batch = model.getBatch();
        String Year = model.getYear();
        String Session = model.getSessionName();

        progressDialog.setMessage("Deleting "+ Session + Year +Batch + "Batch Question" );
        progressDialog.show();

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BookUrl);
        storageReference.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Books");
                        reference.child(bookId)
                                .removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Question Deleted successfully" ,Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(context, ""+e.getMessage() , Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }
    //    ****************************** DELETE QUESTION METHOD START ******************************



    //******************************************* LOAD PDF URL *******************************************
    private void loadPdfFromUrl(ModelPdf model, HolderPdfAdmin holder) {
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
            filter = new FilterPdfAdmin(filterList, this);
        }
        return filter;
    }
    class HolderPdfAdmin extends RecyclerView.ViewHolder {
        PDFView pdfView;
        ProgressBar progressBar;
//        TextView titleTv, descriptionTv, categoryTv, sizeTv, dateTv;
        TextView semesterExam, Ssession, courseTeacherName, Batch, showPdf;
        ImageButton moreBtn;
        public HolderPdfAdmin(@NonNull View itemView) {

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
