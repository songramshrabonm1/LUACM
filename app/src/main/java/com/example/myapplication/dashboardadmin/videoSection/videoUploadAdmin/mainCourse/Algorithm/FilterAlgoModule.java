package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm;

import android.widget.Filter;

import java.util.ArrayList;

public class FilterAlgoModule extends Filter {
    ArrayList<ModelAlgoModule> filterList = new ArrayList<>();
    private com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.AdapterAlgoModule adapterAlgoModule;

    public FilterAlgoModule(ArrayList<ModelAlgoModule> filterList, AdapterAlgoModule adapterAlgoModule) {
        this.filterList = filterList;
        this.adapterAlgoModule = adapterAlgoModule;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();

        if(charSequence != null && charSequence.length()>0){
            ArrayList<ModelAlgoModule> filterModel = new ArrayList<>();
            charSequence = charSequence.toString().toUpperCase();
            for(int i =0 ;i<filterList.size(); i++){
                if(filterList.get(i).getVideoTitle().contains(charSequence)){
                    filterModel.add(filterList.get(i));
                }
            }
            results.count = filterModel.size();
            results.values = filterModel;
        }else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapterAlgoModule.categoryModule = (ArrayList<ModelAlgoModule>) filterResults.values;
        adapterAlgoModule.notifyDataSetChanged();
    }
}
