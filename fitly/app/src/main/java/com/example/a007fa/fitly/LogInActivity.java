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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LogInActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText userEmail;
    EditText userPassword;
    String email=" ";
    String password=" ";

    public void userLogIn()
    {
        Log.d("test on click", email+" "+password);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("test on complete", "onClick:1 ");
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Sign IN", "signInWithEmail:success");
                            // Navigate to MainActivity
                            Intent openDashboard= new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(openDashboard);
                        } else {
                            Log.d("test on fail", email+" "+password);
                            Log.d("failed", "signInWithEmail:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                            }
                            else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                Toast.makeText(getApplicationContext(), "Invalid user", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_log_in);
        Button logIn= (Button) findViewById(R.id.btn_login);
        userEmail=(EditText) findViewById(R.id.input_email);
        userPassword= (EditText)findViewById(R.id.input_password);
        mAuth= FirebaseAuth.getInstance();
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=userEmail.getText().toString();
                password=userPassword.getText().toString();
                userLogIn();
                Log.d("test on vli", "onClick: ");
                

            }
        });
        TextView signUp= (TextView) findViewById(R.id.link_signup);
        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });


    }



}
