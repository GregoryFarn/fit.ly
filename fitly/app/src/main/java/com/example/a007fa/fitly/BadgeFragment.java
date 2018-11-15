package com.example.a007fa.fitly;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BadgeFragment extends Fragment {

    static final String ACTION_BADGEPAGE = "com.fitly.action.BADGEPAGE";
    static final String ACTION_BADGELIST = "com.fitly.action.BADGELIST";

    public BadgeFragment() {
        // Required empty public constructor
        /*mUserRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                ActivityRecord post = dataSnapshot.getValue(ActivityRecord.class);
                Log.d("Testing onload", "onDataChange: Loading data");
                //
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.d("Not loading", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });*/
    }


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_badge, container, false);
        final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference();
        Log.d("Testing onCreate", "onDataChange: Loading data");
       /* bManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BADGEPAGE);
        bManager.registerReceiver(bReceiver, intentFilter);

        Intent intent = new Intent(getActivity(), fitlyHandler.class);
        intent.setAction(ACTION_BADGELIST);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);*/
        mUserRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                String userName= dataSnapshot.child("users").child(mUser.getUid()).child("displayName").getValue(String.class);
                Log.d("Testing onload", "onDataChange: Loading data "+userName);
                GenericTypeIndicator<List<ActivityRecord>> typeIndicator = new GenericTypeIndicator<List<ActivityRecord>>() {};
                ArrayList<ActivityRecord> activityRecords=null;
                dataSnapshot.child("users").child(mUser.getUid()).child("activityRecords").getValue(GenericTypeIndicator.class);
               /* if(activityRecords==null)
                {
                    Log.d("Testing ArrayList", "onDataChange: Loading ArrayList");

                }*/
                //
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.d("Not loading", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        return view;
    }

    /*
    LocalBroadcastManager bManager;

    private BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ACTION_BADGEPAGE)){
                // Inflate the layout for this fragment
                
                final ArrayList<Badge> badgeArraylist= ((badgeWrapper)intent.getSerializableExtra("badgeList")).getBadges();

                ArrayAdapter<Badge> badgeAdapter =
                        new ArrayAdapter<Badge>(getActivity(), 0, badgeArraylist) {
                            @Override
                            public View getView(int position,
                                                View convertView,
                                                ViewGroup parent) {
                                Badge currentBadge= badgeArraylist.get(position);
                                // Inflate only once
                                if(convertView == null) {
                                    convertView = getLayoutInflater()
                                            .inflate(R.layout.list_item, null, false);
                                }
                                TextView badgeMessage=(TextView)convertView.findViewById(R.id.message);
                                ImageView badgeImage=(ImageView)convertView.findViewById(R.id.badge_picture);
                                badgeMessage.setText(currentBadge.getBadgeMessage());
                                if(currentBadge.completed==false)
                                    badgeImage.setImageResource(R.drawable.greystar);
                                else if(currentBadge.typeOfBadge.equals("small"))
                                    badgeImage.setImageResource(R.drawable.starbadge);
                                else
                                    badgeImage.setImageResource(R.drawable.bigbadge);
                                return convertView;
                            }
                        };
                ListView badgeList = view.findViewById(R.id.list);
                badgeList.setAdapter(badgeAdapter);
            }
        }
    };*/

}