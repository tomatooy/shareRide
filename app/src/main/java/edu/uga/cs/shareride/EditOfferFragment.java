package edu.uga.cs.shareride;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;

public class EditOfferFragment extends DialogFragment {

    // indicate the type of an edit
    public static final int SAVE = 1;   // update an existing offer
    public static final int DELETE = 2; // delete an existing offer

    private EditText editTo;
    private EditText editFrom;
    private EditText editDate;
    // private EditText commentsView;

    int position;     // the position of the edited offer on the list of offers
    String key;
    String destination;
    String start;
    String date;

    public interface EditOfferFragment {
        void updateJobLead(int position, Ride ride, int action);
    }

    public static edu.uga.cs.shareride.EditOfferFragment newInstance(int position, String key,
                                                                     String destination, String start,
                                                                     String date) {
        EditOfferFragment dialog = new EditOfferFragment();

        // Supply job lead values as an argument.
        Bundle args = new Bundle();
        args.putString( "key", key );
        args.putString( "destination", destination );
        args.putString("start", start);
        args.putString("date", date);
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState ) {

        key = getArguments().getString( "key" );
        position = getArguments().getInt( "position" );
        destination = getArguments().getString( "destination" );
        start = getArguments().getString( "start" );
        date = getArguments().getString("date");

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate( R.layout.fragment_new_ride, getActivity().findViewById( R.id.root ) );

        editFrom = layout.findViewById( R.id.EditFrom );
        editTo = layout.findViewById( R.id.EditTo );
        editDate = layout.findViewById( R.id.RideDate );

        // Pre-fill the edit texts with the current values for this job lead.
        // The user will be able to modify them.
        editFrom.setText( start );
        editTo.setText( destination );
        editDate.setText( date );

        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity(), R.style.AlertDialogStyle );
        builder.setView(layout);

        // Set the title of the AlertDialog
        builder.setTitle( "Edit Ride Offer" );

        // The Cancel button handler
        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });

        // The Save button handler
        builder.setPositiveButton( "SAVE", new SaveButtonClickListener() );

        // The Delete button handler
        builder.setNeutralButton( "DELETE", new DeleteButtonClickListener() );

        // Create the AlertDialog and show it
        return builder.create();
    }

    private class SaveButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String destAddress = editTo.getText().toString();
            String startAddress = editFrom.getText().toString();
            String depDate = editDate.getText().toString();

            Integer points = 50;

            Ride ride = new Ride( points, startAddress, destAddress, depDate );
            ride.setKey( key );

            // get the Activity's listener to add the new job lead
            EditOfferListener listener = (EditJobLeadDialogFragment.EditJobLeadDialogListener) getActivity();
            // add the new job lead
            listener.updateJobLead( position, jobLead, SAVE );

            // close the dialog
            dismiss();
        }
    }

}
