package com.myjavaprojects.utility;

import java.util.ArrayList;

public class Parent {
    private final String heading;
    private final ArrayList<Child> children;

    public Parent(String heading, ArrayList<Child> children) {
        this.heading = heading;
        this.children = children;
    }

    public String getHeading() {
        return heading;
    }

    public ArrayList<Child> getChildren() {
        return children;
    }
}
