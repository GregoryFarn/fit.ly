package com.example.a007fa.fitly;


import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> f71eb930e8aabdf1a803aa64bec50580d957553f
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;
<<<<<<< HEAD
=======
import android.widget.TextView;
>>>>>>> User class
=======
>>>>>>> f71eb930e8aabdf1a803aa64bec50580d957553f


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
         DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid());

         view = inflater.inflate(R.layout.fragment_profile, container, false);
      //  Log.d(TAG, "mUser: " + name);
         //(R.layout.activity_log_in);
        Button logoutButton= (Button) view.findViewById(R.id.logout_btn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                startActivity(intent);
            }

        });
        TextView nameDisplay = (TextView)view.findViewById(R.id.user_name);
        //String name = (String) mUserRef.child("displayName");
        // mUserRef.child("displayName").
        // Log.d(TAG,"profileName: " +  mUserRef.child().getKey());
        nameDisplay.setText("jjjj");
        return view;


    }

}
