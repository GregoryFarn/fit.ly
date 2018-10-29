package com.example.a007fa.fitly;

import java.util.ArrayList;

public class Users {
    private ArrayList<User> users = new ArrayList<User>();

    public Users(ArrayList<User> users) {
        this.users = users;
    }

    public int size() { return this.users.size(); }
}
