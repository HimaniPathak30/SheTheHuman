package com.example.sth.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sth.ChatContentShow;
import com.example.sth.CommentActivity;
import com.example.sth.MyPostContent;
import com.example.sth.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.MyPostVH>{

    Context context;
    List<MyPostContent> postList;

    public MyPostAdapter(Context context, List<MyPostContent> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyPostAdapter.MyPostVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_post_content,parent,false);
        return new MyPostAdapter.MyPostVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostAdapter.MyPostVH holder, int position) {
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

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pd = new ProgressDialog(view.getContext());
                pd.setMessage("Deleting...");

                Query fquery = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("pid").equalTo(spid);
                fquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren())
                        {
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(view.getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("postId",spid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class MyPostVH extends RecyclerView.ViewHolder {
        TextView uname,time,msg,comment_count;
        LinearLayout comment;
        ImageView delete;
        public MyPostVH(@NonNull View itemView) {
            super(itemView);
            uname = itemView.findViewById(R.id.uname);
            time = itemView.findViewById(R.id.time);
            msg = itemView.findViewById(R.id.msg);
            comment_count = itemView.findViewById(R.id.comment_count);
            comment = itemView.findViewById(R.id.comment);
            delete = itemView.findViewById(R.id.delete);

        }
    }
}
