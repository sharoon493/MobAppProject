package com.example.sharo.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OfflineActivity extends AppCompatActivity implements Myinterface {


    RecyclerView recyclerView;
    MyAdapter myAdapter;
    MyAdapterD myAdapterD;
    RecyclerViewFrag RVfragment;
    ArrayList<Requests> R_list = new ArrayList<>();
    ArrayList<Donations> D_list = new ArrayList<>();
    DatabaseReference myRef;
    long lastReqId = 0, lastDonId = 0;
    int tab_bit =0;

    Tab tbFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);

        RVfragment = (RecyclerViewFrag) getSupportFragmentManager().findFragmentById(R.id.R_viewFrag);
        tbFragment = (Tab) getSupportFragmentManager().findFragmentById(R.id.tabs);
        recyclerView = RVfragment.recyclerView;
        myRef = FirebaseDatabase.getInstance().getReference();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("Mytag", "Moving to detail 1");

                Requests tempObject = R_list.get(position);


                Intent it = new Intent(getApplicationContext(), DetailActivity.class);
                it.putExtra("Username", tempObject.Username);
                it.putExtra("MobileNumber", tempObject.MobileNumber);
                it.putExtra("Country", tempObject.Country);
                it.putExtra("City", tempObject.City);
                it.putExtra("Email", tempObject.Email);
                it.putExtra("Request_Desc", tempObject.Request_Desc);
                it.putExtra("Reason", tempObject.Reason);

                Log.d("Mytag", "Moving to detail");
                startActivity(it);

            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef.child("Users").child(currentFirebaseUser.getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                R_list.clear();
                for (DataSnapshot snapshot1 : dataSnapshot.child("Requests").getChildren()) {
                    Requests r = snapshot1.getValue(Requests.class);
                    if (r.Request_id < lastReqId) {
                        R_list.add(r);
                        lastReqId = r.Request_id;
                    } else {
                        R_list.add(0, r);
                    }
                }
                for (DataSnapshot snapshot1 : dataSnapshot.child("Donations").getChildren()) {
                    Donations d = snapshot1.getValue(Donations.class);
                    if (d.Donate_id < lastDonId) {
                        D_list.add(d);
                        lastDonId = d.Donate_id;
                    } else {
                        D_list.add(0, d);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
    @Override
    public void ButtonClicked(String text) {
        if(text.equals("Donations")){
            tab_bit=1;
            myAdapterD = new MyAdapterD(D_list);
            recyclerView.setAdapter(myAdapterD);

        }
        else if(text.equals("Requests")){
            tab_bit=0;

            myAdapter = new MyAdapter(R_list);
            recyclerView.setAdapter(myAdapter);

        }
    }
}