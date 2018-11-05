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

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Button logIn= (Button) findViewById(R.id.btn_login);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userEmail;
                EditText password;
                userEmail=(EditText) findViewById(R.id.input_email);
                password=(EditText) findViewById(R.id.input_password);
                Boolean authorized=true;
                Log.e("onClick: ",password.getText().toString());
                if(userEmail.getText().toString().isEmpty())
                {

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please enter an email address",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }
                else if (password.getText().toString().equals(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please enter password",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }
                else
                {
                    //get the user ID from the database
                    if(authorized==true)
                    {
                        Intent openDashboard= new Intent(view.getContext(), MainActivity.class);
                        startActivity(openDashboard);

                    }

                }

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
