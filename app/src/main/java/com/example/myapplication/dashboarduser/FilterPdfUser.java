package com.example.myapplication.dashboarduser;

import android.content.Context;
import android.widget.Filter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class FilterPdfUser extends Filter {
    private ArrayList<ModelPdfUser> FilterList;
    public AdapterPdfUser adapterPdfAdmin;

    public FilterPdfUser(ArrayList<ModelPdfUser> FilterList, AdapterPdfUser adapterPdfAdmin) {
//        this.FilterList = FilterList;
        this.FilterList = FilterList != null ? FilterList : new ArrayList<>();
        this.adapterPdfAdmin = adapterPdfAdmin;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if(constraint != null && constraint.length()>0){
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelPdfUser> filteredModels = new ArrayList<>();
            for(int i = 0 ; i<FilterList.size(); i++){
                if(FilterList.get(i).getSemesterExam().toUpperCase().contains(constraint)){
                    filteredModels.add(FilterList.get(i));
                }
            }

            results.count = filteredModels.size();
            results.values = filteredModels;


        }else{
            results.count = FilterList.size();
            results.values = FilterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults filterResults) {
        adapterPdfAdmin.pdfArrayList = (ArrayList<ModelPdfUser>) filterResults.values;
        //ekhane adapterPdfAdmin Name lekha object tar kintu eita adapterPdfUser class er object
        adapterPdfAdmin.notifyDataSetChanged();
    }


}