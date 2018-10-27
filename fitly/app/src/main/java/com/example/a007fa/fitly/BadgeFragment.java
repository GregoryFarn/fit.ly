package com.example.a007fa.fitly;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class BadgeFragment extends Fragment {

    static final String ACTION_FITLY = "com.fitly.action.FITLY";
    static final String ACTION_ENDDAY = "com.fitly.action.ENDDAY";
    static final String ACTION_BADGEPAGE = "com.fitly.action.BADGEPAGE";
    static final String ACTION_BADGELIST = "com.fitly.action.BADGELIST";

    public BadgeFragment() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_badge, container, false);

        bManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BADGEPAGE);
        bManager.registerReceiver(bReceiver, intentFilter);

        Intent intent = new Intent(getActivity(), fitlyHandler.class);
        intent.setAction(ACTION_BADGELIST);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

        return view;
    }

    LocalBroadcastManager bManager;

    private BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ACTION_BADGEPAGE)){
                // Inflate the layout for this fragment
                
                final ArrayList<Badge> badgeArraylist= new ArrayList<Badge>();
                ArrayList<String> testStrings= new ArrayList<String>(Arrays.asList(new String[] {"One","Two","Three","Four"}));
                // LinearLayout parent= (LinearLayout) findViewById(R.id.my_root);

       /* for(int i=0; i<10; i++)
        {
            Badge badgeTest= new Badge("small", false);
            if(i%7==0)
                badgeTest.setTypeOfBadge("big");
            badgeTest.setCompleted(true);
            badgeArraylist.add(badgeTest);

        }*/
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
                                if(currentBadge.typeOfBadge.equals("small"))
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
    };

}