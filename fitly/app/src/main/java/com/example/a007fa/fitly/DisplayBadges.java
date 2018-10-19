package com.example.a007fa.fitly;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

    }
}
