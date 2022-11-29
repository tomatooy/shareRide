package edu.uga.cs.shareride;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainPortal extends AppCompatActivity {
    Button DriverSite;
    Button RiderSite;
    Button SignOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_portal);
        DriverSite = findViewById(R.id.DriverButton);
        RiderSite = findViewById(R.id.RiderButton);
        SignOut = findViewById(R.id.SignOutButton);
        DriverSite.setOnClickListener(new DriveOnClick());
        RiderSite.setOnClickListener(new RiderOnClick());
        SignOut.setOnClickListener(new SignOutOnClick());
    }
    private class DriveOnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ToDriver();
        }
    }

    private class RiderOnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ToRider();
        }
    }

    private class SignOutOnClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            SignOut();
        }
    }

    private void ToRider(){
        Intent intent = new Intent( this, RiderMain.class );
        startActivity(intent);
    }
    private void ToDriver(){
        Intent intent = new Intent( this, DriverMain.class );
        startActivity(intent);
    }
    private void SignOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent( this, MainActivity.class );
        startActivity(intent);
    }

}