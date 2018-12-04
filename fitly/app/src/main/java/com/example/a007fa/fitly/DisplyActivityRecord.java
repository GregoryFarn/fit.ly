package com.example.a007fa.fitly;

import android.app.Activity;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DisplyActivityRecord extends AppCompatActivity {
    private static final int LAYOUT = R.layout.activity_disply_record;

    private FirebaseUser mUser;

    private DatabaseReference mMessageReference;

    public ArrayList<ActRec> actRecs = new ArrayList<>();
    private ArrayList<IncompleteWorkout> incompleteWorkouts;
    private ExpandableListView parentListView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mMessageReference = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid()).child("activityRecords");
        ArrayList<ActRec> data = new ArrayList<>();
        parentListView = (ExpandableListView) findViewById(R.id.parentListView);

        //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference playersRef = rootRef.child("map").child("players");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    incompleteWorkouts = new ArrayList<>();

                   String date = (String) ds.child("date").getValue();

                   Boolean badgeAchieved;
                   if(ds.child("badgeAchieved").getValue() != null){
                       badgeAchieved = (Boolean) ds.child("badgeAchieved").getValue();
                   }else{
                       badgeAchieved = false;
                   }
                   int stepCount;
                   if(ds.child("stepCount").getValue() != null){
                       stepCount = Integer.valueOf(ds.child("stepCount").getValue(Integer.class));
                   }else{
                       stepCount = 0;
                    }
                    int totalCaloriesConsumed;
                    if(ds.child("totalCaloriesConsumed").getValue() != null){
                        totalCaloriesConsumed = Integer.valueOf(ds.child("totalCaloriesConsumed").getValue(Integer.class));
                    }else{
                        totalCaloriesConsumed = 0;
                    }

                   if(ds.child("incompleteWorkouts").getValue() != null){
                        for(DataSnapshot ids : ds.child("incompleteWorkouts").getChildren()){
                            String description = (String)ids.child("description").getValue();
                            String end = (String) ids.child("end").getValue();
                            String location = (String) ids.child("location").getValue();
                            String start = (String) ids.child("start").getValue();
                            String workoutName = (String) ids.child("workoutName").getValue();
                            Log.w("ABC", "location:" + ids.child("location").getValue());
                            incompleteWorkouts.add(new IncompleteWorkout(description, end, location, start , workoutName));
                        }
                    }
                    ActRec ar = new ActRec(date,stepCount, badgeAchieved, totalCaloriesConsumed);
                    ar.setIncomplete(incompleteWorkouts);

                    Log.w("ABC", "date:" + ar.toString());
                    actRecs.add(ar);

                    Log.w("ABC", "date1:" + actRecs.size());
                }
                Log.w("ABCD", "date:" + actRecs.size());
                DisplayActivityAdapter adapter = new DisplayActivityAdapter(getApplicationContext(),actRecs);
                parentListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };


        mMessageReference.addListenerForSingleValueEvent(eventListener);
        //if (!actRecs.isEmpty()) {
         //   Log.w("ABC", "date123:" + actRecs.size());

//        Log.w("ABC", "date2:" + Integer.valueOf(activityRecords.size()));
//        ActRec parentData1 = new ActRec("11/2/18", 26, false, 50);
//        parentData1.getIncomplete().add(new IncompleteWorkout("workout", "11/30", "los", "11/3", "fs"));
//        data.add(parentData1);
//        ActRec parentData2 = new ActRec("12/3/18", 44, false, 12);
//
//        parentData2.getIncomplete().add(new IncompleteWorkout("work1", "12/4/18", "CA", "12/5","sp"));
//        data.add(parentData2);

    }
}
