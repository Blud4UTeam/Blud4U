package com.example.blood4u;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        //notify variable for edit counting the day.
        SharedPreferences sharedPrefs = getApplicationContext().getSharedPreferences("PopActivity", Context.MODE_PRIVATE);
        int count = sharedPrefs.getInt("KEY_NAME", 0);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        //end
        //set the day before user can donate blood. >> show in top of HomeActivity.
        if (count >= 91) {          //before click Done to donate.
            String txt = "   You can donate blood now.   ";
            TextView donateIn = (TextView) findViewById(R.id.youcandonate);
            donateIn.setText(txt);
        } else if (count == 0) {    //when 0 day before can donate set counting variable to 91.
            String txt = "   You can donate blood now.   ";
            TextView donateIn = (TextView) findViewById(R.id.youcandonate);
            donateIn.setText(txt);
            editor.putInt("KEY_NAME", 91);
            editor.apply();
        } else {                    //after click Done set the day to counting.
            String txt = "     You can donate in " + String.valueOf(count) + " day     ";
            TextView donateIn = (TextView) findViewById(R.id.youcandonate);
            donateIn.setText(txt);
        }
        //end
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
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent home_intent = new Intent(HomeActivity.this, HomeActivity.class);
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