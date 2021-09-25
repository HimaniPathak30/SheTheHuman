package com.example.sth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    // ------Variable Declaration------
    EditText mail, password;
    TextView forgotPass, signupAcc;
    Button login;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //------Assigning Variables------
        mail = findViewById(R.id.username);
        password = findViewById(R.id.Pass);
        forgotPass = findViewById(R.id.forgotPass);
        signupAcc = findViewById(R.id.register);
        login = findViewById(R.id.signUpBtn);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        //------Forgot Password-----
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
            }
        });


        //----- Redirect to Sign Up Page-----
        signupAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });

        //-----Redirect to Home Page-----
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mail.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if(email.isEmpty()){
                    mail.setError("Email Required!");
                    mail.requestFocus();
                    return;
                }
                if(pass.isEmpty()){
                    password.setError("Password Required!");
                    password.requestFocus();
                    return;
                }

                //Get data from firebase check its validation and if true then allow to login
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified())
                            {
                                Intent loggedIn = new Intent(getApplicationContext(), DrawerActivity.class);
                                startActivity(loggedIn);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Check you email and verify your account!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Email or Password is incorrect. Try Again!"
                                        ,Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });
    }
}