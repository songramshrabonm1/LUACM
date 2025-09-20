package com.example.myapplication.dashboardadmin.routinepart;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//cloudinary
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import com.bumptech.glide.Glide;
import com.cloudinary.utils.ObjectUtils;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UploadRoutineButtonAdmin extends AppCompatActivity  {

    ImageButton bacbtnnn;
    AppCompatButton uploadBtn;
    String Batch = "" , Section = "";
    TextView SelectBatch, PickImage , pickSection;
    Uri ImageUri = null ;
    ImageView image;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_routine_button_admin);

        getWindow().setStatusBarColor(Color.parseColor("#A6A7AC"));

//



        bacbtnnn = findViewById(R.id.backBtnnc);
        uploadBtn = findViewById(R.id.UploadRoutineImage);
        SelectBatch = findViewById(R.id.Batch);
        PickImage = findViewById(R.id.PickImage);
         progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        image = findViewById(R.id.UploadedimagessRoutine);
        pickSection = findViewById(R.id.sections);



        bacbtnnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //  (Documentation)-->    https://developer.android.com/training/data-storage/shared/photopicker#java
        // Registers a photo picker activity launcher in single-select mode.
        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {

                        ImageUri = uri;
                        Glide.with(UploadRoutineButtonAdmin.this)
                                        .load(ImageUri)
                                                .into(image);
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });



        SelectBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBatches();
            }
        });

        pickSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSection();
            }
        });

        PickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//  (Documentation)-->    https://developer.android.com/training/data-storage/shared/photopicker#java
// Launch the photo picker and let the user choose only images.
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatedData();
            }
        });

    }

    private void selectSection() {
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Section")
                .setItems(alphabet, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Section = alphabet[which];
                        pickSection.setText(Section);
                    }
                }).show();

    }


    private void selectBatches() {
        String [] Batches = new String[101];
        int ii = 0 ;
        for(int i = 50 ; i<=150; i++){
            Batches[ii] = ""+i;
            ii++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Batch")
                .setItems(Batches, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Batch = Batches[i];
                        SelectBatch.setText(Batches[i]);
                    }
                }).show();
    }

    private void validatedData() {
        if(TextUtils.isEmpty(Batch)){
            Toast.makeText(UploadRoutineButtonAdmin.this, "Pick Batch" , Toast.LENGTH_SHORT).show();
        }else if(ImageUri.equals(null)){
            Toast.makeText(UploadRoutineButtonAdmin.this, "Pick Routine Image" , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(Section)){
            Toast.makeText(UploadRoutineButtonAdmin.this , "Pick Section" ,Toast.LENGTH_SHORT).show();
        }
        else{
            UploadFirstRoutineImageStorage();
        }
    }

    private void UploadFirstRoutineImageStorage() {
//  প্রথমে check করবো এই ব্যাচ দিয়ে already child create করা হয়ে গেছে কি না তাই এখানে প্রথমে check করবো এই batch এর child আছে কিনা যদি না থাকে
//  সরাসরি Storage এ upload করে দিবো তারপর তার downloadUrl টা নিয়ে আসবো আর realtime database এ এই ব্যাচের একটি child খুলে তা মধ্যে আরেকটি child
//  থাকেবে url এর মধ্যে আপলোড করে দিবো ।
/*
আর যদি না থাকে তাহলে
 */
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("RoutineCheck");
        reference.child(Batch + "-"+ Section)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            boolean allreadyexist = true;
                            uploadImageCloudinaryImage(allreadyexist);
                        }else{
                            // ইমেজ ফাইল পাথ (যেমন: গ্যালারি থেকে নির্বাচন করা ইমেজ)
// Cloudinary-এ আপলোড করুন
                            boolean allreadyexist = false ;
                            uploadImageCloudinaryImage(allreadyexist);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void uploadImageCloudinaryImage(boolean allreadyexist) {
        MediaManager.get().upload(ImageUri)
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        // আপলোড শুরু হলে
                        Log.d("Cloudinary", "আপলোড শুরু হয়েছে");
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {
                        // আপলোডের প্রোগ্রেস দেখান
                        Log.d("Cloudinary", "আপলোড হচ্ছে: " + bytes + "/" + totalBytes);
                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        // আপলোড সফল হলে
                        Log.d("Cloudinary", "আপলোড সফল হয়েছে: " + resultData.get("url"));
                        String Url = (String) resultData.get("secure_url");

                        progressDialog.setMessage("Uploaded Routine....");
                        progressDialog.show();
                        if(allreadyexist)
                            updateDAtabaseUrl(Url);
                        else
                            uploadDatabase(Url);

                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        // আপলোড ব্যর্থ হলে
                        Log.e("Cloudinary", "আপলোড ব্যর্থ: " + error.getDescription());
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        // রিসিডিউল প্রয়োজন হলে
                        Log.e("Cloudinary", "রিসিডিউল প্রয়োজন: " + error.getDescription());
                    }
                }).dispatch();
    }

    private void updateDAtabaseUrl(String url) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Url" , url);
        hashMap.put("Batch" , ""+Batch);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("RoutineCheck");
//        reference.child(""+Batch)
        reference.child(Batch + "-"+ Section)
                .updateChildren(hashMap)
//                .updateChildren(url)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadRoutineButtonAdmin.this, "Successfully Updated" ,Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadRoutineButtonAdmin.this, "Successfully not Updated" ,Toast.LENGTH_SHORT).show();
                    }
                });
    }




//Batch Child Ta na thakle eta call dibo . prothome storage e upload pore realtime database e oi batch er child create kore rekhe diyechi tar under e
    private void uploadDatabase(String url) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Url" , url);
        hashMap.put("Batch" , ""+Batch);
        hashMap.put("Section" ,""+Section);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("RoutineCheck");
//        reference.child(""+Batch)
        reference.child(Batch + "-"+ Section)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadRoutineButtonAdmin.this, "Uploaded this url successfully", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadRoutineButtonAdmin.this, "Uploaded this url is not  successfully"+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }


}