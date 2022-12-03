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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostedRideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostedRideFragment extends Fragment implements EditOfferFragment.EditOfferListener {

    private RecyclerView recyclerView;
    private RideRecyclerAdapter recyclerAdapter;
    private List<Ride> RideList = new ArrayList<Ride>();;
    final String DEBUG_TAG = "PostRide";
    private FirebaseDatabase database;
    public PostedRideFragment() {
        // Required empty public constructor
    }


    public static PostedRideFragment newInstance(String param1, String param2) {
        PostedRideFragment fragment = new PostedRideFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_posted_ride,
                container,false);
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        // Inflate the layout for this fragment
        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager( layoutManager );
        recyclerAdapter = new RideRecyclerAdapter( getActivity(),PostedRideFragment.this, RideList);
        recyclerView.setAdapter( recyclerAdapter );
        // get a Firebase DB instance reference
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("rideOffer");

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

    public void updateOfferRide(int position, Ride ride, int action ) {
        if( action == EditOfferFragment.SAVE ) {
            Log.d( DEBUG_TAG, "Updating job lead at: " + position + "(" + ride.getKey() + ")" );

            // Update the recycler view to show the changes in the updated job lead in that view
            recyclerAdapter.notifyItemChanged( position );

            // Update this job lead in Firebase
            // Note that we are using a specific key (one child in the list)
            DatabaseReference ref = database
                    .getReference()
                    .child( "rideOffer" )
                    .child( ride.getKey() );

            // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
            // to maintain job leads.
            ref.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                    dataSnapshot.getRef().setValue(ride).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d( DEBUG_TAG, "updated job lead at: " + position + "(" + ride.getKey() + ")" );
                            Toast.makeText(getActivity().getApplicationContext(), "Job lead updated for " + ride.getKey(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled( @NonNull DatabaseError databaseError ) {
                    Log.d( DEBUG_TAG, "failed to update job lead at: " + position + "(" + ride.getKey() + ")" );
                    Toast.makeText(getActivity().getApplicationContext(), "Failed to update " + ride.getKey(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if( action == EditOfferFragment.DELETE ) {
            Log.d( DEBUG_TAG, "Deleting job lead at: " + position + "(" + ride.getKey()+ ")" );

            // remove the deleted job lead from the list (internal list in the App)
            RideList.remove( position );

            // Update the recycler view to remove the deleted job lead from that view
            recyclerAdapter.notifyItemRemoved( position );

            // Delete this job lead in Firebase.
            // Note that we are using a specific key (one child in the list)
            DatabaseReference ref = database
                    .getReference()
                    .child( "rideOffer" )
                    .child( ride.getKey() );

            // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
            // to maintain job leads.
            ref.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                    dataSnapshot.getRef().removeValue().addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d( DEBUG_TAG, "deleted job lead at: " + position + "(" + ride.getKey() + ")" );
                            Toast.makeText(getActivity().getApplicationContext(), "Job lead deleted for " + ride.getKey(),
                                    Toast.LENGTH_SHORT).show();                        }
                    });
                }

                @Override
                public void onCancelled( @NonNull DatabaseError databaseError ) {
                    Log.d( DEBUG_TAG, "failed to delete job lead at: " + position + "(" + ride.getKey() + ")" );
                    Toast.makeText(getActivity().getApplicationContext(), "Failed to delete " + ride.getKey(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}