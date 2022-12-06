package edu.uga.cs.shareride;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequestedRideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestedRideFragment extends Fragment {

    private RecyclerView recyclerView;
    private AcceptRideAdapter recyclerAdapter;
    private List<Ride> RideList = new ArrayList<Ride>();;
    final String DEBUG_TAG = "PostRide";
    private FirebaseDatabase database;
    private String dbName;

    private String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String currentEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

    public RequestedRideFragment() {
        // Required empty public constructor
    }

    public static RequestedRideFragment newInstance() {
        RequestedRideFragment fragment = new RequestedRideFragment();
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
        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.fragment_requested_ride,
                container,false);
        String site = this.getArguments().getString("site");
        if(site.equals("Driver")){
            dbName = "rideRequest";
        }
        else{
            dbName = "rideOffer";
        }
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        // Inflate the layout for this fragment
        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager( layoutManager );
        recyclerAdapter = new AcceptRideAdapter( getActivity(),RequestedRideFragment.this, RideList);
        recyclerView.setAdapter( recyclerAdapter );
        // get a Firebase DB instance reference
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(dbName);

        // Set up a listener (event handler) to receive a value for the database reference.
        // This type of listener is called by Firebase once by immediately executing its onDataChange method
        // and then each time the value at Firebase changes.
        //
        // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
        // to maintain job leads.
        myRef.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                // Once we have a DataSnapshot object, we need to iterate over the elements and place them on our job lead list.
                RideList.clear(); // clear the current content; this is inefficient!
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    Ride ride = postSnapshot.getValue(Ride.class);
                    ride.setKey(postSnapshot.getKey());
                    RideList.add( ride );
//                    Log.d( DEBUG_TAG, "ValueEventListener: added: " + jobLead );
//                    Log.d( DEBUG_TAG, "ValueEventListener: key: " + postSnapshot.getKey() );
                }

                //Log.d( DEBUG_TAG, "ValueEventListener: notifying recyclerAdapter" );
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                System.out.println( "ValueEventListener: reading failed: " + databaseError.getMessage() );
            }
        } );

    }

    public void confirmRide(String key,int position){
        Ride ride = RideList.get(position);
        RideList.remove( position );
        // Update the recycler view to remove the deleted job lead from that view
        recyclerAdapter.notifyItemRemoved( position );
        DatabaseReference ref = database.getReference().child(dbName).child(key);

        DatabaseReference pointRef = database.getReference("points");

        ref.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                dataSnapshot.getRef().removeValue().addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // edit points
                        // if ride is a request being fulfilled, add points to driver(current app user)
                        // and subtract points from rider(poster of request)
                        if(dbName == "rideRequest") {
                            ride.setDriverEmail(currentEmail);
                            // add points to ride driver
                            Query queryDriver = pointRef.orderByChild("userID").equalTo(currentUID);
                            queryDriver.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                        riderPoints pointObject = postSnapshot.getValue(riderPoints.class);
                                        Integer reqPoints = pointObject.getPoints();
                                        Integer newPoints = reqPoints + ride.getPointCost();
                                        riderPoints newRiderPoints = new riderPoints(pointObject.getEmail(), newPoints, pointObject.getUserID());
                                        postSnapshot.getRef().setValue(newRiderPoints);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                            // subtract points from rider
                            Query queryRider = pointRef.orderByChild("userID").equalTo(ride.getPosterID());
                            queryRider.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                        riderPoints pointObject = postSnapshot.getValue(riderPoints.class);
                                        Integer reqPoints = pointObject.getPoints();
                                        Integer newPoints = reqPoints - ride.getPointCost();
                                        riderPoints newRiderPoints = new riderPoints(pointObject.getEmail(), newPoints, pointObject.getUserID());
                                        postSnapshot.getRef().setValue(newRiderPoints);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }

                        // handles points for ride offers(opposite point exchange)
                        else if(dbName == "rideOffer") {
                            ride.setRiderEmail(currentEmail);
                            // subtract points from rider
                            Query queryRider = pointRef.orderByChild("userID").equalTo(currentUID);
                            queryRider.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                        riderPoints pointObject = postSnapshot.getValue(riderPoints.class);
                                        Integer reqPoints = pointObject.getPoints();
                                        Integer newPoints = reqPoints - ride.getPointCost();
                                        riderPoints newRiderPoints = new riderPoints(pointObject.getEmail(), newPoints, pointObject.getUserID());
                                        postSnapshot.getRef().setValue(newRiderPoints);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                            // add points to driver
                            Query queryDriver = pointRef.orderByChild("userID").equalTo(ride.getPosterID());
                            queryDriver.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                        riderPoints pointObject = postSnapshot.getValue(riderPoints.class);
                                        Integer reqPoints = pointObject.getPoints();
                                        Integer newPoints = reqPoints + ride.getPointCost();
                                        riderPoints newRiderPoints = new riderPoints(pointObject.getEmail(), newPoints, pointObject.getUserID());
                                        postSnapshot.getRef().setValue(newRiderPoints);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }

                        Log.d( DEBUG_TAG, "confirmed: " + position + "(" + key + ")" );
                        Toast.makeText(getActivity().getApplicationContext(), "Job lead deleted for " + key,
                                Toast.LENGTH_SHORT).show();
                        DatabaseReference ConfirmedRef = database.getReference("confirmedRide");
                        ConfirmedRef.push().setValue(ride)
                                .addOnSuccessListener( new OnSuccessListener<Void>() {
                                    @SuppressLint("RestrictedApi")
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Show a quick confirmation
                                        Toast.makeText(getApplicationContext(), "Inserted " ,
                                                Toast.LENGTH_SHORT).show();

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
                });
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                Log.d( DEBUG_TAG, "failed to confirm: " + position + "(" + key + ")" );
                Toast.makeText(getActivity().getApplicationContext(), "Failed to delete " + key,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}