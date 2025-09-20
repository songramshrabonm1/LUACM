package com.example.myapplication.dashboardadmin.routinepart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.AdapterCategory;
import com.example.myapplication.dashboardadmin.ModelCategory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Parameter;
import java.util.ArrayList;

public class AdapterFreeClass extends RecyclerView.Adapter<AdapterFreeClass.ViewHolder>{


    public ArrayList<ModelFreeClassShow> freeClassList  ;
    private Context context;
    private String UserType = "" , day , time;
    ProgressDialog progressDialog ;

    public AdapterFreeClass(ArrayList<ModelFreeClassShow> freeClassList, Context context ,String UserType ) {
         this.freeClassList = freeClassList;
        this.context = context;
        this.UserType = UserType;
//        this.day = day;
//        this.time = time;


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Deletinggg");
        progressDialog.setCanceledOnTouchOutside(false);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_free_class , parent , false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        ModelFreeClassShow modelFreeClassShow = new ModelFreeClassShow();
        ModelFreeClassShow modelFreeClassShow = freeClassList.get(position);
//        holder.clasroom.setText(freeClassList.get(position));
//        holder.deleteButton.setVisibility(View.INVISIBLE); // Remove or change visibility conditionally

        holder.clasroom.setText(modelFreeClassShow.getClassName());
    }



    @Override
    public int getItemCount() {
        return freeClassList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView clasroom ;
//        ImageButton deleteButton ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            clasroom = itemView.findViewById(R.id.ClassRoomTitle);
//            deleteButton = itemView.findViewById(R.id.FreeClassdelete_icon);

        }
    }
}
