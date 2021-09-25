package com.example.sth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SafetyActivity extends AppCompatActivity {

    TextView safetyTxt,safetySocial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safety);

        safetyTxt = findViewById(R.id.safetyTxt);
        safetySocial = findViewById(R.id.safetySocial);

        //Setting safety Text from string.xml
        safetyTxt.setText(getResources().getString(R.string.safetyStr));
        safetySocial.setText(getResources().getString(R.string.socialStr));
    }
}