package com.example.sth;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment{

    TextView uname, email, mno;

    FirebaseUser user;
    DatabaseReference reff;
    FirebaseAuth auth;

    String uID;
    CircularImageView profileDP;

    private ProfileViewModel mViewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        uname = getActivity().findViewById(R.id.uname);
        email = getActivity().findViewById(R.id.email);
        mno = getActivity().findViewById(R.id.mno);
        profileDP = getActivity().findViewById(R.id.profile_pic);


        user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference("Users");
        uID = user.getUid();

        //This function is use to handle activity which is use to change profile photo
        profileDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(),EditProfileActivity.class));
            }
        });
        getUserInfo();
    }

    //This Function is used to get user info from firebase
    private void getUserInfo() {
        reff.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0)
                {
                    uname.setText(snapshot.child("username").getValue().toString());
                    email.setText(snapshot.child("email").getValue().toString());
                    mno.setText(snapshot.child("mobno").getValue().toString());

                    if (snapshot.hasChild("image"))
                    {
                        String image = snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profileDP);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}