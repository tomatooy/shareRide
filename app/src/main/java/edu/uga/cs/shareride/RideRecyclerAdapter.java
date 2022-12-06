package edu.uga.cs.shareride;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RideRecyclerAdapter extends RecyclerView.Adapter<RideRecyclerAdapter.RideHolder> {

    public static final String DEBUG_TAG = "RideRecyclerAdapter";
    private Context context;
    private List<Ride> rideList;
    private PostedRideFragment fragment;
    public RideRecyclerAdapter(Context context,PostedRideFragment fragment, List<Ride> rideList) {
        this.rideList = rideList;
        this.fragment=fragment;
        this.context = context;
    }

    class RideHolder extends RecyclerView.ViewHolder {
        TextView destination, departure, date, points;
        Button Edit,Delete;
        public RideHolder(View itemView) {
            super(itemView);
            destination = itemView.findViewById(R.id.destAddress);
            departure = itemView.findViewById(R.id.startAddress);
            date = itemView.findViewById(R.id.date);
            points = itemView.findViewById(R.id.points);
            Edit = itemView.findViewById(R.id.EditRide);
            Delete = itemView.findViewById(R.id.ConfirmRide);
        }
    }

    @Override
    public RideHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride_with_button, parent, false);
        return new RideHolder(view);
    }

    @Override
    public void onBindViewHolder(RideHolder holder, int position) {
        Ride ride = rideList.get(position);
        Log.d(DEBUG_TAG, "onBindViewHolder " + ride);
        holder.destination.setText("To: "+ride.getDestLocation());
        holder.departure.setText("From: "+ride.getStartLocation());
        holder.date.setText("Date: "+ride.getDate());
        holder.points.setText("Points: " + ride.getPointCost());
        String key = ride.getKey();
        String destination = ride.getDestLocation();
        String startLocation = ride.getStartLocation();
        String date = ride.getDate();
        Integer points = ride.getPointCost();
        String userID = ride.getPosterID();
        String driverEmail = ride.getDriverEmail();
        String riderEmail = ride.getRiderEmail();

        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d( TAG, "onBindViewHolder: getItemId: " + holder.getItemId() );
                //Log.d( TAG, "onBindViewHolder: getAdapterPosition: " + holder.getAdapterPosition() );
                EditOfferFragment editOfferFragment =
                        EditOfferFragment.newInstance( holder.getAdapterPosition(), points, key, destination,startLocation, date , userID, driverEmail, riderEmail);
                editOfferFragment.setHostFragment(fragment);
                editOfferFragment.show( ((AppCompatActivity)v.getContext()).getSupportFragmentManager(), null);
            }
        });

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.updateOfferRide(holder.getAdapterPosition(),ride,2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }
}
