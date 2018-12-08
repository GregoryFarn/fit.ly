package com.example.a007fa.fitly;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
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
    private int age;
    private int height;
    private int weight;
    private boolean isPermissionOn = true;
    private String email;
    private TextView changePass;
    private TextView notificanationManage;
    private TextView ageView;
    private TextView heightView;
    private TextView weightView;
    private TextView nameDisplay;
    private TextView emailDiplay;
    private TextView activityView;
    private Switch permissionSwitch;
    private FirebaseUser mUser;
    static final String ACTION_PERMISSION= "com.fitly.action.PERMISSION";
    static final String ACTION_STOP = "com.fitly.action.STOP";
  //  private FirebaseAuth.AuthStateListener mAuthUser;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.fragment_profile, container, false);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid());

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
             heightView = view.findViewById(R.id.user_height);
             weightView = view.findViewById(R.id.user_weight);

             if(snapshot.child("age").getValue() != null){
                 age = Integer.valueOf(snapshot.child("age").getValue(Integer.class));
                 ageView.setText(Integer.toString(age));
             }
             else{
                 ageView.setText("");
             }
             if(snapshot.child("weight").getValue() != null){
                 weight = Integer.valueOf(snapshot.child("weight").getValue(Integer.class));
                 weightView.setText(Integer.toString(weight));
             }
             else{
                 weightView.setText("");
             }
             if(snapshot.child("height").getValue() != null){
                 height = Integer.valueOf(snapshot.child("height").getValue(Integer.class));
                 heightView.setText(Integer.toString(height));
             }
             else{
                 heightView.setText("");
             }
//             if(snapshot.child("pedometerOn").getValue() != null){
//                 isPermissionOn = (Boolean)snapshot.child("pedometerOn").getValue();
//             }
//             Log.d(TAG,"profileName:" + email);
//             Log.d(TAG,"profileName:" + age);
//             Log.d(TAG,"profileName: " +  name);
             }
             @Override
             public void onCancelled(DatabaseError databaseError) {
             Log.d(TAG,"profileName: " +  "error");
             }
         });

         viewActivityRecord(view);
         setAccelerometerOn(view);
         changePass(view);
         logOutButton(view);
         return view;
    }

    public void setAccelerometerOn(View view){
        permissionSwitch = (Switch) view.findViewById(R.id.permission_switch);
        permissionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG,"profileName: " +  permissionSwitch.isChecked());
                Intent intent  = new Intent(getActivity(), fitlyHandler.class);
                intent.putExtra("permission", permissionSwitch.isChecked());
                intent.setAction(ACTION_PERMISSION);
                LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).sendBroadcast(intent);
                getActivity().setResult(Activity.RESULT_OK);

            }
        });
    }
//    public void setNotification(View view){
//        notificanationManage = view.findViewById(R.id.edit_noti);
//        notificanationManage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Dialog dialog = new Dialog(getActivity());
//                dialog.setContentView(R.layout.notification_dialog);
//                dialog.setTitle("hi");
//                dialog.show();
//
//            }
//        });
//    }
    public void changePass(View view){
        changePass = view.findViewById(R.id.change_password);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // getActivity().finish();
                Intent intent = new Intent(getActivity(), ChangePassActivity.class);
                startActivity(intent);
            }
        });

    }
    public void viewActivityRecord(View view) {
        activityView = view.findViewById(R.id.view_activity);
        activityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getActivity().finish();
                Intent intent = new Intent(getActivity(), DisplyActivityRecord.class);
                startActivity(intent);
            }
        });
    }

        public void logOutButton(View view){
        Button logoutButton= (Button) view.findViewById(R.id.logout_btn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // FirebaseAuth.getInstance().signOut();
              //  getActivity().finish();

                // Intent intent1 = new Intent(getActivity().getApplicationContext(), MainActivity.class);
//                Intent intent = new Intent(getActivity(), LogInActivity.class);
//                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//               int num = getActivity().databaseList().length;
//                Log.d(TAG, "onClick: " + num);



                /*Intent intent = new Intent(getContext(), fitlyHandler.class);
                intent.setAction(ACTION_STOP);
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);*/
               restartApp();
            }
        });
    }
    private void restartApp() {
        Intent intent = new Intent(getActivity().getApplicationContext(),LogInActivity.class);
        int mPendingIntentId = 1234;
        PendingIntent mPendingIntent = PendingIntent.getActivity(getActivity().getApplicationContext(), mPendingIntentId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager restart = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        restart.set(AlarmManager.RTC, System.currentTimeMillis() + 10, mPendingIntent);
        System.exit(0);
    }

}
