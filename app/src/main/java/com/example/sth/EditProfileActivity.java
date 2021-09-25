package com.example.sth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    CircularImageView profileDP;
    Button save;
    TextView changeDP;

    DatabaseReference reff;
    FirebaseAuth auth;

    Uri uri;
    String myUri = "";
    StorageTask uplodTask;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        auth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        storageReference = FirebaseStorage.getInstance().getReference().child("ProfileDP");

        profileDP = findViewById(R.id.profile_pic);
        changeDP = findViewById(R.id.changeTv);
        save = findViewById(R.id.saveImage);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadProfileImage();
            }
        });
        changeDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setAspectRatio(1,1).start(EditProfileActivity.this);
            }
        });
        getUserInfo();
    }

    //Get user's info from firebase
    private void getUserInfo() {
        reff.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0)
                {
                    if (snapshot.hasChild("image"))
                    {
                        String image = snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profileDP);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Get Photo from Device
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            uri = result.getUri();
            profileDP.setImageURI(uri);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Error, Try Again!", Toast.LENGTH_SHORT).show();
        }
    }

    //Upload Photo into storage of firebase
    private void uploadProfileImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Set Your Profile");
        pd.setMessage("Please Wait, while we are setting your Profile");
        pd.show();

        if (uri != null)
        {
            final StorageReference fileRef = storageReference.child(auth.getCurrentUser().getUid()+".jpg");
            uplodTask = fileRef.putFile(uri);
             uplodTask.continueWithTask(new Continuation() {
                 @Override
                 public Object then(@NonNull Task task) throws Exception {
                     if (!task.isSuccessful())
                     {
                         throw task.getException();
                     }
                     return fileRef.getDownloadUrl();
                 }
             }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                 @Override
                 public void onComplete(@NonNull Task<Uri> task) {
                     if (task.isSuccessful())
                     {
                         Uri downloadUrl = task.getResult();
                         myUri = downloadUrl.toString();
                         HashMap<String, Object> userMap = new HashMap<>();
                         userMap.put("image",myUri);
                         reff.child(auth.getCurrentUser().getUid()).updateChildren(userMap);
                         pd.dismiss();
                         Toast.makeText(getApplicationContext(), "Photo Updated", Toast.LENGTH_SHORT).show();
                         EditProfileActivity.super.onBackPressed();
                     }
                 }
             });
        }
        else
        {
            pd.dismiss();
            Toast.makeText(getApplicationContext(), "Image not Selected", Toast.LENGTH_SHORT).show();
        }
    }
}