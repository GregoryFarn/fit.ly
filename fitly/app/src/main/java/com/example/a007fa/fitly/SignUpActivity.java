package com.example.a007fa.fitly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button signUp= (Button) findViewById(R.id.btn_signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userName= (EditText) findViewById(R.id.input_name);
                EditText userEmail= (EditText) findViewById(R.id.input_email);
                EditText userPassword= (EditText) findViewById(R.id.input_password);
                Boolean authorized=true;
                Log.e("test signup", "???????");
                if(userName.getText().toString().isEmpty())
                {
                    //authorized=false;
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please enter a user name",
                            Toast.LENGTH_SHORT);

                    toast.show();

                }
                else if (userEmail.getText().toString().isEmpty())
                {
                    //authorized=false;
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please enter an email address",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }
                else if(userPassword.getText().toString().isEmpty())
                {
                    //authorized=false;
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please enter a password",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }
                else {
                    if (authorized == true) {
                        //add them to the database

                        Intent openDashboard = new Intent(view.getContext(), MainActivity.class);
                        startActivity(openDashboard);
                    }
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
