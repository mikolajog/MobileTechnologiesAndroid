package com.agh.expenseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    /*
    Login activity class enabling login and going to Expense Activity or Registering user (going to Register Activity)
     */

    EditText e1, e2;
    Button b1, b2;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // We set buttons listeners
        super.onCreate(savedInstanceState);
        // Set view
        setContentView(R.layout.activity_login);

        // Initialize db, edittexts, buttons properly
        db = new DatabaseHelper(this);
        e1 = (EditText) findViewById(R.id.login);
        e2 = (EditText) findViewById(R.id.password);
        b1 = (Button) findViewById(R.id.loginButton);
        b2 = (Button) findViewById(R.id.registerButton);

        // Set listener on on register button and switch activity
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        // Set listener on login button - verify
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if correct in db
                String email = e1.getText().toString();
                String password = e2.getText().toString();
                Boolean chkemailpass = db.checkEmailAndPasswordIsCorrect(email, password);

                // Handle login
                if (chkemailpass) {
                    Toast.makeText(getApplicationContext(), "Successful login", Toast.LENGTH_SHORT).show();
                    // Stop music
                    stopService(new Intent(Login.this, MusicService.class));

                    Intent i = new Intent(Login.this, ExpenseActivity.class);

                    // Pass username to expense activity
                    i.putExtra("user", e1.getText().toString());

                    // Clear fields in case you log off later
                    e1.setText("");
                    e2.setText("");
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        e1 = (EditText) findViewById(R.id.login);
        e2 = (EditText) findViewById(R.id.password);
    }

}
