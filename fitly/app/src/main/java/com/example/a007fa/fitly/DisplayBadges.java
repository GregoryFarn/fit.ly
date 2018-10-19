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
            Badge badgeTest= new Badge("small", true);
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
                        badgeImage.setImageResource(R.drawable.icon);
                        return convertView;

                    }
                };
        ListView badgeList = new ListView(this);
        setContentView(badgeList);
        badgeList.setAdapter(badgeAdapter);
        /*for(int j=0; j <10; j++)
        {
            LinearLayout a = new LinearLayout(getApplicationContext());
            a.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            a.setOrientation(LinearLayout.HORIZONTAL);
            ImageView image= new ImageView(getApplicationContext());
            image.setImageResource(R.drawable.icon);
            image.getLayoutParams().height=100;
            image.getLayoutParams().width=100;
            TextView text= new TextView(getApplicationContext());
            String test="TESSSTINGGGGGGG";
            text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            text.setText(test);
            a.addView(text);
            a.addView(image);
            parent.addView(a);
            //parent.addView(text);
            //parent.addView(text);
            //parent.addView(text);

        }*/
    }
}
