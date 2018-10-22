package com.example.a007fa.fitly;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import android.os.Parcelable;

public class Badge implements Parcelable {

    String typeOfBadge;
    Boolean completed;
    String badgeMessage;
    Calendar achievedDate;
    //Badge constructor
    Badge(String type, Boolean comp)
    {
        typeOfBadge=type;
        completed=comp;
        TimeZone timezone=TimeZone.getTimeZone("PST");
        achievedDate=Calendar.getInstance(timezone);
        if(typeOfBadge.equals("small"))
            badgeMessage="You got a small badge on";


    }
    public String getBadgeMessage() {
        return badgeMessage;
    }

    public void setBadgeMessage(String badgeMessage) {
        badgeMessage = badgeMessage;
    }
    public String getTypeOfBadge() {
        return typeOfBadge;
    }

    public void setTypeOfBadge(String typeOfBadge) {
        this.typeOfBadge = typeOfBadge;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean comp) {
        this.completed = comp;
        if(typeOfBadge.equals("small") && this.completed==true )
        {
            SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");
            badgeMessage="You got a small badge on "+format.format(achievedDate.getTime());
        }
    }

    //is this how we want this function to work?
    public void updateBadge(Integer steps, Integer time)
    {

        if(steps==1000 && time==3600)
        {
            completed=true;

        }
    }
}
