package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Conceptual;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.videoSection.VideoSection;
import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.AlgoModuleClassesEdit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterConceptualModuleClasses extends RecyclerView.Adapter<AdapterConceptualModuleClasses.ViewHolder> {

    ArrayList<ConceptualModelClasses> moduleClassesCategory = new ArrayList<>();
    private Context context;
    private ProgressDialog progressDialog;
    private String UserType= "";

    public AdapterConceptualModuleClasses(ArrayList<ConceptualModelClasses> moduleClassesCategory, Context context , String UserType) {
        this.moduleClassesCategory = moduleClassesCategory;
        this.context = context;
        this.UserType = UserType;

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_class , parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConceptualModelClasses modelClasses = moduleClassesCategory.get(position);
        String id = modelClasses.getId();
        String driveLink = modelClasses.getDriveLink();
        String className = ""+modelClasses.getClasss();
        String ModuleId = modelClasses.getModuleId();
        long timeStamp = modelClasses.getTimestamp();

        System.out.println("Id: "+ id);
        System.out.println("DriveLink: "+ driveLink);
        System.out.println("ClassName: "+ className);
        System.out.println("ModuleId" + ModuleId);
        System.out.println("Timestamp"+ timeStamp);


        boolean find = false;
        if(className.contains(""+10)){
            className = ""+10;
            find = true ;

        }


        if(className.contains(""+11)){
            className = ""+11;
            find = true ;

        }



        if(className.contains(""+12)){
            className = ""+12;
            find = true ;

        }


        if(className.contains(""+13)){
            className = ""+13;
            find = true ;

        }
        if(className.contains(""+14)){
            className = ""+14;
            find = true ;

        }
        if(className.contains(""+15)){
            className = ""+15;
            find = true ;

        }
        if(className.contains(""+16)){
            className = ""+16;
            find = true ;

        }
        if(className.contains(""+17)){
            className = ""+17;
            find = true ;

        }
        if(className.contains(""+18)){
            className = ""+18;
            find = true ;
        } if(className.contains(""+19)){
            className = ""+19;
            find = true ;
        } if(className.contains(""+20)){
            className = ""+20;
            find = true ;
        }



        if(find == false) {
            for (int i = 0; i <= 9; i++) {
                if (className.contains("" + i)) {
                    System.out.println(i);
                    className = "" + i;
                    find = true ;
                    break;
                }
            }
        }
        holder.classss.setText("Class-"+ className);

        if(UserType.equals("ADMIN")) {
            holder.editBtn.setVisibility(View.VISIBLE);
            holder.deleteButtonClass.setVisibility(View.VISIBLE);

            holder.deleteButtonClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setIcon(R.drawable.baseline_delete_24);
                    builder.setTitle("Delete")
                            .setMessage("Are You Want To Delete The Module")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DeleteClasss(modelClasses, holder);
                                    Toast.makeText(context, "Deleting", Toast.LENGTH_SHORT).show();

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                }
            });

            holder.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ConceptualModuleClassesEdit.class);
                    intent.putExtra("driveLink", driveLink);
                    intent.putExtra("classNumber", modelClasses.getClasss());
                    intent.putExtra("id", id);
                    intent.putExtra("ModuleId", ModuleId);


                    context.startActivity(intent);

                }
            });
        }else{
            holder.deleteButtonClass.setVisibility(View.INVISIBLE);
            holder.editBtn.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoSection.class);
                intent.putExtra("driveLink", driveLink);
                context.startActivity(intent);
            }
        });



    }

    private void DeleteClasss(ConceptualModelClasses modelClasses, ViewHolder holder) {


        progressDialog.setMessage("Deleting The Class");
        progressDialog.show();

        String classId = modelClasses.getId();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Video");
        ref.child("Conceptual")
                .child(modelClasses.getModuleId())
                .child("Classes")
                .child(classId)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Successfully Delete the class" , Toast.LENGTH_SHORT).show();

                            }
                        });

                        int position = holder.getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION && position >=0){
                            moduleClassesCategory.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position ,moduleClassesCategory.size());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Failed Deleted" + e.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public int getItemCount() {
        return moduleClassesCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView classss ;
        ImageButton deleteButtonClass , editBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            classss = itemView.findViewById(R.id.classess);
            deleteButtonClass = itemView.findViewById(R.id.delete_icon);
            editBtn = itemView.findViewById(R.id.editIcon);


        }

    }

}
