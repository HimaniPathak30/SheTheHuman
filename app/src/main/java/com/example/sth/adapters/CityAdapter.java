package com.example.sth.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sth.R;
import com.example.sth.CityNgosContent;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ArunachalVH> {

    Context context;
    List<CityNgosContent> list;

    public CityAdapter(Context context, List<CityNgosContent> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ArunachalVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.city_ngos_content,parent,false);
        return new ArunachalVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ArunachalVH holder, int position) {
        CityNgosContent cityNgosContent = list.get(position);
        holder.ngo.setText(cityNgosContent.getNgo());
        holder.add.setText(cityNgosContent.getAdd());
        holder.content.setText(cityNgosContent.getContent());
        holder.mob.setText(cityNgosContent.getMob());
        holder.email.setText(cityNgosContent.getEmail());

        boolean isExpandable = list.get(position).isExpandable();
        holder.expandableView.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ArunachalVH extends RecyclerView.ViewHolder {

        TextView ngo,add,content,mob,email;
        LinearLayout linearLayout;
        RelativeLayout expandableView;
        public ArunachalVH(@NonNull View itemView) {
            super(itemView);
            ngo = itemView.findViewById(R.id.ngo);
            add = itemView.findViewById(R.id.ngo_add);
            content = itemView.findViewById(R.id.ngo_content);
            mob = itemView.findViewById(R.id.ngo_mob);
            email = itemView.findViewById(R.id.ngo_email);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableView = itemView.findViewById(R.id.expandable_layout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CityNgosContent cityNgosContent = list.get(getAdapterPosition());
                    cityNgosContent.setExpandable(!cityNgosContent.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
