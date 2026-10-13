package com.example.sharo.project;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class FomBottomFrag extends Fragment {


    Button Submit;
    ImageButton pic;
    Myinterface2 myinterface;
    public FomBottomFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_fom_bottom, container, false);
        Submit = v.findViewById(R.id.RFormSubmitBTN);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myinterface.ButtonClicked("SubmitRequest");
            }
        });




        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        myinterface = (Myinterface2) context;
    }

}
