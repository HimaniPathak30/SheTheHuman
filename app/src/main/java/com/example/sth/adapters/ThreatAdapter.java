package com.example.sth.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sth.R;
import com.example.sth.ThreatContentActivity;

import java.util.List;

public class ThreatAdapter extends RecyclerView.Adapter<ThreatAdapter.ThreatVH> {
    //-----Declaring List for Content-----
    List<ThreatContentActivity> threatList;

    public ThreatAdapter(List<ThreatContentActivity> threatList) {
        this.threatList = threatList;
    }

    @NonNull
    @Override
    public ThreatVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.threat_content,parent,false);
        return new ThreatVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThreatAdapter.ThreatVH holder, int position) {
        ThreatContentActivity threatContentActivity = threatList.get(position);
        holder.threatQue.setText(threatContentActivity.getThreatQue());
        holder.threatAns.setText(threatContentActivity.getThreatAns());

        boolean isExpandable = threatList.get(position).isExpandable();
        holder.expandableViewThreat.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return threatList.size();
    }

    public class ThreatVH extends RecyclerView.ViewHolder {
        //-----Initializing Variables-----

        TextView threatQue,threatAns;
        LinearLayout linearLayoutThreat;
        RelativeLayout expandableViewThreat;

        public ThreatVH(@NonNull View itemView) {
            super(itemView);

            //-----Assigning Variables-----
            threatQue = itemView.findViewById(R.id.threatQue);
            threatAns = itemView.findViewById(R.id.threatAns);
            linearLayoutThreat = itemView.findViewById(R.id.linear_layoutThreat);
            expandableViewThreat = itemView.findViewById(R.id.expandable_layoutThreat);

            linearLayoutThreat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ThreatContentActivity threatContentActivity = threatList.get(getAdapterPosition());
                    threatContentActivity.setExpandable(!threatContentActivity.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
