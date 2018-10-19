package com.example.a007fa.fitly;

public class Badge {

    String typeOfBadge;
    Boolean completed;
    String BadgeMessage;
    String imageURL;
    Badge(String type, Boolean comp)
    {
        typeOfBadge=type;
        completed=comp;
        if(typeOfBadge.equals("small"))
            BadgeMessage="Congratulations on getting a small badge!";

    }
    public String getBadgeMessage() {
        return BadgeMessage;
    }

    public void setBadgeMessage(String badgeMessage) {
        BadgeMessage = badgeMessage;
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

    public void setCompleted(Boolean completed) {
        this.completed = completed;
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
