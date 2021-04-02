package com.example.blood4u;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    androidx.drawerlayout.widget.DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button buttonInfo = findViewById(R.id.info);
        Button donatenow = findViewById(R.id.donatenow);
        drawerLayout = findViewById(R.id.drawer_layout);
        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, Info.class);
                startActivity(intent);
            }
        });

        donatenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PopActivity.class);
                startActivity(intent);
            }
        });

        //bottomnavigationbar copy start from this line
        bottomNavigation = findViewById(R.id.bottom_navigation);
        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent home_intent = new Intent(HomeActivity.this,HomeActivity.class);
                                startActivity(home_intent);
                                break;
                            case R.id.navigation_setting:
                                Intent search_intent = new Intent(HomeActivity.this, Setting.class);
                                startActivity(search_intent);
                                break;
                            case R.id.navigation_Profile:
                                Intent profile_intent = new Intent(HomeActivity.this, ProfileActivity.class);
                                startActivity(profile_intent);
                                break;
                            // not finished
                        }
                        return false;
                    }
                };
        //bottomnavigationbar copy finish at this line
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }
}