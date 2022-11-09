package com.myjavaprojects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.myjavaprojects.bottomnavfragments.AboutFragment;
import com.myjavaprojects.bottomnavfragments.HomeFragment;
import com.myjavaprojects.bottomnavfragments.YourCodesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBottomNavigationView();
    }

    private void initBottomNavigationView() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
        bottomNavigationView.setSelectedItemId(R.id.homeFragment);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = getFragment(item);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        });


    }

    private Fragment getFragment(MenuItem item) {
        Fragment fragment;
        int itemId = item.getItemId();

        if (itemId == R.id.yourCodesFragment) {
            fragment = new YourCodesFragment();

        } else if (itemId == R.id.aboutFragment){
            fragment = new AboutFragment();

        } else {
            fragment = new HomeFragment();
        }

        return fragment;
    }
}
