package edu.uga.cs.shareride;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class DriverMain extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);
        bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.new_ride);
        Bundle bundle = new Bundle();
        bundle.putString("site","Driver");
        postedFrag.setArguments(bundle);
        newRideFrag.setArguments(bundle);
        requestedFrag.setArguments(bundle);
        detailsFrag.setArguments(bundle);
        // When we open the application first
        // time the fragment should be shown to the user
        // in this case it is home fragment
    }
    Fragment newRideFrag = new NewRideFragment();
    Fragment requestedFrag = new RequestedRideFragment();
    Fragment postedFrag = new PostedRideFragment();
    Fragment confirmedFrag = new ConfirmedRideFragment();
    Fragment detailsFrag = new AccountDetailsFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Offered_ride:_ride:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame, postedFrag).commit();
                return true;
            case R.id.requested_ride:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame,requestedFrag).commit();
                return true;
            case R.id.new_ride:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame, newRideFrag).commit();
                return true;
            case R.id.confirmed_ride:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame, confirmedFrag).commit();
                return true;
            case R.id.account:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame, detailsFrag).commit();
                return true;
        }
        return false;
    }
}