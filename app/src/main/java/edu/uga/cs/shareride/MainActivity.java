package edu.uga.cs.shareride;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "ShareRide";
    private static final String DEBUG_TAG = "debug" ;
    private FirebaseAuth mAuth;
    private Button loginDriver;
    private Button loginRider;
    private int SigninType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById( R.id.textView );
        loginDriver =  findViewById(R.id.DriverLogin);
        loginRider =  findViewById(R.id.RiderLogin);
        Button registerButton = findViewById(R.id.registerButton);
        loginRider.setOnClickListener( new SignInButtonClickListener());
        loginDriver.setOnClickListener(new SignInButtonClickListener());
        registerButton.setOnClickListener(new RegisterButtonClickListener());

//        mAuth = FirebaseAuth.getInstance();
//        String email = "dawg@mail.com";
//        String password = "password";
//
//        mAuth.signInWithEmailAndPassword( email, password )
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.d(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference( "message" );
//
//        // Read from the database value for ”message”
//        myRef.addValueEventListener( new ValueEventListener() {
//            @Override
//            public void onDataChange( DataSnapshot dataSnapshot ) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String message = dataSnapshot.getValue( String.class );
//                textView.setText( message );
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w( TAG, "Failed to read value.", error.toException() );
//            }
//        });
    }
    private class SignInButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick( View v ) {
            // This is an example of how to use the AuthUI activity for signing in to Firebase.
            // Here, we are just using email/password sign in.
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build()
            );

            Log.d( DEBUG_TAG, "MainActivity.SignInButtonClickListener: Signing in started" );

            // Create an Intent to singin to Firebese.
            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setLogo(R.drawable.logo)
                    // this sets our own theme (color scheme, sizing, etc.) for the AuthUI's appearance
                    .setTheme(R.style.LoginTheme)
                    .build();
            SigninType = v.getId();
            signInLauncher.launch(signInIntent);
        }
    }

    // The ActivityResultLauncher class provides a new way to invoke an activity
    // for some result.  It is a replacement for the deprecated method startActivityForResult.
    //
    // The signInLauncher variable is a launcher to start the AuthUI's logging in process that
    // should return to the MainActivity when completed.  The overridden onActivityResult
    // is then called when the Firebase logging-in process is finished.
    private ActivityResultLauncher<Intent> signInLauncher =
            registerForActivityResult(
                    new FirebaseAuthUIActivityResultContract(),
                    new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                        @Override
                        public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                            onSignInResult(result);
                        }
                    }
            );

    // This method is called once the Firebase sign-in activity (lqunched above) returns (completes).
    // Then, the current (logged-in) Firebase user can be obtained.
    // Subsequently, there is a transition to the JobLeadManagementActivity.
    private void onSignInResult( FirebaseAuthUIAuthenticationResult result ) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            if( response != null ) {
                Log.d( DEBUG_TAG, "MainActivity.onSignInResult: response.getEmail(): " + response.getEmail() );
            }

            //Log.d( DEBUG_TAG, "MainActivity.onSignInResult: Signed in as: " + user.getEmail() );
            Intent intent;
            // after a successful sign in, start the job leads management activity
            if(SigninType == loginDriver.getId() ){
                intent = new Intent( this, DriverMain.class );
            }
            else{
                intent = new Intent( this, RiderMain.class );
            }
            startActivity( intent );
        }
        else {
            Log.d( DEBUG_TAG, "MainActivity.onSignInResult: Failed to sign in" );
            // Sign in failed. If response is null the user canceled the
            Toast.makeText( getApplicationContext(),
                    "Sign in failed",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private class RegisterButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // start the user registration activity
            Intent intent = new Intent(view.getContext(), RegisterActivity.class);
            view.getContext().startActivity(intent);
        }
    }
}