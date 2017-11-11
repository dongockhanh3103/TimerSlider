package com.example.ngockhanh.custompathcomponent.Models;

import java.util.Date;

/**
 * Created by Ngoc Khanh on 11/8/2017.
 */

public class Program {
    String title;
    Date timeStarting;
    Date timeEnding;
    int color;

    public Program(){};
    public Program(String title, int color, Date timeStarting,Date timeEnding){
        this.title=title;
        this.color=color;
        this.timeStarting=timeStarting;
        this.timeEnding=timeEnding;
    };

    public Date getTimeStarting() {
        return timeStarting;
    }

    public void setTimeStarting(Date timeStarting) {
        this.timeStarting = timeStarting;
    }

    public Date getTimeEnding() {
        return timeEnding;
    }

    public void setTimeEnding(Date timeEnding) {
        this.timeEnding = timeEnding;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


}
