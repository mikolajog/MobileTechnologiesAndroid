package com.agh.expenseapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SpendFragment extends Fragment {

    private Button b1, b2;
    private EditText et1;
    private Spinner spinner;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_spend, container, false);

        db = new DatabaseHelper(getContext());

        Bundle bundle = this.getArguments();
        final String user = bundle.get("user").toString();

        final Bundle arguments = new Bundle();
        arguments.putString("user", user);


        b1  = v.findViewById(R.id.backButton);
        et1 = v.findViewById(R.id.amountCash1);
        et1.addTextChangedListener(new NumberTextWatcher(et1));

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

        b2 = v.findViewById(R.id.earnSomeMoneyButton);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertNegativeBalance(user, et1.getText().toString(), spinner.getSelectedItem().toString());
                // validating fields and adding to DB
                StatisticsFragment statisticsFragment = new StatisticsFragment();
                statisticsFragment.setArguments(arguments);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.expenseLayout, statisticsFragment);
                transaction.commit();
                Toast.makeText(getContext(), "You've just spent money!", Toast.LENGTH_LONG).show();
            }
        });

        spinner = (Spinner) v.findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
            R.array.spend_purpose_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        return v;
    }


}
