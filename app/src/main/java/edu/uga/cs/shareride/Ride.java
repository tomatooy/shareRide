package edu.uga.cs.shareride;

import java.util.Date;

public class Ride {
    private String From;
    private String To;
    private String date;
    private int pointCost;

    public Ride(){
        this.From = null;
        this.To = null;
        this.date = null;
        this.pointCost = 0;
    }

    public Ride(String From,String To, String date, int cost){
        this.From = From;
        this.To = To;
        this.date = date;
        this.pointCost = cost;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFrom(String from) {
        From = from;
    }

    public void setPointCost(int pointCost) {
        this.pointCost = pointCost;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getDate() {
        return date;
    }

    public int getPointCost() {
        return pointCost;
    }

    public String getFrom() {
        return From;
    }

    public String getTo() {
        return To;
    }
}
