package edu.uga.cs.shareride;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RiderMain extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_main);
        bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.requested_ride);

        // When we open the application first
        // time the fragment should be shown to the user
        // in this case it is home fragment
    }
    Fragment newRideFrag = new NewRideFragment();
    Fragment postedFrag = new PostedRideFragment();
    Fragment confirmedFrag = new ConfirmedRideFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.posted_ride:_ride:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame, postedFrag).commit();
                return true;
            case R.id.new_ride:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame, newRideFrag).commit();
                return true;
            case R.id.confirmed_ride:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame, confirmedFrag).commit();
                return true;
        }
        return false;
    }
}