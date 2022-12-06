package edu.uga.cs.shareride;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AcceptRideAdapter extends  RecyclerView.Adapter<AcceptRideAdapter.RideHolder>{

    public static final String DEBUG_TAG = "RideRecyclerAdapter";
    private Context context;
    private List<Ride> rideList;
    private RequestedRideFragment fragment;
    public AcceptRideAdapter(Context context,RequestedRideFragment fragment, List<Ride> rideList) {
        this.rideList = rideList;
        this.fragment=fragment;
        this.context = context;
    }

    class RideHolder extends RecyclerView.ViewHolder {
        TextView destination, departure, date, points;
        Button Confirm;
        public RideHolder(View itemView) {
            super(itemView);
            destination = itemView.findViewById(R.id.destAddress);
            departure = itemView.findViewById(R.id.startAddress);
            date = itemView.findViewById(R.id.date);
            Confirm = itemView.findViewById(R.id.ConfirmRide);
            points = itemView.findViewById(R.id.points);
        }
    }

    @Override
    public AcceptRideAdapter.RideHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride_with_accept, parent, false);
        return new AcceptRideAdapter.RideHolder(view);
    }



    @Override
    public void onBindViewHolder(AcceptRideAdapter.RideHolder holder, int position) {
        Ride ride = rideList.get(position);
        Log.d(DEBUG_TAG, "onBindViewHolder " + ride);
        holder.destination.setText("To: "+ride.getDestLocation());
        holder.departure.setText("From:  "+ride.getStartLocation());
        holder.date.setText("Date: "+ride.getDate());
        holder.points.setText("Points: " + ride.getPointCost());
        String key = ride.getKey();
        String destination = ride.getDestLocation();
        String startLocation = ride.getStartLocation();
        String date = ride.getDate();
        Integer points = ride.getPointCost();
        holder.Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.confirmRide(key,holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }


}
