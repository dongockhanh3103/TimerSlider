package com.example.ngockhanh.custompathcomponent.Models;

import java.util.Date;

/**
 * Created by Ngoc Khanh on 11/8/2017.
 */

public class Program {
    String title;
    Date timeStarting;
    Date timeEnding;
    String image;
    String imgChannel;
    public Program() {
    }


    public Program(String title, String image, String imgChannel, Date timeStarting, Date timeEnding) {
        this.title = title;
        this.image = image;
        this.imgChannel = imgChannel;
        this.timeStarting = timeStarting;
        this.timeEnding = timeEnding;
    }


    public String getImgChannel() {
        return imgChannel;
    }
    public void setImgChannel(String imgChannel) {
        this.imgChannel = imgChannel;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

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


}
