package com.example.myapplication.dashboardadmin;

import android.content.Context;
import android.widget.Filter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class FilterPdfAdmin extends Filter {
    private ArrayList<ModelPdf> FilterList;
    public AdapterPdfAdmin adapterPdfAdmin;

    public FilterPdfAdmin(ArrayList<ModelPdf> FilterList, AdapterPdfAdmin adapterPdfAdmin) {
//        this.FilterList = FilterList;
        this.FilterList = FilterList != null ? FilterList : new ArrayList<>();
        this.adapterPdfAdmin = adapterPdfAdmin;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if(constraint != null && constraint.length()>0){
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelPdf> filteredModels = new ArrayList<>();
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
        adapterPdfAdmin.pdfArrayList = (ArrayList<ModelPdf>) filterResults.values;
        adapterPdfAdmin.notifyDataSetChanged();
    }


}