package com.example.sth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sth.adapters.CityAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CityNgosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    CityAdapter adapter;
    List<CityNgosContent> list;
    String sngo,scontent,smob,semail,sadd,city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citi_ngos);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new CityAdapter(this,list);
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        city = intent.getStringExtra("city");

        switch(city)
        {
            case "Arunachal":
                arunachalData();
                break;
            case "Andhra":
                andhraData();
                break;
            case "Assam":
                assamData();
                break;
            case "Bihar":
                biharData();
                break;
            case "Chattis":
                chattisData();
                break;
            case "Goa":
                goaData();
                break;
            case "Guj":
                gujData();
                break;
            case "Haryana":
                haryanaData();
                break;
            case "Himachal":
                himachalData();
                break;
            case "Jhar":
                jharData();
                break;
            case "Karnataka":
                karnatakaData();
                break;
            case "Kerala":
                keralaData();
                break;
            case "Madhya":
                madhyaData();
                break;
            case "Maharash":
                maharashData();
                break;
            case "Mani":
                maniData();
                break;
            case "Megha":
                meghaData();
                break;
            case "Mizo":
                mizoData();
                break;
            case "Naga":
                nagaData();
                break;
            case "Odisha":
                odishaData();
                break;
            case "Punjab":
                punjabData();
                break;
            case "Rajas":
                rajasData();
                break;
            case "Sikkim":
                sikkimData();
                break;
            case "Tn":
                tnData();
                break;
            case "Telan":
                telanData();
                break;
            case "Tripura":
                tripuraData();
                break;
            case "Up":
                upData();
                break;
            case "Uttra":
                uttraData();
                break;
            case "Wb":
                wbData();
                break;
            default:
                Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void wbData() {
        setTitle("West Bengal");
        database = FirebaseDatabase.getInstance().getReference("Bengal");
        loadData(database);
    }

    private void uttraData() {
        setTitle("Uttarakhand");
        database = FirebaseDatabase.getInstance().getReference("Uttarakhand");
        loadData(database);
    }

    private void upData() {
        setTitle("Uttar Pradesh");
        database = FirebaseDatabase.getInstance().getReference("Uttar");
        loadData(database);
    }

    private void tripuraData() {
        setTitle("Tripura");
        database = FirebaseDatabase.getInstance().getReference("Tripura");
        loadData(database);
    }

    private void telanData() {
        setTitle("Telangana");
        database = FirebaseDatabase.getInstance().getReference("Telangana");
        loadData(database);
    }

    private void tnData() {
        setTitle("Tamil Nadu");
        database = FirebaseDatabase.getInstance().getReference("Tamil");
        loadData(database);
    }

    private void sikkimData() {
        setTitle("Sikkim");
        database = FirebaseDatabase.getInstance().getReference("Sikkim");
        loadData(database);
    }

    private void rajasData() {
        setTitle("Rajasthan");
        database = FirebaseDatabase.getInstance().getReference("Rajasthan");
        loadData(database);
    }

    private void punjabData() {
        setTitle("Punjab");
        database = FirebaseDatabase.getInstance().getReference("Punjab");
        loadData(database);
    }

    private void odishaData() {
        setTitle("Odisha");
        database = FirebaseDatabase.getInstance().getReference("Odisha");
        loadData(database);
    }

    private void nagaData() {
        setTitle("Nagaland");
        database = FirebaseDatabase.getInstance().getReference("Nagaland");
        loadData(database);
    }

    private void mizoData() {
        setTitle("Mizoram");
        database = FirebaseDatabase.getInstance().getReference("Mizoram");
        loadData(database);
    }

    private void meghaData() {
        setTitle("Meghalaya");
        database = FirebaseDatabase.getInstance().getReference("Meghalaya");
        loadData(database);
    }

    private void maniData() {
        setTitle("Manipur");
        database = FirebaseDatabase.getInstance().getReference("Manipur");
        loadData(database);
    }

    private void maharashData() {
        setTitle("Maharashtra");
        database = FirebaseDatabase.getInstance().getReference("Maharashtra");
        loadData(database);
    }

    private void madhyaData() {
        setTitle("Madhya Pradesh");
        database = FirebaseDatabase.getInstance().getReference("Madhya");
        loadData(database);
    }

    private void keralaData() {
        setTitle("Kerala");
        database = FirebaseDatabase.getInstance().getReference("Kerala");
        loadData(database);
    }

    private void karnatakaData() {
        setTitle("Karnataka");
        database = FirebaseDatabase.getInstance().getReference("Karnataka");
        loadData(database);
    }

    private void jharData() {
        setTitle("Jharkhand");
        database = FirebaseDatabase.getInstance().getReference("Jharkhand");
        loadData(database);
    }

    private void himachalData() {
        setTitle("Himachal Pradesh");
        database = FirebaseDatabase.getInstance().getReference("Himachal");
        loadData(database);
    }

    private void haryanaData() {
        setTitle("Haryana");
        database = FirebaseDatabase.getInstance().getReference("Haryana");
        loadData(database);
    }

    private void gujData() {
        setTitle("Gujarat");
        database = FirebaseDatabase.getInstance().getReference("Gujarat");
        loadData(database);
    }

    private void goaData() {
        setTitle("Goa");
        database = FirebaseDatabase.getInstance().getReference("Goa");
        loadData(database);
    }

    private void chattisData() {
        setTitle("Chhattisgarh");
        database = FirebaseDatabase.getInstance().getReference("Chhattisgarh");
        loadData(database);
    }

    private void biharData() {
        setTitle("Bihar");
        database = FirebaseDatabase.getInstance().getReference("Bihar");
        loadData(database);
    }

    private void assamData() {
        setTitle("Assam");
        database = FirebaseDatabase.getInstance().getReference("Assam");
        loadData(database);
    }

    private void arunachalData() {
        setTitle("Arunachal Pradesh");
        database = FirebaseDatabase.getInstance().getReference("Arunachal");
        loadData(database);
    }

    private void andhraData() {
        setTitle("Andhra Pradesh");
        database = FirebaseDatabase.getInstance().getReference("Andhra");
        loadData(database);
    }

    private void loadData(DatabaseReference db) {
        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    sngo = dataSnapshot.child("ngo").getValue().toString();
                    sadd = dataSnapshot.child("add").getValue().toString();
                    scontent = dataSnapshot.child("content").getValue().toString();
                    smob = dataSnapshot.child("mob").getValue().toString();
                    semail = dataSnapshot.child("email").getValue().toString();
                    list.add(new CityNgosContent(sngo,sadd,scontent,smob,semail));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
