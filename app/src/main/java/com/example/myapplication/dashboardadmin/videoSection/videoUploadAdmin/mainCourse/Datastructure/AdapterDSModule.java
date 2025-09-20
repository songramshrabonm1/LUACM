package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Datastructure;

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
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.AdapterCppModule;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.CppProgrammingUploadCategoryEdit;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.CppproggramingModuleClass;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.FilterCppModule;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.CPlusProgramming.ModelCppModule;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Datastructure.ModelDsModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterDSModule extends RecyclerView.Adapter<AdapterDSModule.ViewHolder> implements Filterable {

    private Context context;
    public ArrayList<ModelDsModule> categoryModule , filterList;
    private ProgressDialog progressDialog;
    private com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Datastructure.FilterDsModule filter;
    private String UserType = "";

    public AdapterDSModule(Context context, ArrayList<ModelDsModule> categoryModule , String UserType) {
        this.context = context;
        this.categoryModule = categoryModule;
        this.filterList = categoryModule;
        this.progressDialog = progressDialog;
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

        ModelDsModule modelDsModule = categoryModule.get(position);
        String instructorName = modelDsModule.getInstructorName();
        String moduleNumber = modelDsModule.getModule();
        String VideoTitle = modelDsModule.getVideoTitle();
        String TotalVideoCnt = ""+modelDsModule.getTotalVideo();
        String id = modelDsModule.getId();
        String uid = modelDsModule.getUid();
        long timestamp = modelDsModule.getTimeStamp();


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

                Intent intent = new Intent(context, DsModuleClass.class);
                intent.putExtra("id", id); // Data pass kortesi
                intent.putExtra("module", moduleNumber);
                intent.putExtra("VideoTitle",VideoTitle);
                intent.putExtra("instructorName",instructorName);
                intent.putExtra("UserType",UserType);


                context.startActivity(intent); // Corrected line

                Toast.makeText(context, "Hello I am Item ", Toast.LENGTH_SHORT).show();

            }
        });


        if(UserType.equals("ADMIN")) {
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
                                    DeleteModule(modelDsModule, holder);
                                    Toast.makeText(context, "Deleting....", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();


                }
            });
        }else{
            holder.delete.setVisibility(View.INVISIBLE);
            holder.edit.setVisibility(View.INVISIBLE);
        }

//        if(UserType.equals("UserType")) {
        if(UserType.equals("ADMIN")){
            holder.edit.setVisibility(View.VISIBLE);
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, DsUploadCategoryEdit.class);
                    intent.putExtra("id", id); // Data pass kortesi
                    intent.putExtra("module", moduleNumber);
                    intent.putExtra("VideoTitle", VideoTitle);
                    intent.putExtra("instructorName", instructorName);
                    intent.putExtra("TotalVideoCount", "" + modelDsModule.getTotalVideo());

                    context.startActivity(intent);
//                Toast.makeText(context, "Hello I am the edit btn ", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            holder.edit.setVisibility(View.INVISIBLE);

        }

    }



    private void DeleteModule(ModelDsModule modelCppModule, AdapterDSModule.ViewHolder holder) {


        progressDialog.setMessage("Deleting The Module");
        progressDialog.show();

        String id = modelCppModule.getId();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("DataStructure")
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
            filter = new FilterDsModule(filterList, AdapterDSModule.this);

        }
        return filter;
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
