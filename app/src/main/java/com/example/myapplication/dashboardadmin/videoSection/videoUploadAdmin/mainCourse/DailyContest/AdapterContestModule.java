package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.DailyContest;



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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.DailyContest.AdapterContestModule;
//import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.DailyContest.AdapterAlgoModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterContestModule extends RecyclerView.Adapter<AdapterContestModule.ViewHolder> implements Filterable {

    private Context context;
    public ArrayList<ModelDailyContestModule> categoryModule , filterList;
    private  ProgressDialog progressDialog ;
    public com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.DailyContest.FilterDailyModule filter;
    private String UserType= "";
    public AdapterContestModule(Context context, ArrayList<ModelDailyContestModule> categoryModule, String UserType) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_daily_contest,parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelDailyContestModule modelDailyContestModule = categoryModule.get(position);
        holder.ContestName.setText(modelDailyContestModule.getContestName());
        holder.PlatformName.setText(modelDailyContestModule.getSelectContestSite());
        holder.DeivisionName.setText(modelDailyContestModule.getDivision());
//        holder.totalVideo.setText(modelDailyContestModule.getTotalVideo());
        holder.InstructorNames.setText(modelDailyContestModule.getInstructorName());
        String Url = modelDailyContestModule.getValue();


        String totalVideo = modelDailyContestModule.getTotalVideo();

        boolean find = false;

        if(find == false) {
            if (totalVideo.toString().toUpperCase().contains("20")) {
                holder.totalVideo.setText("Total Video - " + modelDailyContestModule.getTotalVideo());
                find = true;
            }
        }
        if(find == false) {
            if (totalVideo.toString().toUpperCase().contains("19")) {
                holder.totalVideo.setText("Total Video - " + modelDailyContestModule.getTotalVideo());
                find = true;
            }
        }
        if(find == false) {
            if (totalVideo.toString().toUpperCase().contains("18")) {
                holder.totalVideo.setText("Total Video - " + modelDailyContestModule.getTotalVideo());
                find = true;
            }
        }
        if(find == false) {
            if (totalVideo.toString().toUpperCase().contains("17")) {
                holder.totalVideo.setText("Total Video - " + modelDailyContestModule.getTotalVideo());
                find = true;
            }
        }
        if(find == false) {
            if (totalVideo.toString().toUpperCase().contains("16")) {
                holder.totalVideo.setText("Total Video - " + modelDailyContestModule.getTotalVideo());
                find = true;
            }
        }
        if(find == false) {
            if (totalVideo.toString().toUpperCase().contains("15")) {
                holder.totalVideo.setText("Total Video - " + modelDailyContestModule.getTotalVideo());
                find = true;
            }
        }
        if(find == false) {
            if (totalVideo.toString().toUpperCase().contains("14")) {
                holder.totalVideo.setText("Total Video - " + modelDailyContestModule.getTotalVideo());
                find = true;
            }
        }
        if(find == false) {
            if (totalVideo.toString().toUpperCase().contains("13")) {
                holder.totalVideo.setText("Total Video - " + modelDailyContestModule.getTotalVideo());
                find = true;
            }
        }
        if(find == false) {
            if (totalVideo.toString().toUpperCase().contains("12")) {
                holder.totalVideo.setText("Total Video - " + modelDailyContestModule.getTotalVideo());
                find = true;
            }
        }
        if(find == false) {
            if (totalVideo.toString().toUpperCase().contains("11")) {
                holder.totalVideo.setText("Total Video - " + modelDailyContestModule.getTotalVideo());
                find = true;
            }
        }

        if(find == false) {
            for (int i = 1; i <= 10; i++) {
                if(totalVideo.toString().toUpperCase().contains(""+i)){
                    holder.totalVideo.setText("Total Video - "+ modelDailyContestModule.getTotalVideo());
                    break;
                }
            }
        }

        Glide.with(context)
                .load(Url) // Load the image URL
                .into(holder.imageView); // Display in the ImageView




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,DailycontestModuleClass.class );
                intent.putExtra("ContestName" , modelDailyContestModule.getContestName() );
                intent.putExtra("PlatformName", modelDailyContestModule.getSelectContestSite());
                intent.putExtra("DeivisionName" ,modelDailyContestModule.getDivision() );
                intent.putExtra("totalVideo" , modelDailyContestModule.getTotalVideo());
                intent.putExtra("InstructorNames" , modelDailyContestModule.getInstructorName());
                intent.putExtra("id" , modelDailyContestModule.getId());
                intent.putExtra("uid" , modelDailyContestModule.getUid());
                intent.putExtra("UserType", UserType);
                context.startActivity(intent);

                Toast.makeText(context, "Touches item view ",Toast.LENGTH_SHORT).show();
            }
        });

        if(UserType.equals("ADMIN")) {
            holder.edit.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProblemSolvingUploadCategoryEdit.class);
                    intent.putExtra("ContestName", modelDailyContestModule.getContestName());
                    intent.putExtra("PlatformName", modelDailyContestModule.getSelectContestSite());
                    intent.putExtra("DeivisionName", modelDailyContestModule.getDivision());
                    intent.putExtra("totalVideo", modelDailyContestModule.getTotalVideo());
                    intent.putExtra("InstructorNames", modelDailyContestModule.getInstructorName());
                    intent.putExtra("id", modelDailyContestModule.getId());
                    intent.putExtra("uid", modelDailyContestModule.getUid());

                    context.startActivity(intent);
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setIcon(R.drawable.baseline_delete_24);
                    builder.setTitle("Delete")
                            .setMessage("Are You Want to Delete?")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DeleteModelContest(modelDailyContestModule, holder);
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
    }

    private void DeleteModelContest(ModelDailyContestModule modelDailyContestModule, ViewHolder holder) {
        progressDialog.setMessage("Deleting the Contest");
        progressDialog.show();

        String id = modelDailyContestModule.getId();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("DailyContest")
                .child(id)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Deleted the Module" , Toast.LENGTH_SHORT).show();
                            }
                        });

                        int position = holder.getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION && position >= 0){
                            categoryModule.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, categoryModule.size());
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
            filter = new FilterDailyModule(filterList,AdapterContestModule.this);

        }
        return filter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView ContestName , PlatformName , InstructorNames , DeivisionName , totalVideo ;
        ImageButton delete, edit;
        ImageView imageView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ProbleSolvingPlatformImage);
            ContestName = itemView.findViewById(R.id.constestname);
            PlatformName = itemView.findViewById(R.id.PlatformName);
            InstructorNames = itemView.findViewById(R.id.InstructorNames);
            DeivisionName = itemView.findViewById(R.id.DeivisionName);
            totalVideo = itemView.findViewById(R.id.totalVideo);
            delete = itemView.findViewById(R.id.deleteIcon);
            edit = itemView.findViewById(R.id.editIcon);
        }
    }
}
