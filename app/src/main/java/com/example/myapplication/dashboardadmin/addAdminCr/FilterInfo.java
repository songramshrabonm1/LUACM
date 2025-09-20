package com.example.myapplication.dashboardadmin.addAdminCr;

import android.content.Context;
import android.widget.Filter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FilterInfo extends Filter {

//    categoryInfoArrayList

    ArrayList<ModelShowInfo> filterCategory = new ArrayList<>();
    public com.example.myapplication.dashboardadmin.addAdminCr.AdapterInformation adapterInformation;

    public FilterInfo(ArrayList<ModelShowInfo> filterCategory , AdapterInformation adapterInformation) {
        this.filterCategory = filterCategory;
        this.adapterInformation = adapterInformation;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        ArrayList<ModelShowInfo> filterModel = new ArrayList<>();
        charSequence = charSequence.toString().toUpperCase();
        if(charSequence != null && charSequence.length()>0){
            for(int i =0 ; i<filterCategory.size(); i++){
                if(filterCategory.get(i).getEmail().toUpperCase().contains(charSequence)){
                    filterModel.add(filterCategory.get(i));
                }
            }

            results.count = filterModel.size();
            results.values = filterModel;
        }else{
            results.count = filterCategory.size();
            results.values = filterCategory;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapterInformation.categoryInfoArrayList = (ArrayList<ModelShowInfo>) filterResults.values;
        adapterInformation.notifyDataSetChanged();

    }
}
