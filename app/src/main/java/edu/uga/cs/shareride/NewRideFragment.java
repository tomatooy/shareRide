package edu.uga.cs.shareride;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewRideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewRideFragment extends Fragment  {
    private EditText From;
    private EditText To;
    private EditText Date;
    private Button button;
    private int cost = 50;
    final String Tag ="debug";
    private String TABLE;
    public NewRideFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NewRideFragment newInstance(String param1, String param2) {
        NewRideFragment fragment = new NewRideFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        String site = this.getArguments().getString("site");
        if(site.equals("Driver")){
            TABLE = "rideOffer";
        }
        else{
            TABLE = "rideRequest";
        }
        final View layout = inflater.inflate(R.layout.fragment_new_ride,
                getActivity().findViewById(R.id.diagRoot));
        From = layout.findViewById(R.id.EditFrom);
        To = layout.findViewById(R.id.EditTo);
        Date = layout.findViewById(R.id.RideDate);
        button = layout.findViewById(R.id.buttonSubmit);
        button.setOnClickListener(new addNewRideListener());
        return layout;
    }

    private class addNewRideListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Log.d(Tag,"submit clicked");
            String fromData = From.getText().toString();
            String toData = To.getText().toString();
            String dateData = Date.getText().toString();
            Integer cost = new Integer(50);
            Ride newRide = new Ride(cost,fromData,toData,dateData);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(TABLE);

            // First, a call to push() appends a new node to the existing list (one is created
            // if this is done for the first time).  Then, we set the value in the newly created
            // list node to store the new job lead.
            // This listener will be invoked asynchronously, as no need for an AsyncTask, as in
            // the previous apps to maintain job leads.
            myRef.push().setValue( newRide )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @SuppressLint("RestrictedApi")
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Show a quick confirmation
                            Toast.makeText(getApplicationContext(), "Inserted " ,
                                    Toast.LENGTH_SHORT).show();

                            // Clear the EditTexts for next use.
                            From.setText("");
                            To.setText("");
                            Date.setText("");
                        }
                    })
                    .addOnFailureListener( new OnFailureListener() {
                        @SuppressLint("RestrictedApi")
                        @Override
                        public void onFailure( @NonNull Exception e ) {
                            Toast.makeText( getApplicationContext(), "Failed " ,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
    }
    }
}