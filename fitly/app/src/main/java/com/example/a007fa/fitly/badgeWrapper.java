package com.example.a007fa.fitly;

import java.util.ArrayList;
import java.io.Serializable;

public class badgeWrapper implements Serializable{
    ArrayList<Badge> badges;

    public badgeWrapper(ArrayList<Badge> b) {
        badges = b;
    }
    public ArrayList<Badge> getBadges(){
        return badges;
    }
}
