package com.example.sth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;

import com.example.sth.adapters.ThreatAdapter;

import java.util.ArrayList;
import java.util.List;

public class ThreatActivity extends AppCompatActivity {

    RecyclerView recyclerViewThreat;
    List<ThreatContentActivity> threatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threat);
        recyclerViewThreat = findViewById(R.id.recyclerViewThreat);
        initData();
        setRecycleView();
    }

    //This Function sets recycler view
    private void setRecycleView() {
        ThreatAdapter threatAdapter = new ThreatAdapter(threatList);
        recyclerViewThreat.setAdapter(threatAdapter);
        recyclerViewThreat.setHasFixedSize(true);
    }

    //This Function get Threat Que and Answer from string.xml
    private void initData() {
        Resources res = getResources();
        String[] threatQue = res.getStringArray(R.array.threatQueStr);
        String[] threatAns = res.getStringArray(R.array.threatAnsStr);
        threatList = new ArrayList<>();
        for (int i = 0; i < threatQue.length; i++)
        {
            threatList.add(new ThreatContentActivity(threatQue[i], threatAns[i]));
        }
    }
}