package com.example.sharo.project;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.AndroidException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TagReason extends DialogFragment {
    String[] Tags ={"Education تعلیم", "Poverty غربت",
            "Jobless بےروزگار","NaturalDisaster قدرتی آفت","Lack of Resourse وسائل کی کمی",
            "NGO Work فلاح و بہبود کے لیے" , "Human Rights انسانی حقوق" ,"Fund Raising " , "Living"};
    ListView LV;
    EditText ReasonFrag;
    String tag;
    Button Done;
    TextView tagSelect;
    Myinterface2 d;



    public TagReason() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_tag_reason, container, false);

        LV = v.findViewById(R.id.ListView);
        Done =v.findViewById(R.id.Done);
        tagSelect =v.findViewById(R.id.tagSelect);
        ReasonFrag = v.findViewById(R.id.ReasonFrag);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ReasonFrag.getText().toString().equals("")){
                    d.data("Reason",tag+"\n"+ReasonFrag.getText());
                    dismiss();
                }else{
                    if(!tag.equals(""))
                    d.data("Reason",tag);
                    dismiss();
                }
                Toast.makeText(getContext(),""+tag,Toast.LENGTH_SHORT).show();
            }
        });

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tag = LV.getItemAtPosition(position).toString();
                tagSelect.setText("Select Tag: "+tag);
            }
        });
        ReasonFrag =v.findViewById(R.id.ReasonFrag);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.spinner_item,Tags);
        LV.setAdapter(adapter);


        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        d = (Myinterface2) context;
    }

}
