package com.example.sharo.project;


import android.content.Context;
import android.graphics.Color;
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
public class Tab extends Fragment {

    ImageButton RequestsBTN,DonationsBTN;
    Myinterface myinterface;

    public Tab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tab, container, false);
        RequestsBTN = view.findViewById(R.id.RequestsButton);
        DonationsBTN = view.findViewById(R.id.ItemsButton);

        RequestsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestsBTN.setImageResource(R.drawable.request);
                DonationsBTN.setImageResource(R.drawable.donation_bw);
                RequestsBTN.setBackgroundResource(R.drawable.button_style5);
                DonationsBTN.setBackgroundResource(R.drawable.button_style4);
//                RequestsBTN.setTextColor(getResources().getColor(R.color.colorAccent));
//                DonationsBTN.setTextColor(getResources().getColor(R.color.white));
                myinterface.ButtonClicked("Requests" );
            }
        });
        DonationsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DonationsBTN.setImageResource(R.drawable.donation);
                RequestsBTN.setImageResource(R.drawable.request_bw);
                DonationsBTN.setBackgroundResource(R.drawable.button_style5);
                RequestsBTN.setBackgroundResource(R.drawable.button_style4);
//                RequestsBTN.setTextColor(getResources().getColor(R.color.white));
//                DonationsBTN.setTextColor(getResources().getColor(R.color.colorAccent));
                myinterface.ButtonClicked("Donations" );
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
