package com.agh.expenseapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class EarnFragment extends Fragment {

    /*
    Fragment responsible for handling Earning part of application
     */

    Button b1, b2;
    DatabaseHelper db;
    EditText et1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_earn, container, false);

        // Arguments received and prepared to pass to other fragments
        Bundle bundle = this.getArguments();
        final String user = bundle.get("user").toString();
        final Bundle arguments = new Bundle();
        arguments.putString("user", user);

        // Initialize and watch field responsible for input (money)
        et1 = v.findViewById(R.id.amountCash1);
        et1.addTextChangedListener(new CurrencyTextWatcher(et1));

        // DB helper initialization
        db = new DatabaseHelper(getContext());

        // Button to earn money initialized
        b2 = v.findViewById(R.id.earnSomeMoneyButton);

        // Listener on earn money button - reaction only if not empty input field
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et1.getText().toString().equals("")) {
                    // Insert amount to DB
                    db.insertPositiveBalance(user, et1.getText().toString(), "Incoming");

                    // Make a toast to show user our awareness
                    Toast.makeText(getContext(), "Congrats on earning money!", Toast.LENGTH_LONG).show();

                    // Prepare new fragments with proper arguments
                    StatisticsFragment statisticsFragment = new StatisticsFragment();
                    statisticsFragment.setArguments(arguments);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    MenuFragment menuFragment = new MenuFragment();
                    menuFragment.setArguments(arguments);

                    // Depending on configuration if landscape, we switch only fragment container 2, if horizontal we change general one fragment container
                    int orientation = getResources().getConfiguration().orientation;
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        transaction.replace(R.id.fragment_container2, statisticsFragment);
                        transaction.commit();
                    } else {
                        transaction.replace(R.id.fragment_container, statisticsFragment);
                        transaction.commit();
                    }
                }


            }
        });

        // Initlialize back button
        b1 = v.findViewById(R.id.backButton);


        // Set back button listener to switch fragment in main container in portrait mode
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuFragment menuFragment = new MenuFragment();
                menuFragment.setArguments(arguments);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, menuFragment);
                transaction.commit();
            }
        });

        return v;
    }

}
