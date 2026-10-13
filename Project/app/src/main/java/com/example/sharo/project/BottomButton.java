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
public class BottomButton extends Fragment {

    ImageButton RaiseBTN,DonateBTN;
    Myinterface myinterface;

    public BottomButton() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_button, container, false);

        RaiseBTN = view.findViewById(R.id.RaiseBTN);
        DonateBTN = view.findViewById(R.id.DonateBTN);

        RaiseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myinterface.ButtonClicked("RaiseRequest");
            }
        });
        DonateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myinterface.ButtonClicked("Donate");
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        myinterface = (Myinterface) context;
    }


}
