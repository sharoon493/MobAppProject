package com.example.sharo.project;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestForm extends Fragment {


    EditText Username,Email,Request_Desc,MobileNumber,City;
    ImageButton imgView;
    ImageView imgView2;
    LinearLayout AddedPicLayout;
    Button Retry,Cancel,Country,Reason;
    int testbit =0;
    Myinterface2 myinterface;
    public RequestForm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View temp= inflater.inflate(R.layout.fragment_request_form, container, false);

        Username =temp.findViewById(R.id.userNameEdit);
        Email =temp.findViewById(R.id.EmailEdit);
        Request_Desc=temp.findViewById(R.id.RequestEdit);
        Reason= temp.findViewById(R.id.ReasonBtn);
        MobileNumber =temp.findViewById(R.id.MobileEdit);
        Country= temp.findViewById(R.id.CountryEdit);
        Country.setTextColor(this.getResources().getColor(R.color.colorAccent));
        Country.setText("Country");
        City =temp.findViewById(R.id.CityEdit);
        imgView = temp.findViewById(R.id.imgView);
        imgView2 = temp.findViewById(R.id.imgView2);
        AddedPicLayout = temp.findViewById(R.id.AddedPicLayout);
        Retry =temp.findViewById(R.id.Retry_action);
        Cancel =temp.findViewById(R.id.Cancel_action);

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testbit =1;
                myinterface.ButtonClicked("AddImage");
            }
        });


        return temp;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        myinterface = (Myinterface2) context;
    }

}
