package com.example.sth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.sth.adapters.LawAdapter;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    //-----Declaring Variables-----
    RecyclerView recyclerView;
    ImageView noresult;
    List<LawContentActivity> lawList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        recyclerView = findViewById(R.id.recyclerView);
        initData();
        setRecycleView();
    }

    //This function set recycler view
    private void setRecycleView() {
        LawAdapter lawAdapter = new LawAdapter(lawList);
        recyclerView.setAdapter(lawAdapter);
        recyclerView.setHasFixedSize(true);
    }

    //This Function get Laws and its detail from string.xml
    private void initData() {
        Resources res = getResources();
        String[] lawListGroup = res.getStringArray(R.array.lawListGroup);
        String[] lawListChild = res.getStringArray(R.array.lawListChild);
        lawList = new ArrayList<>();
        Intent intent = getIntent();
        String[] str = intent.getStringArrayExtra("lawkey");

        noresult = findViewById(R.id.noresult);
        noresult.setVisibility(View.VISIBLE);
        for(int i = 0; i < str.length; i++)
        {
            noresult.setVisibility(View.INVISIBLE);
            int s = Integer.parseInt(str[i]);
            lawList.add(new LawContentActivity(lawListGroup[s],lawListChild[s]));
        }
    }

    //This Functions handle Back Press Button
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),DrawerActivity.class);
        startActivity(i);
    }
}