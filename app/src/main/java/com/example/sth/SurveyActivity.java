package com.example.sth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class SurveyActivity extends AppCompatActivity {
    TextView tv;
    Button submitbutton, quitbutton;
    RadioGroup radio_g;
    RadioButton rb1,rb2;

    String questions[] = {
            "Have you ever experienced street harassment?",
            "Have you ever experienced domestic violence and scared to complain because of societal pressure?",
            "Have you ever experienced sexual harassment at workplace/school/college?",
            "Have you ever experienced Sexual/Mental Harassment with Family?",
            "Have you ever experienced Sexual/Mental Harassment by husband or in-laws?",
            "Have you ever experienced harassment on social media?",
            "Do you know anyone who is victim of asking for dowry?",
            "Do you know anyone who is victim of Rape?",
            "Do you know anyone who is victim of Acid Attack?",
            "Do you know anyone whose in-law killed her?"
    };
    int flag = 0, harass = 0, cruelty = 0, death = 0, acid = 0, rape = 0, dowry = 0;
    ArrayList<String> lawarr = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey);

        submitbutton=(Button)findViewById(R.id.nextque);
        quitbutton=(Button)findViewById(R.id.buttonquit);
        tv=(TextView) findViewById(R.id.tvque);
        radio_g=(RadioGroup)findViewById(R.id.answersgrp);
        rb1=(RadioButton)findViewById(R.id.yes);
        rb2=(RadioButton)findViewById(R.id.no);
        tv.setText(questions[flag]);

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radio_g.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();
                if(flag == 0 || flag == 2 ||flag ==3 || flag == 5 )
                {
                    if(ansText.equals("Yes")) {
                        harass =1;
                    }
                }
                if(flag == 1 || flag == 4)
                {
                    if(ansText.equals("Yes")) {
                        cruelty =1;
                    }
                }
                if(flag == 6)
                {
                    if(ansText.equals("Yes")) {
                        dowry =1;
                    }
                }
                if(flag == 7)
                {
                    if(ansText.equals("Yes")) {
                        rape =1;
                    }
                }
                if(flag == 8)
                {
                    if(ansText.equals("Yes")) {
                        acid =1;
                    }
                }
                if(flag == 9)
                {
                    if(ansText.equals("Yes")) {
                        death =1;
                    }
                }

                flag++;

                if(flag<questions.length)
                {
                    tv.setText(questions[flag]);
                }
                else
                {
                    if(harass==1){lawarr.add("7");}
                    if(cruelty==1){lawarr.add("5");}
                    if(acid==1){lawarr.add("0");}
                    if(rape==1){lawarr.add("1");}
                    if(death==1){lawarr.add("3");}
                    if(dowry==1){lawarr.add("4");}
                    String[] send = new String[lawarr.size()];
                    lawarr.toArray(send);
                    Intent in = new Intent(getApplicationContext(),ResultActivity.class);
                    in.putExtra("lawkey",send);
                    startActivity(in);
                }
                radio_g.clearCheck();
            }
        });

        quitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DrawerActivity.class);
                startActivity(intent);
            }
        });
    }
}