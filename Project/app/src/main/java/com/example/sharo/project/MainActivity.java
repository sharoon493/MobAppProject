package com.example.sharo.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.InetAddress;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;


public class MainActivity extends AppCompatActivity implements Myinterface {
    TextView msg;
    ProgressBar progressSpinner;
    MyAdapter mAdapter;
    MyAdapterD myAdapterD;
    BottomButton BB_frag;
    Tab Tab_frag;ErrorFragment EFrag;
    long lastDonId=0, lastReqId =0;
    RecyclerView recyclerView;
    ArrayList<Requests> Req_View_list = new ArrayList<>();
    public ArrayList<Requests> SearchRequestList = new ArrayList<>();
    public   ArrayList<Donations> SearchDonation = new ArrayList<>();
    ArrayList<Donations> Donate_View_list = new ArrayList<>();
    private DatabaseReference myRef;
    SharedPreferences p;
    RecyclerViewFrag rvFrag;
    int tab_bit=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressSpinner = findViewById(R.id.progressSpinner);

        Tab_frag = (Tab) getSupportFragmentManager().findFragmentById(R.id.tabs);
        BB_frag = (BottomButton) getSupportFragmentManager().findFragmentById(R.id.BottomButton);

        rvFrag = (RecyclerViewFrag) getSupportFragmentManager().findFragmentById(R.id.R_viewFrag);
        recyclerView = (RecyclerView) rvFrag.recyclerView;


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new  LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("Mytag","Moving to detail 1");

                Requests tempObject = Req_View_list.get(position);


                Intent it = new Intent(getApplicationContext(),DetailActivity.class);
                it.putExtra("Username",tempObject.Username);
                it.putExtra("MobileNumber",tempObject.MobileNumber);
                it.putExtra("Country",tempObject.Country);
                it.putExtra("City",tempObject.City);
                it.putExtra("Email",tempObject.Email);
                it.putExtra("Request_Desc",tempObject.Request_Desc);
                it.putExtra("Reason",tempObject.Reason);

                Log.d("Mytag","Moving to detail");
                startActivity(it);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        myRef = FirebaseDatabase.getInstance().getReference();
        //msg = findViewById(R.id.msg);
        //msg.setText("Default");

        //myRef.child("R4").setValue(R2);
//        Requests R3 = new Requests("Khan","Hi gkgkgkg " +
//                "this is pakistan" +
//                "ilove my country too much ia ma student of myRef.addValueEventListener(new ValueEventListene" +
//                "            @Override" +
//                "            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {" +
//                "                //Requests temp = dataSnapshot.getValue(Requests.class);" +
//                "                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {" +
//                "                    Requests user = snapshot.getValue(Requests.class);est name");
//        myRef.child("R3").setValue(R3);

//        Donations Dtest = new Donations(1,"Sharoon","sharoon493@gmail.com","I have shirts if someone " +
//                "is willing to get that he can contact","They are in Good condition",
//                "03004385355",0.0,0.0,
//                ""+java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()),
//                "https://firebasestorage.googleapis.com/v0/b/test1-acff0.appspot.com/o/" +
//                        "photos%2F57303?alt=media&token=50074573-cda6-4ce3-a305-35a97285f61a");
//
//        String key = (UUID.randomUUID().toString());
//        (myRef.child("Donations")).child(key).setValue(Dtest);


        myRef.child("Users").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Requests temp = dataSnapshot.getValue(Requests.class);
                Log.d("Tagtest","1 here");

                Req_View_list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //String temp = snapshot.getValue().toString();

