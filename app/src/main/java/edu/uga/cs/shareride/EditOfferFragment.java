package edu.uga.cs.shareride;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;


public class EditOfferFragment extends DialogFragment {

    // indicate the type of an edit
    public static final int SAVE = 1;   // update an existing offer
    public static final int DELETE = 2; // delete an existing offer
    private Context context;
    private EditText editTo;
    private EditText editFrom;
    private EditText editDate;
    // private EditText commentsView;
    private PostedRideFragment hostFragment;
    int position;     // the position of the edited offer on the list of offers
    String key;
    String destination;
    String start;
    String date;
    String userID;
    String driverEmail;
    String riderEmail;
    private String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();


    public interface EditOfferListener {
        void updateOfferRide(int position, Ride ride, int action);
    }

    public static EditOfferFragment newInstance(int position, Integer points, String key,
                                                String destination, String start,
                                                String date, String userID, String driverEmail,
                                                String riderEmail) {
        EditOfferFragment dialog = new EditOfferFragment();

        // Supply job lead values as an argument.
        Bundle args = new Bundle();
        args.putString("key", key);
        args.putString("destination", destination);
        args.putString("start", start);
        args.putString("date", date);
        args.putString("userID", userID);
        args.putString("driverEmail", driverEmail);
        args.putString("riderEmail", riderEmail);
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        key = getArguments().getString("key");
        position = getArguments().getInt("position");
        destination = getArguments().getString("destination");
        start = getArguments().getString("start");
        date = getArguments().getString("date");
        userID = getArguments().getString("userID");
        driverEmail = getArguments().getString("driverEmail");
        riderEmail = getArguments().getString("riderEmail");

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.edit_dialog, getActivity().findViewById(R.id.diagRoot));

        editFrom = layout.findViewById(R.id.EditFrom2);
        editTo = layout.findViewById(R.id.EditTo2);
        editDate = layout.findViewById(R.id.EditDate2);

        // Pre-fill the edit texts with the current values for this job lead.
        // The user will be able to modify them.
        editFrom.setText(start);
        editTo.setText(destination);
        editDate.setText(date);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyles);
        builder.setView(layout);

        // Set the title of the AlertDialog
        builder.setTitle("Edit Ride Offer");
        builder.setPositiveButton( "SAVE", new SaveButtonClickListener() );

        // The Delete button handler
        builder.setNeutralButton( "DELETE", new DeleteButtonClickListener() );
        // The Cancel button handler
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    private class SaveButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String destAddress = editTo.getText().toString();
            String startAddress = editFrom.getText().toString();
            String depDate = editDate.getText().toString();

            Integer points = 50;

            Ride ride = new Ride(points, startAddress, destAddress, depDate, currentUID, driverEmail, riderEmail);
            ride.setKey(key);

            // get the Activity's listener to add the new job lead
            EditOfferListener listener = hostFragment;
            listener.updateOfferRide(position, ride, SAVE);

            // close the dialog
            dismiss();
        }
    }

    private class DeleteButtonClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            String destAddress = editTo.getText().toString();
            String startAddress = editFrom.getText().toString();
            String depDate = editDate.getText().toString();

            Integer points = 50;

            Ride ride = new Ride(points, startAddress, destAddress, depDate, currentUID, driverEmail, riderEmail);
            ride.setKey(key);

            // get the Activity's listener to add the new job lead
            EditOfferListener listener = hostFragment ;
            // add the new job lead
            listener.updateOfferRide(position,ride,DELETE);
            // close the dialog
            dismiss();
        }

    }
    public void setHostFragment( PostedRideFragment hostFragment )
    {
        this.hostFragment = hostFragment;
    }


}
