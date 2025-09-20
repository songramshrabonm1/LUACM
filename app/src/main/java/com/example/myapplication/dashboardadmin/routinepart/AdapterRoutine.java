package com.example.myapplication.dashboardadmin.routinepart;

import android.app.Activity;
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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterRoutine extends RecyclerView.Adapter<AdapterRoutine.ViewHolder>  implements Filterable {

    private Context context;
    ArrayList<ModelRoutine> categoryBatch , filterList ;
    ProgressDialog progressDialog ;
    FirebaseAuth firebaseAuth;
    String User = "";
    public com.example.myapplication.dashboardadmin.routinepart.FilterModelClass filter;


    public AdapterRoutine(Context context, ArrayList<ModelRoutine> categoryBatch , String User) {
        this.context = context;
        this.categoryBatch = categoryBatch;
        this.filterList = categoryBatch;
        this.User = User;

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading");
        progressDialog.setCanceledOnTouchOutside(false);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_batch_for_routine,parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelRoutine modelRoutine = categoryBatch.get(position);
        String Url = modelRoutine.getUrl();
        String Batch = ""+modelRoutine.getBatch();
        String Section = ""+modelRoutine.getSection();
        holder.batch.setText(Batch + " - "+ Section);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RoutineShow.class);
                intent.putExtra("Batch" ,Batch);
                intent.putExtra("Url" , Url);
                intent.putExtra("Section", Section);
                context.startActivity(intent);

            }
        });

//        if(User.equals())
//        if(User.equals("ADMIN")){
//
//        }

//        if(User.equals("USER")){
//        if(User.equals("ADMIN_OR_CR")) {
        if(User.equals("ADMIN_OR_CR") || User.equals("ADMIN") || User.equals("CR")){
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setIcon(R.drawable.baseline_delete_24);
                    builder.setTitle("Delete")
                            .setMessage("Are you sure you want to delete the category ")
                            .setPositiveButton("Confirmed", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DeleteBatch(modelRoutine, holder);
                                    Toast.makeText(context, "Deleting...", Toast.LENGTH_SHORT).show();
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

        }





    }

    private void DeleteBatch(ModelRoutine modelRoutine, ViewHolder holder) {
        progressDialog.setMessage("Deleting the Module");
        progressDialog.show();
//        reference.child(Batch + "-"+ Section)
        String Batch = ""+modelRoutine.getBatch();
        String Section = ""+modelRoutine.getSection();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("RoutineCheck");
        ref.child(Batch + "-"+ Section)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Delete Item  Successfully" , Toast.LENGTH_SHORT).show();
                            }
                        });

                        int position = holder.getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION && position>=0){
                            categoryBatch.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, categoryBatch.size());
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
        return categoryBatch.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new FilterModelClass(filterList, AdapterRoutine.this);
        }
        return filter;
    }


//    row_batch_for_routine

    public static class  ViewHolder extends RecyclerView.ViewHolder{

        TextView batch ;
        ImageButton delete ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            batch = itemView.findViewById(R.id.category_title);
            delete = itemView.findViewById(R.id.delete_icon);

        }
    }

}
