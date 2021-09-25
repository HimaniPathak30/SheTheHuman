package com.example.sth;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sth.adapters.ChatAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatForumFragment extends Fragment {

    private ChatForumViewModel mViewModel;
    FloatingActionButton my_post;
    EditText message_text;
    ImageView send_button;

    //User Info
    String email,uname,uid;

    DatabaseReference userdbreff;
    FirebaseAuth firebaseAuth;

    //Progress Bar
    ProgressDialog pd;

    //Recycler View
    RecyclerView recyclerView;
    List<ChatContentShow> postList;
    ChatAdapter adapter;

    public static ChatForumFragment newInstance() {
        return new ChatForumFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.chat_forum, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //show new at first
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        //init postlist
        postList = new ArrayList<>();
        loadPosts();

        return v;
    }

    //This function get all data from firebase
    private void loadPosts() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    ChatContentShow chatContent = ds.getValue(ChatContentShow.class);
                    postList.add(chatContent);
                    adapter =new ChatAdapter(getActivity(),postList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity().getApplicationContext(), ""+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChatForumViewModel.class);
        // TODO: Use the ViewModel

        message_text = getActivity().findViewById(R.id.message_text);
        send_button = getActivity().findViewById(R.id.send_button);
        my_post = getActivity().findViewById(R.id.my_post);
        my_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(),MyPostActivity.class));
            }
        });
        pd = new ProgressDialog(getContext());

        firebaseAuth = FirebaseAuth.getInstance();
        checkUserStatus();

        userdbreff = FirebaseDatabase.getInstance().getReference("Users");
        Query query = userdbreff.orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    uname = "" + ds.child("username").getValue();
                    email = "" + ds.child("email").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Send button Handling
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = message_text.getText().toString().trim();

                if(TextUtils.isEmpty(msg))
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Please Write Message", Toast.LENGTH_LONG).show();
                    return;
                }
                uploadData(msg);
            }
        });
    }

    //This function stores all data into firebase
    private void uploadData(String msg) {
        pd.setMessage("Uploading Post...");
        pd.show();

        String timestamp = String.valueOf(System.currentTimeMillis());
        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put("uid",uid);
        hashMap.put("email", email);
        hashMap.put("username",uname);
        hashMap.put("pid",timestamp);
        hashMap.put("pmsg",msg);
        hashMap.put("ptime",timestamp);
        hashMap.put("pComments","0");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        ref.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(), "Post Uploaded", Toast.LENGTH_LONG).show();
                        message_text.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        checkUserStatus();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkUserStatus();
    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null)
        {
            email = user.getEmail();
            uid = user.getUid();
        }
        else
        {
            startActivity(new Intent(getActivity().getApplicationContext(),LoginActivity.class));
            getActivity().finish();
        }
    }

}