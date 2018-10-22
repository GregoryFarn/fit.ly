package com.example.a007fa.fitly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.Arrays;

public class DisplayBadges extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_badges);
        final ArrayList<Badge> badgeArraylist= new ArrayList<Badge>();
        ArrayList<String> testStrings= new ArrayList<String>(Arrays.asList(new String[] {"One","Two","Three","Four"}));
       // LinearLayout parent= (LinearLayout) findViewById(R.id.my_root);

        for(int i=0; i<10; i++)
        {
            Badge badgeTest= new Badge("small", false);
            if(i%7==0)
                badgeTest.setTypeOfBadge("big");
            badgeTest.setCompleted(true);
            badgeArraylist.add(badgeTest);

        }
        ArrayAdapter<Badge> badgeAdapter =
                new ArrayAdapter<Badge>(this, 0, badgeArraylist) {
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
                        if(currentBadge.typeOfBadge.equals("small"))
                            badgeImage.setImageResource(R.drawable.starbadge);
                        else
                            badgeImage.setImageResource(R.drawable.bigbadge);
                        return convertView;

                    }
                };
        ListView badgeList = new ListView(this);
        setContentView(badgeList);
        badgeList.setAdapter(badgeAdapter);

        bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_FITLY);
        intentFilter.addAction(ACTION_ENDDAY);
        bManager.registerReceiver(bReceiver, intentFilter);

    }

    static final String ACTION_FITLY = "com.fitly.action.FITLY";
    static final String ACTION_ENDDAY = "com.fitly.action.ENDDAY";

    LocalBroadcastManager bManager;

    private BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };
}
