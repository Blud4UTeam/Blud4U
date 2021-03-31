package com.example.blood4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class PopActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private EditText EditTextDate;
    private EditText EditTextLocation;
    private EditText EditTextnum;
    private  Button btnsave1;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private static int PICK_IMAGE = 123;
    Uri imagePath1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);


        firebaseAuth= FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        EditTextDate = (EditText)findViewById(R.id.EditTextDate);
        EditTextLocation = (EditText)findViewById(R.id.EditTextLocation);
        EditTextnum = (EditText)findViewById(R.id.EditTextBloodDoante);


        btnsave1=(Button)findViewById(R.id.btnSaveButton1);
        FirebaseUser user1=firebaseAuth.getCurrentUser();
        btnsave1.setOnClickListener(this);


        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


    }

    private void information(){
        String date = EditTextDate.getText().toString().trim();
        String location = EditTextLocation.getText().toString().trim();
        String blooddonate = EditTextnum.getText().toString().trim();

        Information information = new Information(date, location, blooddonate);
        FirebaseUser user1 = firebaseAuth.getCurrentUser();
        databaseReference.child(user1.getUid()).setValue(information);
        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();

    }
    @Override
    public void onClick(View view) {
        if (view==btnsave1){
            if (imagePath1 == null) {

                Drawable drawable = this.getResources().getDrawable(R.drawable.person);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.person);
                // openSelectProfilePictureDialog();
                information();
                // sendUserData();
                finish();
                startActivity(new Intent(PopActivity.this, HistoryActivity.class));
            }
            else {
                information();
                sendUserData();
                finish();
                startActivity(new Intent(PopActivity.this, HistoryActivity.class));
            }
        }
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Get "User UID" from Firebase > Authentification > Users.
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic"); //User id/Images/Profile Pic.jpg
        UploadTask uploadTask = imageReference.putFile(imagePath1);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PopActivity.this, "Error: Uploading profile picture", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(PopActivity.this, "Profile picture uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }
}