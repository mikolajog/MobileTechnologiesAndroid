package com.agh.expenseapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class EarnFragment extends Fragment {
    Button b1, b2;
    DatabaseHelper db;
    EditText et1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_earn, container, false);

        Bundle bundle = this.getArguments();
        final String user = bundle.get("user").toString();

        final Bundle arguments = new Bundle();
        arguments.putString("user", user);
        et1 = v.findViewById(R.id.amountCash1);
        et1.addTextChangedListener(new NumberTextWatcher(et1));


        db = new DatabaseHelper(getContext());

        b2 = v.findViewById(R.id.earnSomeMoneyButton);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertPositiveBalance(user, et1.getText().toString(), "Incoming");
                // validating fields and adding to DB
                StatisticsFragment statisticsFragment = new StatisticsFragment();
                statisticsFragment.setArguments(arguments);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.expenseLayout, statisticsFragment);
                transaction.commit();
                Toast.makeText(getContext(), "Congrats on earning money!", Toast.LENGTH_LONG).show();
            }
        });

        b1  = v.findViewById(R.id.backButton);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuFragment menuFragment = new MenuFragment();
                menuFragment.setArguments(arguments);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.expenseLayout, menuFragment);
                transaction.commit();
            }
        });

        return v;
    }

}
