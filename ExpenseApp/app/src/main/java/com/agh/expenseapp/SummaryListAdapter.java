package com.agh.expenseapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class SummaryListAdapter extends RecyclerView.Adapter<SummaryListAdapter.ViewHolder> {

    private List<SummaryListData> listdata;

    public SummaryListAdapter(List<SummaryListData> listdata) {
        this.listdata = listdata;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SummaryListData summaryListData = listdata.get(position);
        holder.textView.setText(listdata.get(position).getValue());
        holder.textView2.setText(listdata.get(position).getPurpose());
        holder.imageView.setImageResource(listdata.get(position).getImgId());
        if(holder.textView2.getText().equals("Incoming"))
            holder.textView2.setTextColor(Color.parseColor("#117243"));
        else
            holder.textView2.setTextColor(Color.parseColor("#922D25"));
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView textView2;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.list_view_value);
            this.textView2 = (TextView) itemView.findViewById(R.id.list_view_purpose);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}