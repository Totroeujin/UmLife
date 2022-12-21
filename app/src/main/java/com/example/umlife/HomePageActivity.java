package com.example.umlife;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePageActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RelativeLayout relativeLayout;

    // show all post from all user
    PostFragment postFragment = new PostFragment();

    // show all ongoing event
    EventListFragment eventListFragment = new EventListFragment();

    // reward system
    RewardSystemFragment rewardSystemFragment = new RewardSystemFragment();

    // profile of the user
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // search relative layout
        relativeLayout = findViewById(R.id.Main);

        try {
            // bottom navigation
            bottomNavigationView = findViewById(R.id.bottomnavigation);

            getSupportFragmentManager().beginTransaction().replace(R.id.container, postFragment).commit();

            bottomNavigateListener();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void bottomNavigateListener() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, postFragment).commit();
                        return true;
                    case R.id.event:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, eventListFragment).commit();
                        return true;
                    case R.id.reward:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, rewardSystemFragment).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}