package com.example.a007fa.fitly;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText userEmail;
    EditText userPassword;
    EditText userName;
    String email=" ";
    String password=" ";
    String name=" ";

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
                                Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_LONG).show();
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
        userName=(EditText) findViewById(R.id.input_name);
        mAuth= FirebaseAuth.getInstance();
        Button signUp = (Button) findViewById(R.id.btn_signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=userEmail.getText().toString();
                password=userPassword.getText().toString();
                name=userName.getText().toString();
                signUp();

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
