package com.example.a007fa.fitly;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    View view;
    private String name;
    private double age;
    private String email;
    private TextView changePass;
    private TextView nofiManage;
    private TextView ageView;
    private TextView nameDisplay;
    private TextView emailDiplay;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
         DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid());

        view = inflater.inflate(R.layout.fragment_profile, container, false);
         mUserRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot snapshot) {
             name = (String)snapshot.child("displayName").getValue();
             email = (String)snapshot.child("email").getValue();


             nameDisplay = view.findViewById(R.id.user_name);
             nameDisplay.setText(name);

             emailDiplay = view.findViewById(R.id.user_email);
             emailDiplay.setText(email);
             ageView = view.findViewById(R.id.user_age);
             Log.d(TAG,"profileName:" + email);
             Log.d(TAG,"profileName: " +  name);
             }
             @Override
             public void onCancelled(DatabaseError databaseError) {
             Log.d(TAG,"profileName: " +  "error");
             }
         });

        changePass = view.findViewById(R.id.change_password);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePassActivity.class);
                startActivity(intent);
            }
        });

        nofiManage = view.findViewById(R.id.edit_noti);
        nofiManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(getActivity());

                dialog.setContentView(R.layout.notification_dialog);
                dialog.setTitle("hi");
                dialog.show();

            }
        });


        logOutButton(view);
        return view;


    }




    public void logOutButton(View view){
        Button logoutButton= (Button) view.findViewById(R.id.logout_btn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                startActivity(intent);
            }

        });

    }




}
