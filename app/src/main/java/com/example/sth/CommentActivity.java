package com.example.sth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.*;

import com.example.sth.adapters.CommentShowAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CommentActivity extends AppCompatActivity {

    //Views
    TextView uname,time,msg,comment_count;

    //Comment
    EditText comment_text;
    ImageView send_button;

    DatabaseReference reff;
    FirebaseUser user;

    //Details
    String myUid,myEmail,myUname,pId;

    ProgressDialog pd;

    RecyclerView recyclerView;
    List<CommentsShow> commentsShowList;
    CommentShowAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);

        uname = findViewById(R.id.uname);
        time = findViewById(R.id.time);
        msg = findViewById(R.id.msg);

        comment_text = findViewById(R.id.comment_text);
        comment_count = findViewById(R.id.comment_count);
        send_button = findViewById(R.id.send_button);

        recyclerView = findViewById(R.id.recyclerView);

        //get post id
        Intent intent = getIntent();
        pId = intent.getStringExtra("postId");
        user = FirebaseAuth.getInstance().getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference("Users");
        myUid = user.getUid();
        reff.child(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                myUname = snapshot.child("username").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
        loadPostInfo();
        checkUserStatus();
        loadUserInfo();
        loadComments();
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postComment();
            }
        });


    }

    //This function get Comments from firebase
    private void loadComments() {
        LinearLayoutManager layoutManager = new LinearLayoutManager((getApplicationContext()));
        recyclerView.setLayoutManager(layoutManager);

        commentsShowList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts").child(pId).child("Comments");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentsShowList.clear();
                for (DataSnapshot ds : snapshot.getChildren())
                {
                        CommentsShow commentsShow = ds.getValue(CommentsShow.class);
                        commentsShowList.add(commentsShow);
                        adapter = new CommentShowAdapter(getApplicationContext(), commentsShowList);
                        recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //ThisFunction get Post Information from firebase
    private void loadPostInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        Query query = ref.orderByChild("pid").equalTo(pId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    String pmsg = "" + ds.child("pmsg").getValue().toString();
                    String username = "" + ds.child("username").getValue().toString();
                    String ptimestamp = "" + ds.child("ptime").getValue().toString();
                    String uid = "" + ds.child("uid").getValue().toString();
                    String uemail = "" + ds.child("email").getValue().toString();
                    String commentCount = "" + ds.child("pComments").getValue();

                    //Converting timestamp to dd/mm/yyyy hh:mm am/pm
                    Calendar calendar = Calendar.getInstance(Locale.getDefault());
                    calendar.setTimeInMillis(Long.parseLong(ptimestamp));
                    String ptimes = DateFormat.format("dd/MM/yyyy hh:mm aa",calendar).toString();

                    uname.setText(username);
                    msg.setText(pmsg);
                    time.setText(ptimes);
                    comment_count.setText(commentCount + " Comments");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void checkUserStatus() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
        {
            myEmail = user.getEmail();
            myUid = user.getUid();
        }
        else
        {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
    }

    //This Function get user Information from Firebase
    public void loadUserInfo() {
        Query myRef = FirebaseDatabase.getInstance().getReference("Users");
        myRef.orderByChild("uid").equalTo(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    myUname = "" + ds.child("username").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //This Function posts Comments into Firebase
    public void postComment() {
        pd = new ProgressDialog(this);
        pd.setMessage("Posting Comment...");

        String comment = comment_text.getText().toString().trim();
        if(TextUtils.isEmpty(comment))
        {
            Toast.makeText(getApplicationContext(), "Comment is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        String timestamp = String.valueOf(System.currentTimeMillis());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts").child(pId).child("Comments");

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("cId",timestamp);
        hashMap.put("comment",comment);
        hashMap.put("timestamp",timestamp);
        hashMap.put("uid",myUid);
        hashMap.put("uEmail",myEmail);
        hashMap.put("uName",myUname);

        ref.child(String.valueOf(timestamp)).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Commented Successfully", Toast.LENGTH_SHORT).show();
                        comment_text.setText("");
                        updateCommentCount();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //This Function update comments counts
    boolean mProcessComment = false;
    private void updateCommentCount() {
        mProcessComment = true;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts").child(pId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(mProcessComment)
                {
                    String comments = "" + snapshot.child("pComments").getValue();
                    int newCommentVal = Integer.parseInt(comments) + 1;
                    ref.child("pComments").setValue(""+newCommentVal);
                    mProcessComment = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}