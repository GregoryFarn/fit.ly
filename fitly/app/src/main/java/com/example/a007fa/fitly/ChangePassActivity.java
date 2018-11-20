package com.example.a007fa.fitly;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassActivity extends AppCompatActivity {

    private EditText editPassword;
    private EditText editConfirmPassword;
    private String newPassword = null;
    private String confirmPassword =null;
    private FirebaseUser mUser;
    //private FirebaseAuth mAuth;
    private ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        editPassword = (EditText) findViewById(R.id.edit_password);
        editConfirmPassword = (EditText) findViewById(R.id.input_confirm_password);
        dialog = new ProgressDialog(this);
       // mAuth = FirebaseAuth.getInstance();

        Button btn = (Button) findViewById(R.id.btn_edit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword = editPassword.getText().toString();
                confirmPassword = editConfirmPassword.getText().toString();
                Log.w("edit" ,editPassword.getText().toString());
                Log.w("edit1", newPassword);
                Log.w("edit1",confirmPassword);
                Log.w("edit", Boolean.toString(newPassword.isEmpty()));
                if((newPassword == null || newPassword.isEmpty()) && (confirmPassword == null || confirmPassword.isEmpty())){
                    Toast.makeText(getApplicationContext(), "Please enter new password and confirm password", Toast.LENGTH_SHORT).show();
                }
                else if(newPassword == null || newPassword.isEmpty()){
                    Log.w("edit","ab1");
                    Toast.makeText(getApplicationContext(), "Please enter new password", Toast.LENGTH_SHORT).show();
                }
                else if((confirmPassword == null || confirmPassword.isEmpty())){
                    Toast.makeText(getApplicationContext(), "Please enter confirm password", Toast.LENGTH_SHORT).show();
                }
                else if (!newPassword.equals(confirmPassword)){
                    Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                }
                else{
                    changePassword(v);
                }

            }
        });
    }

    public void changePassword(View v){
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUser != null){
            dialog.setMessage("Changing password...");
            dialog.show();
            mUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>(){
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Password has been changed", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), DashboardFragment.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        dialog.dismiss();
                           if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                               Toast.makeText(getApplicationContext(), ((FirebaseAuthWeakPasswordException) task.getException()).getReason(), Toast.LENGTH_LONG).show();
                            }
                            else{
                               Toast.makeText(getApplicationContext(),"Password could not be changed", Toast.LENGTH_SHORT).show();
                           }
                    }
                }
            });
        }
    }
}
