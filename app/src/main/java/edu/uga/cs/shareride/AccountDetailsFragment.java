package edu.uga.cs.shareride;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AccountDetailsFragment extends Fragment {

    private TextView accountEmail;
    private TextView accountPoints;
    private FirebaseDatabase database;
    private String dbName;
    private String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String currentEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();


    public AccountDetailsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.account_details,
                container,false);
        return layout;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference pointRef = database.getReference("points");

        super.onViewCreated(view, savedInstanceState);
        accountEmail = getView().findViewById(R.id.emailView);
        accountPoints = getView().findViewById(R.id.ridePointView);
        accountEmail.setText("Email: " + currentEmail);
        Query queryDriver = pointRef.orderByChild("userID").equalTo(currentUID);
        queryDriver.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    riderPoints pointObject = postSnapshot.getValue(riderPoints.class);
                    Integer points = pointObject.getPoints();
                    accountPoints.setText("Points: " + points);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //accountPoints.setText(points);
    }
}
