package com.example.sth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    // ------Variable Declaration------
    EditText username, email, password, phone;
    TextView login;
    Button signup;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        //------Assigning Variables------
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.Pass);
        phone = findViewById(R.id.Phone);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signUpBtn);
        progressBar = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();

        //----- Redirect to Sign Up Page-----
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginPage = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginPage);
            }
        });

        //-----Store in Database-----
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = username.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String phno = phone.getText().toString().trim();

                if(uname.isEmpty()){
                    username.setError("Username Required!");
                    username.requestFocus();
                    return;
                }
                if(mail.isEmpty()){
                    email.setError("Email Required!");
                    email.requestFocus();
                    return;
                }
                if(pass.isEmpty()){
                    password.setError("Password Required!");
                    password.requestFocus();
                    return;
                }
                if(phno.isEmpty()){
                    phone.setError("Rewrite Password!");
                    phone.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //Create user and store users data into firebase
                firebaseAuth.createUserWithEmailAndPassword(mail,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    FirebaseUser curr_user = firebaseAuth.getCurrentUser();
                                    curr_user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getApplicationContext(), "Verification Email is Sent", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "Error to send Verification Code", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    User user = new User(uname,mail,phno);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(getApplicationContext(),"Registered Successfully!"
                                                ,Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.VISIBLE);
                                                Intent loginPage = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(loginPage);
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(),"Failed to Register.Try Again!"
                                                        ,Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Failed to Register.Try Again!"
                                            ,Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });
    }
}