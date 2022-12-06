package edu.uga.cs.shareride;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ConfirmedRideAdapter extends  RecyclerView.Adapter<ConfirmedRideAdapter.RideHolder>{

    public static final String DEBUG_TAG = "RideRecyclerAdapter";
    private Context context;
    private List<Ride> rideList;

    public ConfirmedRideAdapter(Context context,  List<Ride> rideList) {
        this.rideList = rideList;
        this.context = context;
    }

    class RideHolder extends RecyclerView.ViewHolder {
        TextView destination, departure, date,driverEmail,riderEmail, points;
        public RideHolder(View itemView) {
            super(itemView);
            destination = itemView.findViewById(R.id.destAddress);
            departure = itemView.findViewById(R.id.startAddress);
            date = itemView.findViewById(R.id.date);
            points = itemView.findViewById(R.id.points);
            driverEmail = itemView.findViewById(R.id.DriverEmail);
            riderEmail = itemView.findViewById(R.id.RiderEmail);
        }
    }

    @Override
    public ConfirmedRideAdapter.RideHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride_confirmed, parent, false);
        return new ConfirmedRideAdapter.RideHolder(view);
    }


    @Override
    public void onBindViewHolder(ConfirmedRideAdapter.RideHolder holder, int position) {
        Ride ride = rideList.get(position);
        Log.d(DEBUG_TAG, "onBindViewHolder " + ride);
        holder.destination.setText("To: "+ride.getDestLocation());
        holder.departure.setText("From: "+ride.getStartLocation());
        holder.date.setText("Date: "+ride.getDate());
        holder.points.setText("Points: " + ride.getPointCost());
        holder.riderEmail.setText("Rider Email: " + ride.getRiderEmail());
        holder.driverEmail.setText("Driver Email: " + ride.getDriverEmail());

    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }


}
