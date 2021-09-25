package com.example.sth;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;

import com.example.sth.adapters.LawAdapter;

import java.util.ArrayList;
import java.util.List;

public class LawActivity extends AppCompatActivity {

    //-----Declaring Variables-----
    RecyclerView recyclerView;
    List<LawContentActivity> lawList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.law);

        recyclerView = findViewById(R.id.recyclerView);
        initData();
        setRecycleView();

    }

    //This Function set recycler view
    private void setRecycleView() {
        LawAdapter lawAdapter = new LawAdapter(lawList);
        recyclerView.setAdapter(lawAdapter);
        recyclerView.setHasFixedSize(true);
    }

    //This function get data of Law from string.xml file
    private void initData() {
        Resources res = getResources();
        String[] lawListGroup = res.getStringArray(R.array.lawListGroup);
        String[] lawListChild = res.getStringArray(R.array.lawListChild);
        lawList = new ArrayList<>();
        for (int i = 0; i < lawListGroup.length; i++)
        {
            lawList.add(new LawContentActivity(lawListGroup[i],lawListChild[i]));
        }
    }
}