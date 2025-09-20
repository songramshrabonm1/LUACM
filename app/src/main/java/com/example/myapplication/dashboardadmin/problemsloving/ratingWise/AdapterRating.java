package com.example.myapplication.dashboardadmin.problemsloving.ratingWise;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.dashboardadmin.problemsloving.ratingWise.AdapterRating ;
import com.example.myapplication.dashboardadmin.routinepart.AdapterRoutine;
import com.example.myapplication.dashboardadmin.problemsloving.ratingWise.ModelRating ;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.security.cert.Extension;
import java.util.ArrayList;

public class AdapterRating extends RecyclerView.Adapter<AdapterRating.Viewholder> implements Filterable {


    public ArrayList<ModelRating> ratingsCateogyArraylist , filterList;
    private Context context;
    private String Platform = "" , UserType = "";
    private com.example.myapplication.dashboardadmin.problemsloving.ratingWise.FilterModel filter;
    private ProgressDialog progressDialog;
    public AdapterRating(Context context, ArrayList<ModelRating> ratingsCateogyArraylist , String platform , String UserType) {
        this.ratingsCateogyArraylist = ratingsCateogyArraylist;
        this.context = context;
        this.filterList = ratingsCateogyArraylist;
        this.Platform = platform;
        this.UserType = UserType;


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Deleting......");
        progressDialog.setCanceledOnTouchOutside(false);

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rating_wise_contest_set, parent, false);
       Viewholder vh = new Viewholder(view);
       return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ModelRating modelRating = ratingsCateogyArraylist.get(position);
        String links = modelRating.getLink();
        String platformName = modelRating.getPlatform().toString().toUpperCase().trim();
        String rating = modelRating.getRating();
        String password = modelRating.getPassword();

        System.out.println("Model Rating"+ modelRating);
        System.out.println("Links"+ links);
        System.out.println("platform"+ platformName);
        System.out.println("rating"+ rating);
        System.out.println("password"+ password);

        holder.password.setText("Password : "+ password);
        holder.link.setText("Opne Vjudge Sheet Link  ");

            holder.link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String urll = "" + links;
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urll));
                        context.startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

//        Glide.with(RoutineShow.this)
//                .load(Url)
//                .into(image);
//        CODEFORCES
        //CODECHEF

        if(platformName.equals("CODEFORCES")){
            holder.rating.setText("Rating - "+ rating);

            holder.delete.setVisibility(View.INVISIBLE);
            holder.edit.setVisibility(View.INVISIBLE);


            Glide.with(context)
                    .load(R.drawable.img_8)
                    .into(holder.ProbleSolvingPlatformImage);


        }else if(platformName.equals("CODECHEF")){
            holder.rating.setText("Rating - "+ rating);

            holder.delete.setVisibility(View.INVISIBLE);

            Glide.with(context)
                    .load(R.drawable.img_9)
                    .into(holder.ProbleSolvingPlatformImage);
        }else if(platformName.equals("CATEGORYWISE")){
            holder.rating.setText("Category - "+ rating);

            holder.delete.setVisibility(View.INVISIBLE);
            holder.edit.setVisibility(View.INVISIBLE);



            Glide.with(context)
                    .load(R.drawable.img_5)
                    .into(holder.ProbleSolvingPlatformImage);
        }else if(platformName.equals("LUACM")){
            holder.rating.setText("LUACM - "+ rating);

            if(UserType.equals("ADMIN") && UserType != null) {

                holder.edit.setVisibility(View.VISIBLE);
                holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, EditCategories.class);


                        intent.putExtra("links", links);
                        intent.putExtra("platformName", platformName);
                        intent.putExtra("rating", rating);
                        intent.putExtra("password", password);

                        context.startActivity(intent);

                    }
                });

                holder.delete.setVisibility(View.VISIBLE);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setIcon(R.drawable.baseline_delete_24);
                        builder.setTitle("Delete")
                                .setMessage("Are You Want to Delete This")
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DeleteCategory(modelRating, holder);
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


            Glide.with(context)
                    .load(R.drawable.img_10)
                    .into(holder.ProbleSolvingPlatformImage);
        }else{
            holder.rating.setText("Programming: "+ rating);

            if(UserType.equals("ADMIN")) {

                holder.edit.setVisibility(View.VISIBLE);
                holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, EditCategories.class);

                        intent.putExtra("links", links);
                        intent.putExtra("platformName", platformName);
                        intent.putExtra("rating", rating);
                        intent.putExtra("password", password);

                        context.startActivity(intent);
                    }
                });

                holder.delete.setVisibility(View.VISIBLE);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setIcon(R.drawable.baseline_delete_24);
                        builder.setTitle("Delete")
                                .setMessage("Are You Want to Delete This")
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DeleteCategory(modelRating, holder);
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

            Glide.with(context)
                    .load(R.drawable.img_11)
                    .into(holder.ProbleSolvingPlatformImage);
        }
    }

    private void DeleteCategory(ModelRating modelRating, Viewholder holder) {
        progressDialog.setMessage("Deleting.....");
        progressDialog.show();

        String rating = modelRating.getRating();
        String link = modelRating.getLink();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child(""+Platform)
                .child(rating)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Deleting..." , Toast.LENGTH_SHORT).show();

                            }
                        });
                        int position = holder.getPosition();
                        if(position != RecyclerView.NO_POSITION && position >= 0){
                            ratingsCateogyArraylist.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, ratingsCateogyArraylist.size());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Deleting Not successfully...", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    @Override
    public int getItemCount() {
        return ratingsCateogyArraylist.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new FilterModel(filterList, AdapterRating.this);
        }
        return filter;
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        TextView rating , password , link ;
        ImageView ProbleSolvingPlatformImage;
        ImageButton delete , edit ;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            ProbleSolvingPlatformImage = itemView.findViewById(R.id.ProbleSolvingPlatformImage);
            rating = itemView.findViewById(R.id.Rating);
            password = itemView.findViewById(R.id.VjudgePassword);
            link = itemView.findViewById(R.id.DeivisionName);
            delete = itemView.findViewById(R.id.deleteIcon);
            edit = itemView.findViewById(R.id.editIcon);



        }
    }
}
