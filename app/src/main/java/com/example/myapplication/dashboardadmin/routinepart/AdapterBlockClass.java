package com.example.myapplication.dashboardadmin.routinepart;

import android.content.Context;
import android.content.DialogInterface;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterBlockClass extends RecyclerView.Adapter<AdapterBlockClass.ViewHolder> implements Filterable {


    public com.example.myapplication.dashboardadmin.routinepart.FilterBlockClass filter ;
    public ArrayList<ModelFreeClassShow> blockArrayList, filterList;
    private Context context;
    public String UserType = "";
    FirebaseAuth firebaseAuth ;

    public AdapterBlockClass(ArrayList<ModelFreeClassShow> blockArrayList, Context context) {
        this.blockArrayList = blockArrayList;
        this.context = context;
        this.filterList = blockArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_block_class, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelFreeClassShow modelFreeClassShow = blockArrayList.get(position);


        String classNamest = modelFreeClassShow.getClassName();
        String Departmentst = modelFreeClassShow.getDepartment();
        String Sectionst = modelFreeClassShow.getSection();
        String batchst = modelFreeClassShow.getBatch();
        String daytv = modelFreeClassShow.getDay();
        String timetv = modelFreeClassShow.getClassTime();
        String classTypetv = modelFreeClassShow.getClassType();

        holder.className.setText(classNamest);
        holder.Department.setText(Departmentst);
        holder.Section.setText(Sectionst);
        holder.Batch.setText(batchst);
        firebaseAuth = FirebaseAuth.getInstance();
        userType();

        String [] clasTypess = {"FREE", "BLOCK"};
//        holder.edit.setVisibility(View.INVISIBLE);

        if(UserType.equals("USER") || UserType.equals("CR")){
            holder.edit.setVisibility(View.INVISIBLE);
        }else {
            holder.edit.setVisibility(View.VISIBLE);
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setIcon(R.drawable.baseline_edit_24);
                    builder.setTitle("EDIT CLASSTYPE")
                            .setMessage("Are Want to sure you convert this class Type into FREE")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("Batch", "");
                                    hashMap.put("Section", "");
                                    hashMap.put("Department", "");
                                    hashMap.put("classType", "FREE");
                                    hashMap.put("className", classNamest);
                                    hashMap.put("classTime", timetv);
                                    hashMap.put("day", daytv);


                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("FreeClassroom");

                                    ref.child(daytv)
                                            .child(timetv)
                                            .child("classRoom")
                                            .child(classNamest)
                                            .updateChildren(hashMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(context, "Set class type free Successfully", Toast.LENGTH_SHORT).show();

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(context, "Set class type free not Successfully", Toast.LENGTH_SHORT).show();

                                                }
                                            });


                                    Toast.makeText(context, "Set class type free", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();

                }
            });
        }

    }

    private void userType() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//            reference.child(currentUser.getUid())
        reference.child(firebaseAuth.getCurrentUser().getEmail().replace(".",","))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String Name = "" + snapshot.child("Name").getValue();
                        String email = "" + snapshot.child("email").getValue();
                        UserType = ""+snapshot.child("userType").getValue(String.class);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    @Override
    public int getItemCount() {
        return blockArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new FilterBlockClass(filterList, AdapterBlockClass.this);

        }
        return filter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView className ,Department ,Section , Batch ;
        ImageButton edit ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            className = itemView.findViewById(R.id.ClassName);
            Department = itemView.findViewById(R.id.Department);
            Section = itemView.findViewById(R.id.Section);
            Batch = itemView.findViewById(R.id.Batch);
            edit = itemView.findViewById(R.id.editIconss);

        }
    }
}
