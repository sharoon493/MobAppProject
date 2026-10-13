package com.example.sharo.project;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountryList extends DialogFragment {


    Spinner countrylist;
    Myinterface2 d;
    public CountryList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_country_list, container, false);
        countrylist = v.findViewById(R.id.spinnerCountry);
        GetCountryList();
        countrylist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if(!countrylist.getSelectedItem().toString().equals("Select Country")){
                    d.Country(countrylist.getSelectedItem().toString());
                    dismiss();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



        return v;
    }
    public void GetCountryList(){
        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<>();
        String country;
        for(Locale lo :locale){
            country = lo.getDisplayCountry();
            if(country.length()>0 && !countries.contains(country)){
                countries.add(country);
            }
        }
        Collections.sort(countries,String.CASE_INSENSITIVE_ORDER);
        countries.add(0,"Select Country");
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(),R.layout.spinner_item,countries);
        countrylist.setAdapter(adapter);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        d = (Myinterface2) context;
    }
}
