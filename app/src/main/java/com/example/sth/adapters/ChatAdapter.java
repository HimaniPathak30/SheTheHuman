package com.example.sth.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sth.ChatContentShow;
import com.example.sth.CommentActivity;
import com.example.sth.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.zip.DataFormatException;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatVH>{

    Context context;
    List<ChatContentShow> postList;

    public ChatAdapter(Context context, List<ChatContentShow> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public ChatAdapter.ChatVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_content,parent,false);
        return new ChatVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatVH holder, int position) {
        String suid = postList.get(position).getUid();
        String suname = postList.get(position).getUsername();
        String semail = postList.get(position).getEmail();
        String spid = postList.get(position).getPid();
        String sptime = postList.get(position).getPtime();
        String spmsg = postList.get(position).getPmsg();
        String pComments = postList.get(position).getpComments();

        //Converting timestamp to dd/mm/yyyy hh:mm am/pm
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(sptime));
        String ptimestamp = DateFormat.format("dd/MM/yyyy hh:mm aa",calendar).toString();

        holder.uname.setText(suname);
        holder.time.setText(ptimestamp);
        holder.msg.setText(spmsg);
        holder.comment_count.setText(pComments + " Comments");

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("postId",spid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ChatVH extends RecyclerView.ViewHolder {

        TextView uname,time,msg,comment_count;
        LinearLayout comment;
        public ChatVH(@NonNull View itemView) {
            super(itemView);

            uname = itemView.findViewById(R.id.uname);
            time = itemView.findViewById(R.id.time);
            msg = itemView.findViewById(R.id.msg);
            comment_count = itemView.findViewById(R.id.comment_count);
            comment = itemView.findViewById(R.id.comment);
        }
    }
}
