package com.example.a007fa.fitly;

import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText userEmail;
    EditText userPassword;
    EditText userConfirmedPassword;
    EditText userName;
    EditText userHeight;
    EditText userAge;
    EditText userWeight;
    boolean pedometerAllowed;
    boolean notificationsAllowed;
    String email=" ";
    String password=" ";
    String confirmedPassword = "";
    String name=" ";
    int age;
    int height; // height in cm
    int weight;

    public boolean checkPasswordsMatch(String password, String confirmedPassword) {
        if (password.equals(confirmedPassword)) return true;
        return false;
    }

    public boolean fieldsEmpty(String name, String email, String password, String confirmedPassword) {
        if (name.equals("") || email.equals("") || password.equals("") || confirmedPassword.equals("")) {
            return true;
        }
        return false;
    }

    public void signUp()
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Success", "createUserWithEmail:success");
                            //add new user to database
                            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                            DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid());
                            User setU= new User(name,email);
                            mUserRef.setValue(setU);

                            // add other details
                            mUserRef.child("age").setValue(age);
                            mUserRef.child("height").setValue(height);
                            mUserRef.child("weight").setValue(weight);
                            mUserRef.child("notificationsOn").setValue(notificationsAllowed);
                            mUserRef.child("pedometerOn").setValue(pedometerAllowed);
                            mUserRef.child("numConsecutiveDays").setValue(0);
                          
                            // Navigate to MainActivity
                            Intent openDashboard= new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(openDashboard);

                        } else {
                            Log.w("Failure", "createUserWithEmail:failed");
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "User is already registered.", Toast.LENGTH_SHORT).show();
                            }
                            else if (task.getException() instanceof FirebaseAuthEmailException) {
                                Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_LONG).show();
                            }
                            else if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(getApplicationContext(), "Password is too weak", Toast.LENGTH_LONG).show();
                            }
                            else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userEmail=(EditText) findViewById(R.id.input_email);
        userPassword= (EditText)findViewById(R.id.input_password);
        userConfirmedPassword = (EditText) findViewById(R.id.input_confirm_password);
        userName=(EditText) findViewById(R.id.input_name);
        mAuth= FirebaseAuth.getInstance();

        String age_choices[] = {"Age", "17", "18", "19", "20", "21", "22", "23", "24", "25"};
        final Spinner age_spinner = (Spinner) findViewById(R.id.age_spinner);

        ArrayAdapter<String> ageArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, age_choices);
        ageArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        age_spinner.setAdapter(ageArrayAdapter);

        String weight_choices[] = {"Weight", "100 - 109", "110 - 119", "120 - 129", "130 - 139", "140 - 149", "150 - 159", "160 - 169", "170 - 179", "180 - 189", "190 - 199"};
        final Spinner weight_spinner = (Spinner) findViewById(R.id.weight_spinner);

        ArrayAdapter<String> weightArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, weight_choices);
        weightArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        weight_spinner.setAdapter(weightArrayAdapter);

        String height_choices[] = {"Height", "4\' 10\"", "4\' 11\"", "5\' 0\"", "5\' 1\"", "5\' 2\"", "5\' 3\"", "5\' 4\"", "5\' 5\"", "5\' 6\"",  "5\' 7\"", "5\' 8\"", "5\' 9\"", "5\' 10\"", "5\' 11\"", "6\' 0\"", "6\' 1\"", "6\' 2\""};
        final Spinner height_spinner = (Spinner) findViewById(R.id.height_spinner);

        ArrayAdapter<String> heightArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, height_choices);
        heightArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        height_spinner.setAdapter(heightArrayAdapter);

        Button signUp = (Button) findViewById(R.id.btn_signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password=userPassword.getText().toString();
                confirmedPassword = userConfirmedPassword.getText().toString();

                if (!fieldsEmpty(name, email, password, confirmedPassword)) {
                    if (checkPasswordsMatch(password, confirmedPassword)) {
                        email=userEmail.getText().toString();
                        name=userName.getText().toString();

                        String a = age_spinner.getSelectedItem().toString();
                        String w = weight_spinner.getSelectedItem().toString();
                        String h = height_spinner.getSelectedItem().toString();

                        if (!a.equals("Age")) {
                            age = Integer.parseInt(a);
                        }
                        else {
                            age = 20; // default age for calculations
                        }

                        if (!w.equals("Weight")) {
                            String temp = w.substring(0, 3);
                            weight = Integer.parseInt(temp);
                        }
                        else {
                            weight = 120;
                        }

                        if (!h.equals("Height")) {
                            int feet = Integer.parseInt(h.substring(0, 1));
                            int inches = Integer.parseInt(h.substring(3, h.length() - 1));
                            Log.d("feet", Integer.toString(feet));
                            Log.d("inches", Integer.toString(inches));

                            height = (int) Math.ceil(((feet * 12) + inches) * 2.54); // convert to cm
                        }
                        else {
                            height = 175;
                        }

                        pedometerAllowed = ((CheckBox) findViewById(R.id.check_pedometer_permission)).isChecked();
                        notificationsAllowed = ((CheckBox) findViewById(R.id.check_notifications_permission)).isChecked();

                        Log.d("Name", name);
                        Log.d("Email", email);
                        Log.d("Password", confirmedPassword);
                        Log.d("Age", Integer.toString(age));
                        Log.d("Weight", Integer.toString(weight));
                        Log.d("Height", Integer.toString(height));
                        Log.d("pedometer", Boolean.toString(pedometerAllowed));
                        Log.d("Notifications", Boolean.toString(notificationsAllowed));

                        signUp();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView logIn= (TextView) findViewById(R.id.link_login);
        logIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the logIn activity
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
            }
        });
    }
}
