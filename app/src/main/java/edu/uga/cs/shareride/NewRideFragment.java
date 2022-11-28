package edu.uga.cs.shareride;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewRideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewRideFragment extends Fragment {


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_ride, container, false);
    }
}