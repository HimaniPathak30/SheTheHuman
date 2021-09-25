package com.example.sth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sth.adapters.ContactAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddContactsActivity extends AppCompatActivity {

    Button add;

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    EditText p_name, p_number;
    Button cancel, save;

    RecyclerView recyclerView;
    List<AddContactData> contactList;
    ContactAdapter adapter;

    //Upload Data
    String uname, unumber, uid;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contacts);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUserStatus();

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //show new at first
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        //init postlist
        contactList = new ArrayList<>();
        loadPosts();

        add = findViewById(R.id.addBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContactData();
            }
        });
    }

    //This function get emergency contact detail from firebase
    private void loadPosts() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Contacts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contactList.clear();
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    if(ds.child("uid").getValue().toString().equals(uid))
                    {
                        AddContactData addContactData = ds.getValue(AddContactData.class);
                        contactList.add(addContactData);
                        adapter = new ContactAdapter(getApplicationContext(),contactList);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), ""+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //This function is use to collect emergency contact details

    public void getContactData() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopup = getLayoutInflater().inflate(R.layout.popup,null);

        p_name = contactPopup.findViewById(R.id.nameEt);
        p_number = contactPopup.findViewById(R.id.numberEt);

        cancel = contactPopup.findViewById(R.id.cancelBtn);
        save = contactPopup.findViewById(R.id.saveBtn);

        dialogBuilder.setView(contactPopup);
        dialog = dialogBuilder.create();
        dialog.show();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname = p_name.getText().toString().trim();
                unumber = p_number.getText().toString().trim();
                if (uname.equals(""))
                {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_SHORT).show();
                }
                if (unumber.equals(""))
                {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Enter Number",Toast.LENGTH_SHORT).show();
                }
                if (unumber.length() != 10)
                {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Enter Valid Number",Toast.LENGTH_SHORT).show();
                }
                if (!uname.equals("") && !unumber.equals("") && unumber.length() == 10)
                {
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    HashMap<Object, String> hashMap = new HashMap<>();
                    hashMap.put("name",uname);
                    hashMap.put("number",unumber);
                    hashMap.put("uid",uid);
                    hashMap.put("con_id",timestamp);

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Contacts");
                    ref.child(timestamp).setValue(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>(){
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Contact Saved", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Contact Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
            uid = user.getUid();
        }
        else
        {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
    }

}