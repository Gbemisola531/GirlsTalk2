package com.example.sai.girlstalk.models;

public class User
{
    private String username;
    private String password;
    private double rating;
    private Integer numOfRating;
    private UserProfile profile;

    public User() {}

    public User(String username, String password, double rating, Integer numOfRating,UserProfile profile) {
        this.username = username;
        this.password = password;
        this.rating = rating;
        this.numOfRating = numOfRating;
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Integer getNumOfRating() {
        return numOfRating;
    }

    public void setNumOfRating(Integer numOfRating) {
        this.numOfRating = numOfRating;
    }
}
