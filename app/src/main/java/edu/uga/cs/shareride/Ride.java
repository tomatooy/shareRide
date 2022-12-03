package edu.uga.cs.shareride;


public class Ride {
    private String key;;
    private Integer pointCost;
    private String destLocation;
    private String startLocation;
    private String date;

    public Ride() {
        this.key = null;
        this.pointCost = null;
        this.destLocation = null;
        this.startLocation = null;
        this.date = null;
    }

    public Ride(Integer points, String start,String destination, String time) {
        //this.isOffer = offer;
        this.pointCost = points;
        this.destLocation = destination;
        this.startLocation = start;
        this.date = time;
    }

    // getters
    public String getKey() {
        return key;
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
    public void setKey(String key) {
        this.key = key;
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
