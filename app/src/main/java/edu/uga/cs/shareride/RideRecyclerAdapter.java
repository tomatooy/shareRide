package edu.uga.cs.shareride;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RideRecyclerAdapter extends RecyclerView.Adapter<RideRecyclerAdapter.RideHolder> {

    public static final String DEBUG_TAG = "RideRecyclerAdapter";

    private List<Ride> rideList;

    public RideRecyclerAdapter(List<Ride> rideList) {
        this.rideList = rideList;
    }

    class RideHolder extends RecyclerView.ViewHolder {
        TextView destination, departure, time;

        public RideHolder(View itemView) {
            super(itemView);

            destination = itemView.findViewById(R.id.destAddress);
            departure = itemView.findViewById(R.id.startAddress);
            time = itemView.findViewById(R.id.time);
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

        holder.destination.setText(ride.getDestLocation());
        holder.departure.setText(ride.getStartLocation());
        holder.time.setText(ride.getDate());
    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }
}