                        for(DataSnapshot snapshot1 :snapshot.child("Requests").getChildren()){
                            Requests r = snapshot1.getValue(Requests.class);
                            if(r.Request_id<lastReqId){
                                Req_View_list.add(r);
                                lastReqId =r.Request_id;
                            }else{
                                Req_View_list.add(0,r);
                            }
                        }
                        for(DataSnapshot snapshot1 :snapshot.child("Donations").getChildren()){
                        Donations d = snapshot1.getValue(Donations.class);
                            if(d.Donate_id<lastDonId){
                                Donate_View_list.add(d);
                                lastDonId = d.Donate_id;
                            }else{
                                Donate_View_list.add(0,d);
                            }
                        }
                        //Log.d("MyTAG",temp);
//                        Req_View_list.add(user);

                }
                Log.d("Tagtest","1 "+ Req_View_list.get(0).Email);
                mAdapter = new MyAdapter(Req_View_list);
                recyclerView.setAdapter(mAdapter);
                progressSpinner.setVisibility(View.GONE);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Tagtest",""+databaseError.toString());
            }

        });


        myRef.child("Donations").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Requests temp = dataSnapshot.getValue(Requests.class);
                Log.d("Tagtest","1D here");
                String temp = dataSnapshot.getValue().toString();
                Log.d("Tagtest",temp);
                Donate_View_list.clear();
                int max=0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //String temp = snapshot.getValue().toString();
                    try {
                        Donations user = snapshot.getValue(Donations.class);
                        //Log.d("Tagtest",user.UserName + "  "+ user.Donate_Descr);
                        Donate_View_list.add(user);
//                        if(user.Donate_id){
//                            Donate_View_list.add(user);
//                            max = user.Donate_id;
//                        }else{
//                            Donate_View_list.add(0,user);
//                        }

                    }catch(Exception e){
                        //EFrag.ERROR_IN.setVisibility(View.VISIBLE);
                        Log.d("Tagtest","From try/catch"+e);
                    }
                    myAdapterD = new MyAdapterD(Donate_View_list);

                }

                Log.d("Tagtest","size "+ Donate_View_list.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Tagtest",""+databaseError.toString());
            }

        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                final ActionBar MainTopBar=getSupportActionBar();
                if( MainTopBar != null) {
                    MainTopBar.setTitle("");
                    MainTopBar.setDisplayShowCustomEnabled(true);
                    MainTopBar.setCustomView(R.layout.custom_searchbar);
                    final EditText searchBar = (EditText) MainTopBar.getCustomView().findViewById(R.id.searchBar);
                    final ImageButton backButton = (ImageButton) MainTopBar.getCustomView().findViewById(R.id.BackMain);
                    backButton.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                          getSupportActionBar().setDisplayShowCustomEnabled(false);
                          getSupportActionBar().setTitle("OnDonate");
                          rvFrag.Search_rview.setVisibility(View.GONE);
                          rvFrag.recyclerView.setVisibility(View.VISIBLE);
                          recyclerView =rvFrag.recyclerView;
                          recyclerView.setHasFixedSize(true);
                          recyclerView.setLayoutManager( new  LinearLayoutManager(getApplicationContext()));
                          mAdapter = new MyAdapter(Req_View_list);
                          recyclerView.setAdapter(mAdapter);

                         }
                    });
                    searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(hasFocus) {
                                //final ArrayList<Requests> SearchRequest = new ArrayList<>();
                                backButton.setVisibility(View.VISIBLE);
                                rvFrag.recyclerView.setVisibility(View.GONE);
                                recyclerView =rvFrag.Search_rview;
                                recyclerView.setVisibility(View.VISIBLE);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager( new  LinearLayoutManager(getApplicationContext()));
                                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        Log.d("Tagtest","Moving to detail 1");
                                        if(tab_bit==0){
                                            Requests tempObject = SearchRequestList.get(position);
                                        }else{
                                            Donations tempObject2 = SearchDonation.get(position);
                                        }

                                        Intent it = new Intent(getApplicationContext(),DetailActivity.class);
//                                        it.putExtra("Username",tempObject.Username);
//                                        it.putExtra("MobileNumber",tempObject.MobileNumber);
//                                        it.putExtra("Country",tempObject.Country);
//                                        it.putExtra("City",tempObject.City);
//                                        it.putExtra("Email",tempObject.Email);
//                                        it.putExtra("Request_Desc",tempObject.Request_Desc);
//                                        it.putExtra("Reason",tempObject.Reason);
                                        Log.d("Tagtest","Moving to detail");
                                        startActivity(it);
                                    }
                                    @Override
                                    public void onLongClick(View view, int position) {}
                                }));
                            }
                        }
                    });
                    searchBar.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                        public void onTextChanged(CharSequence cs, int s, int b, int c) {
                            if(searchBar.getText().toString().equals("")){
                                Log.d("Tagtest","working here ");
                            }
                            else {
                                if(tab_bit==0){
                                    SearchRequestList.clear();
                                    Log.d("Tagtest","working here "+tab_bit);
                                    for (Requests temp : Req_View_list) {
                                        if(temp.Request_Desc.toLowerCase().contains(""+cs) || temp.Reason.contains(""+cs) ||
                                                temp.City.contains(""+cs) || temp.Username.contains(""+cs)){
                                            SearchRequestList.add(temp);
                                        }
                                    }
                                    mAdapter = new MyAdapter(SearchRequestList);
                                    recyclerView.setAdapter(mAdapter);
                                    //rvFrag.Search_rview.setVisibility(View.VISIBLE);
                                    Log.d("Tagtest","working here"+SearchRequestList.size());
                                }
                                else if(tab_bit==1){
                                    Log.d("Tagtest","working here "+tab_bit);
                                    SearchDonation.clear();
                                    for (Donations temp : Donate_View_list) {
                                        if((temp.Donate_Descr.toLowerCase()).contains(""+cs) || temp.Condition.contains(""+cs) ||
                                                temp.UserName.contains(""+cs)){
                                            SearchDonation.add(temp);
                                        }
                                    }
                                    myAdapterD = new MyAdapterD(SearchDonation);
                                    recyclerView.setAdapter(myAdapterD);
                                    Log.d("Tagtest","working here"+SearchDonation.size());
                                }
                            }
                        }
                        @Override
                        public void afterTextChanged(Editable s) { }
                    });
                }

                return true;
            case R.id.AboutBtn:
                Intent i = new Intent(this, AboutDeveloper.class);
                startActivity(i);

                Log.d("Tagtest","STRAtActivity...2");
                return true;
            case R.id.SaveBtn:
                Intent g = new Intent(getApplicationContext(),OfflineActivity.class);
                startActivity(g);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void ButtonClicked(String text) {
        if(text.equals("Donations")){
            tab_bit=1;
            BB_frag.RaiseBTN.setVisibility(View.GONE);
            BB_frag.DonateBTN.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(myAdapterD);
            progressSpinner.setVisibility(View.GONE);

        }
        else if(text.equals("Requests")){
            tab_bit=0;
            BB_frag.DonateBTN.setVisibility(View.GONE);
            BB_frag.RaiseBTN.setVisibility(View.VISIBLE);
            mAdapter = new MyAdapter(Req_View_list);
            recyclerView.setAdapter(mAdapter);
            progressSpinner.setVisibility(View.GONE);


        }
        else if(text.equals("RaiseRequest")){
            Log.d("Tagtest","Raising Request");
            Intent i = new Intent(getApplicationContext(),FormActivity.class);
            Log.d("Tagtest","Raising Request2");
            i.putExtra("Size#",lastReqId);
            i.putExtra("tab_bit",tab_bit);
            Log.d("Tagtest","Raising Request3");
            startActivity(i);

        }else if(text.equals("Donate")){
            Log.d("Tagtest","Raising Request");
            Intent i = new Intent(getApplicationContext(),FormActivity.class);
            Log.d("Tagtest","Raising Request2");
            i.putExtra("Size#",lastReqId);
            i.putExtra("tab_bit",tab_bit);
            Log.d("Tagtest","Raising Request3");
            startActivity(i);
        }
        else if(text.equals("fromStartFrag")){

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        try{
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }catch (Exception e){
            Log.e("Tagtest",""+e);
        }
    }
}
