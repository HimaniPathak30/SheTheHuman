package com.example.sth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CityActivity extends AppCompatActivity {

    CardView arunachalp,andhrap,assam,bihar,chattis,goa,guj,haryana,hp,jharkhand,karnataka,kerala,mp,maharashtra,manipur,
            meghalaya,mizoram,nagaland,odisha,punjab,rajasthan,sikkim,tn,telangana,tripura,up,uttarakhand,wb;

    String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city);

        arunachalp = findViewById(R.id.arunachal_pradesh);
        andhrap = findViewById(R.id.andhra_pradesh);
        assam = findViewById(R.id.assam);
        bihar = findViewById(R.id.bihar);
        chattis = findViewById(R.id.chhattisgarh);
        goa = findViewById(R.id.goa);
        guj = findViewById(R.id.gujarat);
        haryana = findViewById(R.id.haryana);
        hp = findViewById(R.id.himachal_pradesh);
        jharkhand = findViewById(R.id.jharkhand);
        karnataka = findViewById(R.id.karnataka);
        kerala = findViewById(R.id.kerala);
        mp = findViewById(R.id.madhya_pradesh);
        maharashtra = findViewById(R.id.maharashtra);
        manipur = findViewById(R.id.manipur);
        meghalaya = findViewById(R.id.meghalaya);
        mizoram = findViewById(R.id.mizoram);
        nagaland = findViewById(R.id.nagaland);
        odisha = findViewById(R.id.odisha);
        punjab = findViewById(R.id.punjab);
        rajasthan = findViewById(R.id.rajasthan);
        sikkim = findViewById(R.id.sikkim);
        tn = findViewById(R.id.tamil_nadu);
        telangana = findViewById(R.id.telangana);
        tripura = findViewById(R.id.tripura);
        up = findViewById(R.id.uttar_pradesh);
        uttarakhand = findViewById(R.id.uttarakhand);
        wb = findViewById(R.id.west_bengal);

        arunachalp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Arunachal";
                intentdata(city);
            }
        });
        andhrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Andhra";
                intentdata(city);
            }
        });
        assam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Assam";
                intentdata(city);
            }
        });
        bihar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Bihar";
                intentdata(city);
            }
        });
        chattis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Chattis";
                intentdata(city);
            }
        });
        goa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Goa";
                intentdata(city);
            }
        });
        guj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Guj";
                intentdata(city);
            }
        });
        haryana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Haryana";
                intentdata(city);
            }
        });
        hp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Himachal";
                intentdata(city);
            }
        });
        jharkhand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Jhar";
                intentdata(city);
            }
        });
        karnataka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Karnataka";
                intentdata(city);
            }
        });
        kerala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Kerala";
                intentdata(city);
            }
        });
        mp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Madhya";
                intentdata(city);
            }
        });
        maharashtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Maharash";
                intentdata(city);
            }
        });
        manipur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Mani";
                intentdata(city);
            }
        });
        meghalaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Megha";
                intentdata(city);
            }
        });
        mizoram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Mizo";
                intentdata(city);
            }
        });
        nagaland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Naga";
                intentdata(city);
            }
        });
        odisha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Odisha";
                intentdata(city);
            }
        });
        punjab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Punjab";
                intentdata(city);
            }
        });
        rajasthan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Rajas";
                intentdata(city);
            }
        });
        sikkim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Sikkim";
                intentdata(city);
            }
        });
        tn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Tn";
                intentdata(city);
            }
        });
        telangana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Telan";
                intentdata(city);
            }
        });
        tripura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Tripura";
                intentdata(city);
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Up";
                intentdata(city);
            }
        });
        uttarakhand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Uttra";
                intentdata(city);
            }
        });
        wb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Wb";
                intentdata(city);
            }
        });
    }

    private void intentdata(String city) {
        Intent intent = new Intent(getApplicationContext(), CityNgosActivity.class);
        intent.putExtra("city",city);
        startActivity(intent);
    }
}