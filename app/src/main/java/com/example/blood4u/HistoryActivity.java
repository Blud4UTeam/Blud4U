package com.example.blood4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference databaseReference;
    private TextView profileDate, profileLocation, profileNum;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private static int PICK_IMAGE = 123;
    private Bitmap bitmap;
    Uri imagePath;
    private Button d1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        profileDate = findViewById(R.id.profile_date);
        profileLocation = findViewById(R.id.profile_location);
        profileNum = findViewById(R.id.profile_num);
        d1 = findViewById(R.id.back);
        d1.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        try {
            databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        } catch (RuntimeException e) {
            Toast.makeText(HistoryActivity.this, "Profile has not yet been created",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), PopActivity.class));
            finish();
        }
        try {
            storageReference = firebaseStorage.getReference();
        } catch (RuntimeException e) {
            Toast.makeText(HistoryActivity.this, "Profile has not yet been created",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), PopActivity.class));
            finish();
        }
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),PopActivity.class));
        }
        final FirebaseUser user1 =firebaseAuth.getCurrentUser();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                try {
                    Information userProfile = dataSnapshot.getValue(Information.class);
                    profileDate.setText(userProfile.getUserDate());
                    profileLocation.setText(userProfile.getUserLocation());
                    profileNum.setText(userProfile.getUserNum());





                } catch (NullPointerException e) {
                    Toast.makeText(HistoryActivity.this, "Cannot Find Profile Please Re sign in again.",
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled( DatabaseError databaseError) {
                Toast.makeText(HistoryActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onClick(View view) {
        Intent d1 = new Intent(HistoryActivity.this, HomeActivity.class);
        startActivity(d1);
    }
}