package com.agh.expenseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    DatabaseHelper db;
    EditText e1, e2, e3;
    Button b1, b2;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-zA-Z])" +      //any letter
                    ".{4,}" +               //at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DatabaseHelper(this);
        e1 = (EditText)findViewById(R.id.email);
        e2 = (EditText)findViewById(R.id.pass);
        e3 = (EditText)findViewById(R.id.cpass);
        b1 = (Button)findViewById(R.id.register);
        b2 = (Button) findViewById(R.id.loginButton);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEmail() & validatePassword() & validateConfirmPassword()) {
                    String s1 = e1.getText().toString();
                    String s2 = e2.getText().toString();
                    Boolean chkemail = db.chkemail(s1);
                    if (chkemail) {
                        boolean insert = db.insertUser(s1, s2);
                        if (insert) {
                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Register.this, Login.class);
                            startActivity(i);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            else {
                Toast.makeText(getApplicationContext(), "Invalid data inserted", Toast.LENGTH_SHORT).show();
            }
        }
        });
    }

    private boolean validateEmail() {
        String emailInput = e1.getText().toString().trim();
        if (emailInput.isEmpty()) {
            e1.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            e1.setError("Please enter a valid email address");
            return false;
        } else {
            e1.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = e2.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            e2.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            e2.setError("Password must contain at least one lowercase letter, one uppercase letter, one numeric digit");
            return false;
        } else {
            e2.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String passwordInput = e2.getText().toString().trim();
        String cpasswordInput = e3.getText().toString().trim();
        if(cpasswordInput.isEmpty()){
            e3.setError("Field can't be empty");
            return false;
        } else if (!cpasswordInput.equals(passwordInput)) {
            e3.setError("Passwords do not match");
            return false;
        } else {
            e1.setError(null);
            return true;
        }
    }


}
