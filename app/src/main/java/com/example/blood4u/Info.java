package com.example.blood4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Button buttonPrepare = findViewById(R.id.preparation);
        buttonPrepare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Info.this, activity_preparation.class);
                startActivity(intent);
            }
        });
    }

    //bottomnavigationbar copy start from this line
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            Intent home_intent = new Intent(Info.this,HomeActivity.class);
                            startActivity(home_intent);
                            break;
                        case R.id.navigation_setting:
                            Intent search_intent = new Intent(Info.this, HomeActivity.class);
                            startActivity(search_intent);
                            break;
                        case R.id.navigation_Profile:
                            Intent profile_intent = new Intent(Info.this, ProfileActivity.class);
                            startActivity(profile_intent);
                            break;
                        // not finished
                    }
                    return false;
                }
            };
    //bottomnavigationbar copy finish at this line
}