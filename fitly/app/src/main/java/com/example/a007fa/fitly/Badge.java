package com.example.a007fa.fitly;

public class Badge {
    String typeOfBadge;
    Boolean completed;
    Badge(String type, Boolean comp)
    {
        typeOfBadge=type;
        completed=comp;

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
