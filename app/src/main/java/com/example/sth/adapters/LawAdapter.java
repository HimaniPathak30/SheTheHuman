package com.example.sth.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sth.LawContentActivity;
import com.example.sth.R;

import java.util.List;

public class LawAdapter extends RecyclerView.Adapter<LawAdapter.LawVH> {

    //-----Declaring List for Content-----
    List<LawContentActivity> lawList;

    public LawAdapter(List<LawContentActivity> lawList) {
        this.lawList = lawList;
    }

    @NonNull
    @Override
    public LawVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.law_content,parent,false);
        return new LawVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LawVH holder, int position) {

        LawContentActivity lawContentActivity = lawList.get(position);
        holder.lawName.setText(lawContentActivity.getLawName());
        holder.lawDetails.setText(lawContentActivity.getLawDetails());

        boolean isExpandable = lawList.get(position).isExpandable();
        holder.expandableView.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return lawList.size();
    }

    public class LawVH extends RecyclerView.ViewHolder {

        //-----Initializing Variables-----

        TextView lawName, lawDetails;
        LinearLayout linearLayout;
        RelativeLayout expandableView;

        public LawVH(@NonNull View itemView) {
            super(itemView);

            //-----Assigning Variables-----
            lawName = itemView.findViewById(R.id.law_name);
            lawDetails = itemView.findViewById(R.id.law_details);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableView = itemView.findViewById(R.id.expandable_layout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LawContentActivity lawContentActivity = lawList.get(getAdapterPosition());
                    lawContentActivity.setExpandable(!lawContentActivity.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}