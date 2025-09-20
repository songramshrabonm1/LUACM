package com.example.myapplication.dashboardadmin.problemsloving.ratingWise;

import android.widget.Filter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FilterModel extends Filter {


    ArrayList<ModelRating> filterList ;
    public com.example.myapplication.dashboardadmin.problemsloving.ratingWise.AdapterRating adapterRating;
    public FilterModel(ArrayList<ModelRating> filterList, AdapterRating adapterRating) {
        this.filterList = filterList;
        this.adapterRating = adapterRating;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        ArrayList<ModelRating> FilterModel = new ArrayList<>();
        charSequence = charSequence.toString().toUpperCase();

        if(charSequence != null && charSequence.length()>0){
            for(int i = 0 ; i<filterList.size(); i++){
                if(filterList.get(i).getRating().toUpperCase().contains(charSequence)){
                    FilterModel.add(filterList.get(i));
                }
            }
            results.count = FilterModel.size();
            results.values = FilterModel;
        }else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapterRating.ratingsCateogyArraylist = (ArrayList<ModelRating>) filterResults.values;
        adapterRating.notifyDataSetChanged();
    }
}
