package com.example.ngockhanh.custompathcomponent.Models;

/**
 * Created by Ngoc Khanh on 11/8/2017.
 */

public class Program {
    String title;
    public Program(){};
    public Program(String title, int color){
        this.title=title;
        this.color=color;
    };

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

    int color;

}
