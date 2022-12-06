package edu.uga.cs.shareride;


public class Ride {
    private String key;;
    private Integer pointCost;
    private String destLocation;
    private String startLocation;
    private String date;

    private String posterID;
    private String driverEmail;
    private String riderEmail;

    public Ride() {
        this.key = null;
        this.pointCost = null;
        this.destLocation = null;
        this.startLocation = null;
        this.date = null;
        this.posterID = null;
        this.driverEmail = null;
        this.riderEmail = null;
    }

    public Ride(Integer points, String start,String destination, String time, String posterID, String driverEmail, String riderEmail) {
        this.pointCost = points;
        this.destLocation = destination;
        this.startLocation = start;
        this.date = time;
        this.posterID = posterID;
        this.driverEmail = driverEmail;
        this.riderEmail = riderEmail;
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
    public String getPosterID() { return posterID; }
    public String getDriverEmail() { return driverEmail;}
    public String getRiderEmail() { return riderEmail; }

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
    public void setPosterID(String posterID) { this.posterID = posterID; }
    public void setDriverEmail(String driverEmail) { this.driverEmail = driverEmail; }
    public void setRiderEmail(String riderEmail) { this.riderEmail = riderEmail; }
}
