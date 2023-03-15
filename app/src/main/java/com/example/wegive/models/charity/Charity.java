package com.example.wegive.models.charity;

public class Charity {
    public Charity(String title, String content, String imageUrl, String creatorName, String creatorEmail, String date, String charityUrl, String country) {

        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.creatorName = creatorName;
        this.creatorEmail = creatorEmail;
        this.date = date;
        this.charityUrl = charityUrl;
        this.country = country;

    }

    private String title;
    private String content;
    private String imageUrl;
    private String creatorName;
    private String creatorEmail;
    private String date;
    private String charityUrl;
    private String country;


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public String getDate() {
        return date;
    }

    public String getCharityUrl() {
        return charityUrl;
    }

    public String getCountry() {
        return country;
    }
}
