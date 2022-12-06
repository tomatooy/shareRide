package edu.uga.cs.shareride;

public class riderPoints {
    private String email;
    private Integer points;
    private String userID;

    public riderPoints() {
        this.email = null;
        this.points = null;
        this.userID = null;
    }

    public riderPoints(String email, Integer points, String userID) {
        this.email = email;
        this.points = points;
        this.userID = userID;
    }

    public String getEmail() { return email; }
    public Integer getPoints() { return points; }
    public String getUserID() { return userID; }

    public void setEmail(String email) { this.email = email; }
    public void setPoints(Integer points) { this.points = points; }
    public void serUserID(String userID) { this.userID = userID; }
}
