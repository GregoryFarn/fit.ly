package com.example.a007fa.fitly;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayBadges extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_badges);
        ArrayList<Badge> badgeArraylist= new ArrayList<Badge>();
        for(int i=0; i<10; i++)
        {
            Badge badgeTest= new Badge("small", true);

        }
        for(int i=0; i <10;i++)
        {
            LinearLayout parent= (LinearLayout) findViewById(R.id.parent);
            LinearLayout a = new LinearLayout(this);
            a.setOrientation(LinearLayout.HORIZONTAL);
            TextView text= new TextView(this);
            ImageView image= new ImageView(this);
            image.setImageResource(R.drawable.icon);
            a.addView(text);
            a.addView(image);
            parent.addView(a);

        }
    }
}
