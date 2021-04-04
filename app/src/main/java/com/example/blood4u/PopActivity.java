package com.example.blood4u;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    private Button btnsave1;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private static int PICK_IMAGE = 123;
    public static AlarmManager alarmManager;
    public static PendingIntent pendingIntent;
    HomeActivity donateTimeStuff = new HomeActivity();
    Uri imagePath1;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        createNotificationChannel();

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        EditTextDate = (EditText) findViewById(R.id.EditTextDate);
        EditTextLocation = (EditText) findViewById(R.id.EditTextLocation);
        EditTextnum = (EditText) findViewById(R.id.EditTextBloodDoante);


        btnsave1 = (Button) findViewById(R.id.btnSaveButton1);
        FirebaseUser user1 = firebaseAuth.getCurrentUser();
        btnsave1.setOnClickListener(this);


        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


    }


    private void information() {
        String date = EditTextDate.getText().toString().trim();
        String location = EditTextLocation.getText().toString().trim();
        String blooddonate = EditTextnum.getText().toString().trim();

        Information information = new Information(date, location, blooddonate);


        FirebaseUser user1 = firebaseAuth.getCurrentUser();
        databaseReference.child(user1.getUid()).setValue(information);
        Toast.makeText(getApplicationContext(), "Your donation has been booked", Toast.LENGTH_LONG).show();

    }

    private void alarmManager() {            //set alarm to sent notification everyday until day 90
        Intent intent = new Intent(this, ReminderBroadcast.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long timeAtButtonClick = System.currentTimeMillis();

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeAtButtonClick + 3000,
                5000, pendingIntent);

    }

    @Override
    public void onClick(View view) {
        if (view == btnsave1) {
            alarmManager();
            //set counting day to 90 when click Done.
            SharedPreferences sharedPrefs = getApplicationContext().getSharedPreferences("PopActivity", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putInt("KEY_NAME", 91);
            editor.apply();
            //end
            if (imagePath1 == null) {

                Drawable drawable = this.getResources().getDrawable(R.drawable.person);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.person);
                // openSelectProfilePictureDialog();
                information();
                // sendUserData();
                finish();
                startActivity(new Intent(PopActivity.this, HistoryActivity.class));
            } else {
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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "BLud4U Reminder Notification";
            String description = "You can donate your blood now. Don't forget to prepare yourself!";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyBud", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //bottomnavigationbar copy start from this line
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            Intent home_intent = new Intent(PopActivity.this, HomeActivity.class);
                            startActivity(home_intent);
                            break;
                        case R.id.navigation_setting:
                            Intent search_intent = new Intent(PopActivity.this, Setting.class);
                            startActivity(search_intent);
                            break;
                        case R.id.navigation_Profile:
                            Intent profile_intent = new Intent(PopActivity.this, ProfileActivity.class);
                            startActivity(profile_intent);
                            break;

                        // not finished
                    }
                    return false;
                }
            };
    //bottomnavigationbar copy finish at this line
}