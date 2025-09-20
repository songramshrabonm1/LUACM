package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.AdapterAlgoModule;
//import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.CppProgrammingUploadCategoryEdit;
//import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.CppproggramingModuleClass;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.ModelAlgoModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterAlgoModule extends RecyclerView.Adapter<AdapterAlgoModule.ViewHolder> implements Filterable {


    private Context context;
    public ArrayList<ModelAlgoModule> categoryModule , filterList;
    private ProgressDialog progressDialog;
    private  com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.FilterAlgoModule filter ;
    private String UserType= "";


    public AdapterAlgoModule(Context context, ArrayList<ModelAlgoModule> categoryModule , String UserType) {
        this.context = context;
        this.categoryModule = categoryModule;
        this.filterList = categoryModule;
        this.UserType = UserType;

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Module Deleted Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video_day_admin,parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModelAlgoModule  modelCppModule = categoryModule.get(position);
        String instructorName = modelCppModule.getInstructorName();
        String moduleNumber = modelCppModule.getModule();
        String VideoTitle = modelCppModule.getVideoTitle();
        String TotalVideoCnt = ""+modelCppModule.getTotalVideo();
        String id = modelCppModule.getId();
        String uid = modelCppModule.getUid();
        long timestamp = modelCppModule.getTimeStamp();


        boolean find = false;

        if(TotalVideoCnt.contains(""+10)){
            TotalVideoCnt = ""+10;
            find = true ;
        }
        if(TotalVideoCnt.contains(""+11)){
            TotalVideoCnt = ""+11;
            find = true ;
        }
        if(TotalVideoCnt.contains(""+12)){
            TotalVideoCnt = ""+12;
            find = true ;
        }
        if(TotalVideoCnt.contains(""+13)){
            TotalVideoCnt = ""+13;
            find = true ;
        }
        if(TotalVideoCnt.contains(""+14) ){
            TotalVideoCnt = ""+14;
            find = true ;
        }
        if(TotalVideoCnt.contains(""+15) ){
            TotalVideoCnt = ""+15;
            find = true ;
        }
        if(TotalVideoCnt.contains(""+16) ){
            TotalVideoCnt = ""+16;
            find = true ;
        }
        if(TotalVideoCnt.contains(""+17) ){
            TotalVideoCnt = ""+17;
            find = true ;
        }
        if(TotalVideoCnt.contains(""+18) ){
            TotalVideoCnt = ""+18;
            find = true ;
        }
        if(TotalVideoCnt.contains(""+19) ){
            TotalVideoCnt = ""+19;
            find = true ;
        }
        if(TotalVideoCnt.contains(""+20) ){
            TotalVideoCnt = ""+20;
            find = true ;
        }

        if(find == false) {
            for (int i = 0; i <= 9; i++) {
                if (TotalVideoCnt.contains("" + i)) {
                    System.out.println(i);
                    TotalVideoCnt = "" + i;
                    break;
                }
            }
        }


        holder.InstructorName.setText(instructorName);
        holder.ModuleNumber.setText(moduleNumber);
        holder.TitleName.setText(VideoTitle);
        holder.TotalVideoCount.setText("Today Totla Video - "+ TotalVideoCnt);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, AlgoModuleClass.class);
                intent.putExtra("id", id); // Data pass kortesi
                intent.putExtra("module", moduleNumber);
                intent.putExtra("VideoTitle",VideoTitle);
                intent.putExtra("instructorName",instructorName);
                intent.putExtra("UserType",UserType);

                context.startActivity(intent); // Corrected line
//                Toast.makeText(context, "Hello I am Item ", Toast.LENGTH_SHORT).show();
            }
        });

        if(UserType.equals("ADMIN")) {
            holder.edit.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setIcon(R.drawable.baseline_delete_24);
                    builder.setTitle("Delete")
                            .setMessage("Are You Want To Delete The Module")
                            .setPositiveButton("Confirmed", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DeleteModule(modelCppModule, holder);
                                    Toast.makeText(context, "Deleting....", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                }
            });

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, AlgoUploadCategoryEdit.class);
                    intent.putExtra("id", id); // Data pass kortesi
                    intent.putExtra("module", moduleNumber);
                    intent.putExtra("VideoTitle", VideoTitle);
                    intent.putExtra("instructorName", instructorName);
                    intent.putExtra("TotalVideoCount", "" + modelCppModule.getTotalVideo());

                    context.startActivity(intent);
//                Toast.makeText(context, "Hello I am the edit btn ", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            holder.delete.setVisibility(View.INVISIBLE);
            holder.edit.setVisibility(View.INVISIBLE);
        }
    }

    private void DeleteModule(ModelAlgoModule modelCppModule, AdapterAlgoModule.ViewHolder holder) {


        progressDialog.setMessage("Deleting The Module");
        progressDialog.show();

        String id = modelCppModule.getId();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("Algo")
                .child(id)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Deleted Item successfully" , Toast.LENGTH_SHORT).show();

                            }
                        });

                        int position = holder.getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION && position>=0){
                            categoryModule.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position , categoryModule.size());

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }


    @Override
    public int getItemCount() {
        return categoryModule.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new FilterAlgoModule(filterList, AdapterAlgoModule.this);
        }return filter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView ModuleNumber , TitleName , TotalVideoCount , InstructorName ;
        ImageButton delete, edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ModuleNumber = itemView.findViewById(R.id.ModuleText);
            TitleName = itemView.findViewById(R.id.TopicName);
            TotalVideoCount = itemView.findViewById(R.id.TodayTotalVideo);
            InstructorName = itemView.findViewById(R.id.insctructorName);
            delete = itemView.findViewById(R.id.deleteIcon);
            edit = itemView.findViewById(R.id.editIcon);
        }
    }
}
