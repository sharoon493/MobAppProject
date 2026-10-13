package com.example.sharo.project;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class DonateformFragment extends Fragment {


    EditText Username,Email,Condition,Donate_Descr,MobileNumber,Country,City;
    ImageButton imgView;
    ImageView imgView2;
    LinearLayout AddedPicLayout;
    Button Retry,Cancel;
    Button AddLocation;
    int testbit =0;
    Myinterface2 myinterface;



    public DonateformFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View temp =inflater.inflate(R.layout.fragment_donateform, container, false);

        Username =temp.findViewById(R.id.userNameEdit);
        Email =temp.findViewById(R.id.EmailEdit);
        Condition=temp.findViewById(R.id.ConditionEdit);
        Donate_Descr= temp.findViewById(R.id.DonateEdit);
        MobileNumber =temp.findViewById(R.id.MobileEdit);
        AddLocation = temp.findViewById(R.id.AddLocation);

        imgView = temp.findViewById(R.id.imgView);
        imgView2 = temp.findViewById(R.id.imgView2);
        AddedPicLayout = temp.findViewById(R.id.AddedPicLayout);
        Retry =temp.findViewById(R.id.Retry_action);
        Cancel =temp.findViewById(R.id.Cancel_action);

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
