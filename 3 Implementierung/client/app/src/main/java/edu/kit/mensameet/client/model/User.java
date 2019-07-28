package edu.kit.mensameet.client.model;

import java.util.Date;

public class User {

    private String token = "";
    private String name = "";
    private String motto = "";
    private Date birthDate;
    private Gender gender;
    private Subject subject;
    private Status status;
    private int profilePictureId;

    public void setToken(String token) {
        this.token = token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setProfilePictureId(int profilePictureId) {
        this.profilePictureId = profilePictureId;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getMotto() {
        return motto;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public Subject getSubject() {
        return subject;
    }

    public Status getStatus() {
        return status;
    }

    public int getProfilePictureId() {
        return profilePictureId;
    }

}
