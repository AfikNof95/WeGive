package com.example.wegive.models.charity;

import com.google.firebase.firestore.IgnoreExtraProperties;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "project", strict = false)
@IgnoreExtraProperties
public class ProjectsResponse {


    public ProjectsResponse() {

    }


    public ProjectsResponse(String title, String activities, String imageLink, String country, String projectLink, String contactName, String contactEmail,String approvedDate) {

        this.title = title;
        this.activities = activities;
        this.imageLink = imageLink;
        this.country = country;
        this.projectLink = projectLink;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.approvedDate = approvedDate;
    }


    @Element(name = "title", required = false)
    private String title;

    @Element(name = "activities", required = false)
    private String activities;

    @Element(name = "imageLink", required = false)
    private String imageLink;

    @Element(name = "country", required = false)
    private String country;

    @Element(name = "projectLink", required = false)
    private String projectLink;


    @Element(name = "contactName", required = false)
    private String contactName;

    @Element(name = "contactEmail", required = false)
    private String contactEmail;

    @Element(name = "approvedDate", required = false)
    private String approvedDate;

    public String getTitle() {
        return title;
    }

    public String getActivities() {
        return activities;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getCountry() {
        return country;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getApprovedDate() {
        return approvedDate;
    }
}
