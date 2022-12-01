package edu.uga.cs.shareride;

/**
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
*/

public class Ride {
    private Boolean isOffer;
    private Boolean isAccepted;
    private Boolean isConfirmed;
    private String driverID;
    private Integer pointCost;
    private String destLocation;
    private String startLocation;
    private String date;

    public Ride() {
        this.isOffer = null;
        this.isAccepted = false;
        this.isConfirmed = false;
        this.driverID = null;
        this.pointCost = null;
        this.destLocation = null;
        this.startLocation = null;
        this.date = null;
    }

    public Ride(Boolean offer, Integer points, String destination, String start, String time) {
        this.isOffer = offer;
        this.pointCost = points;
        this.destLocation = destination;
        this.startLocation = start;
        this.date = time;
    }

    // getters
    public Boolean getIsOffer() {
        return isOffer;
    }
    public Boolean getIsAccepted() {
        return isAccepted;
    }
    public Boolean getIsConfirmed() {
        return isConfirmed;
    }
    public String getDriverID() {
        return driverID;
    }
    public Integer getPointCost() {
        return pointCost;
    }
    public String getDestLocation() {
        return destLocation;
    }
    public String getStartLocation() {
        return startLocation;
    }
    public String getDate() { return date; }

    // setters
    public void setIsOffer(Boolean isOffer) {
        this.isOffer = isOffer;
    }
    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }
    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }
    public void setPointCost(Integer pointCost) {
        this.pointCost = pointCost;
    }
    public void setDestLocation(String destLocation) {
        this.destLocation = destLocation;
    }
    public void setStartLocation(String pickupLocation) {
        this.startLocation = pickupLocation;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
