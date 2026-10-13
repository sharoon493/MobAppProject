package com.example.sharo.project;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapterD extends RecyclerView.Adapter<MyAdapterD.MyViewHolder> {

    private ArrayList<Donations> mDataset;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView, mTextView2,datetime;
        public ImageView imageView;
        public MyViewHolder(LinearLayout v) {
            super(v);
            mTextView = v.findViewById(R.id.PersonName);
            mTextView2 = v.findViewById(R.id.ReqDesc);
            datetime = v.findViewById(R.id.DateTime);
            imageView = v.findViewById(R.id.Donate_viewImage);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterD(ArrayList myDataset) {
        this.mDataset = myDataset;
    }

    @NonNull
    @Override
    public MyAdapterD.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.donate_view, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).UserName);
        holder.mTextView2.setText(mDataset.get(position).Donate_Descr);
        holder.datetime.setText(mDataset.get(position).date);
        Glide.with(holder.imageView).load(mDataset.get(position).Image).into(holder.imageView);



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

