package com.example.myapplication.dashboardadmin.addAdminCr;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterInformation extends RecyclerView.Adapter<AdapterInformation.ViewHolder>implements Filterable {

    FirebaseAuth firebaseAuth;
    private Context context;
    ArrayList<ModelShowInfo> categoryInfoArrayList , filterlist ;
    String valueeInfo  = "";
    String userTypees = "";
    public com.example.myapplication.dashboardadmin.addAdminCr.FilterInfo filter;

    ProgressDialog progressDialog ;

    public AdapterInformation(Context context, ArrayList<ModelShowInfo> categoryInfoArrayList, String valueeInfo , String userTypees ) {
        this.context = context;
        this.categoryInfoArrayList = categoryInfoArrayList;
        this.filterlist = categoryInfoArrayList;
        this.valueeInfo = valueeInfo;
        this.userTypees = userTypees;

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_show_info, parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
        //        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModelShowInfo modelShowInfo = categoryInfoArrayList.get(position);
        String name = modelShowInfo.getName();
        String email = modelShowInfo.getEmail();
        String mbl = modelShowInfo.getMobile();
        long timestamp = modelShowInfo.getTimestamp();
        String cr = modelShowInfo.getCR();
        String admin = modelShowInfo.getADMIN();

        holder.names.setText(name);
        holder.email.setText(email);
        holder.number.setText(mbl);



        /*
        if(Rule == "ADMIN"){
                Rule = "ADMIN";
                hashMap.put("ADMIN" ,"ADMIN" );
                hashMap.put("CR" , "NO");

            }else if(Rule == "CR"){
                Rule = "CR";
                hashMap.put("ADMIN" ,"NO");
                hashMap.put("CR" , "CR");
            }else if(Rule == "ADMIN_OR_CR"){
                hashMap.put("ADMIN" , "ADMIN");
                hashMap.put("CR", "CR");
         */

        if(cr.equals("NO") && admin.equals("NO") ){
            holder.userType.setVisibility(View.GONE);

        }else if(admin .equals("ADMIN") && cr.equals("NO")){
            holder.userType.setText("ADMIN");


        }else if(admin.equals("NO") && cr.equals("CR") ){
            holder.userType.setText("CR");
        }else{
            holder.userType.setText("ADMIN AND CR");
        }

        userTypees = userTypees.toString().toUpperCase().trim();
//        if(userTypees != "USER") {
        if(userTypees.equals("ADMIN") || userTypees.equals("ADMIN_OR_CR")){
            holder.delete.setVisibility(View.VISIBLE);
            holder.edit.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setIcon(R.drawable.baseline_delete_24);
                    builder.setTitle("DELETE")
                            .setMessage("Are You Want to Delete this Information...")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                        deleteitems(valueeInfo);
                                    deleteitems(modelShowInfo, holder, valueeInfo);

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


                    Intent intent =new Intent(context, EditInformation.class);
                    intent.putExtra("name",name);
                    intent.putExtra("email", email);
                    intent.putExtra("mbl", mbl);
                    intent.putExtra("timestamp",timestamp );
                    intent.putExtra("cr", cr);
                    intent.putExtra("admin",admin);
                    intent.putExtra("valueeInfo", valueeInfo);
                    context.startActivity(intent);

//ADDEMAILREGISTERSTUDENT    CRADDADMINADD

//                    if(valueeInfo)

                }
            });
        }else{
            holder.delete.setVisibility(View.INVISIBLE);
            holder.edit.setVisibility(View.INVISIBLE);
        }


    }

    private void deleteitems(ModelShowInfo modelShowInfo, ViewHolder holder, String valueeInfo) {
        String safeEmail = modelShowInfo.getEmail();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AdminAddingSection");
        ref.child(""+valueeInfo)
//                .child(""+modelShowInfo.getTimestamp())
                .child(""+modelShowInfo.getEmail().replace("." , ","))
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                categoryInfoArrayList.remove(modelShowInfo); // লিস্ট থেকে আইটেম রিমুভ করুন
//                                notifyDataSetChanged(); // রিসাইকেলারভিউ আপডেট করুন

                                Toast.makeText(context, "Deleted Item successfully" , Toast.LENGTH_SHORT).show();

                            }
                        });
                        int position = holder.getPosition();
                        if(position != RecyclerView.NO_POSITION && position >= 0){
                             categoryInfoArrayList.remove(modelShowInfo); // লিস্ট থেকে আইটেম রিমুভ করুন
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, categoryInfoArrayList.size());
                        }

                        }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Deleted not successfully" , Toast.LENGTH_SHORT).show();

                    }
                });


        //UPDATE children  users er usertype
//        String safeEmail = modelShowInfo.getEmail();
        DatabaseReference referenceUser = FirebaseDatabase.getInstance().getReference("Users");
        referenceUser.child(safeEmail.replace(".",","))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            HashMap<String, Object> updateHashMap = new HashMap<>();
                            updateHashMap.put("userType", "USER");

                            referenceUser.child(safeEmail).updateChildren(updateHashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context , "Set User Type USER" , Toast.LENGTH_SHORT).show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context , "Set User Type Failed" , Toast.LENGTH_SHORT).show();

                                        }
                                    });


                        }else{

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    @Override
    public int getItemCount() {
        return categoryInfoArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new FilterInfo(filterlist,AdapterInformation.this);
        }
        return filter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

         TextView names , email , number , userType  ;
         ImageButton delete , edit ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            names = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.emails);
            number = itemView.findViewById(R.id.mobileNumberr);
            delete = itemView.findViewById(R.id.delete_icon);
            edit = itemView.findViewById(R.id.editIcon);
            userType = itemView.findViewById(R.id.newUserId);

        }
    }
}
