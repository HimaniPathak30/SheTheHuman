package com.example.sth.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sth.CommentsShow;
import com.example.sth.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommentShowAdapter extends RecyclerView.Adapter<CommentShowAdapter.CommentVH>{
    Context context;
    List<CommentsShow> commentsShowList;

    public CommentShowAdapter(Context context, List<CommentsShow> commentsShowList) {
        this.context = context;
        this.commentsShowList = commentsShowList;
    }

    @NonNull
    @Override
    public CommentShowAdapter.CommentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_show,parent,false);
        return new CommentShowAdapter.CommentVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentShowAdapter.CommentVH holder, int position) {
        String uid = commentsShowList.get(position).getUid();
        String name = commentsShowList.get(position).getuName();
        String email = commentsShowList.get(position).getuEmail();
        String cid = commentsShowList.get(position).getcId();
        String comment = commentsShowList.get(position).getComment();
        String timestamp = commentsShowList.get(position).getTimestamp();

        //Converting timestamp to dd/mm/yyyy hh:mm am/pm
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(timestamp));
        String ptimestamp = DateFormat.format("dd/MM/yyyy hh:mm aa",calendar).toString();

        holder.nameTv.setText(name);
        holder.commentTv.setText(comment);
        holder.timeTv.setText(ptimestamp);

    }

    @Override
    public int getItemCount() {
        return commentsShowList.size();
    }

    public class CommentVH extends RecyclerView.ViewHolder {
        TextView nameTv,commentTv,timeTv;
        public CommentVH(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.uname);
            commentTv = itemView.findViewById(R.id.actual_comment);
            timeTv = itemView.findViewById(R.id.time);
        }
    }
}
