package com.example.a007fa.fitly;

import android.os.Parcelable;
import android.os.Parcel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.ArrayList;
import java.io.Serializable;

public class Badge implements Serializable {
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
        else
        {
            if(this.completed==true)
            {
                SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");
                badgeMessage="You got a big badge on "+format.format(achievedDate.getTime());
            }

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

   /* public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Badge createFromParcel(Parcel in) {
            return new Badge(in);
        }

        public Badge[] newArray(int size) {
            return new Badge[size];
        }

    };

    public Badge(Parcel in){
        this.typeOfBadge = in.readString();
        this.completed = in.readByte() !=0;
        this.badgeMessage =  in.readString();
        this.achievedDate.setTimeInMillis(in.readLong());
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(typeOfBadge);
        dest.writeByte((byte) (completed ? 1 : 0));
        dest.writeString(badgeMessage);
        dest.writeLong(achievedDate.getTimeInMillis());
    }

    public int describeContents(){
        return 0;
    }*/
}
