package com.example.sth;

import static android.content.Context.LOCATION_SERVICE;

import androidx.activity.OnBackPressedCallback;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sth.adapters.ContactAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    //Declaring Variables
    CardView survey_banner, safety_banner, escape_banner, law_banner, video_banner, ngo_banner;
    Button addContact;
    TextView emergencyBtn;
    View v;

    String uId;
    FirebaseUser user;
    DatabaseReference reff;

    //Location
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationManager locationManager;
    LocationListener locationListener;
    String[] appPermission = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.SEND_SMS
    };
    private static final int PERMISSIONS_REQUESTS_CODE = 123;
    public static long back_pressed;

    private DashboardViewModel mViewModel;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashboard, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        // TODO: Use the ViewModel

        survey_banner = getActivity().findViewById(R.id.survey_banner);
        safety_banner = getActivity().findViewById(R.id.safety_banner);
        escape_banner = getActivity().findViewById(R.id.escape_banner);
        law_banner = getActivity().findViewById(R.id.law_banner);
        video_banner = getActivity().findViewById(R.id.video_banner);
        ngo_banner = getActivity().findViewById(R.id.ngo_banner);

        addContact = getActivity().findViewById(R.id.addContact);
        emergencyBtn = getActivity().findViewById(R.id.emergency_btn);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference("Users");
        uId = user.getUid();

        //-----Redirect to Law Page-----
        law_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent law = new Intent(getActivity(), LawActivity.class);
                startActivity(law);
            }
        });

        //-----Redirect to Defence Video Page-----
        video_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent video = new Intent(getActivity(), VideoActivity.class);
                startActivity(video);
            }
        });

        //-----Redirect to Threat Tips Page-----
        escape_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent escape = new Intent(getActivity(), ThreatActivity.class);
                startActivity(escape);
            }
        });

        //-----Redirect to Survey Page-----
        survey_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent survey = new Intent(getActivity(), SurveyActivity.class);
                startActivity(survey);
            }
        });

        //-----Redirect to Safety Page-----
        safety_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SafetyActivity.class));
            }
        });

        //-----Redirect to NGO Page-----
        ngo_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CityActivity.class));
            }
        });

        //-----Redirect to Add Contact Page-----
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddContactsActivity.class));
            }
        });

        //-----Send Location-----
        emergencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Location Send
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
    }

    //This Function get current location
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Location location = task.getResult();
                if (location != null)
                {
                    Geocoder geocoder = new Geocoder(getContext(),Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Contacts");
                        ref.addValueEventListener(new ValueEventListener() {
                            String SMS = String.valueOf("Emergency SOS, I need you help.My coordinates are " +
                                    "http://www.google.com/maps/place/"+
                                    addresses.get(0).getLatitude()+","+addresses.get(0).getLongitude());
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren())
                                {
                                    if(ds.child("uid").getValue().toString().equals(uId))
                                    {
                                        String phoneNo = ds.child("number").getValue().toString();
                                        try {
                                            SmsManager smsManager = SmsManager.getDefault();
                                            smsManager.sendTextMessage(phoneNo,null,SMS,null,null);;
                                            Toast.makeText(getActivity().getApplicationContext(), "Message Sent", Toast.LENGTH_SHORT).show();
                                        }
                                        catch (Exception e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getActivity().getApplicationContext(), ""+ error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}