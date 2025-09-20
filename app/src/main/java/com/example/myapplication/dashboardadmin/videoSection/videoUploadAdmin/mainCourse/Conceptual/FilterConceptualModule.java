package com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Conceptual;

import android.widget.Filter;

import com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Algorithm.ModelAlgoModule;

import java.util.ArrayList;

public class FilterConceptualModule extends Filter {

    ArrayList<ModelConceptualModule> filterList = new ArrayList<>();
    private com.example.myapplication.dashboardadmin.videoSection.videoUploadAdmin.mainCourse.Conceptual.AdapterConceptualModule adapterConceptualModule;

    public FilterConceptualModule(ArrayList<ModelConceptualModule> filterList, AdapterConceptualModule adapterConceptualModule) {
        this.filterList = filterList;
        this.adapterConceptualModule = adapterConceptualModule;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        if(charSequence != null && charSequence.length() > 0){
            ArrayList<ModelConceptualModule> filterModel = new ArrayList<>();
            charSequence = charSequence.toString().toUpperCase();
            for(int i =0 ; i<filterList.size(); i++){
                if(filterList.get(i) . toString().toUpperCase().contains(charSequence)){
                    filterModel.add(filterList.get(i));
                }
                results.count = filterModel.size();
                results.values = filterModel;
            }

        }else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapterConceptualModule.categoryModule = (ArrayList<ModelConceptualModule>) filterResults.values;
        adapterConceptualModule.notifyDataSetChanged();
    }
}
