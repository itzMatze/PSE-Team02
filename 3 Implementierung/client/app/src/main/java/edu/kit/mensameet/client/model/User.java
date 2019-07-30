package edu.kit.mensameet.client.model;

import java.util.Date;

/**
 * Representation of an user.
 */
public class User {

    private String token = "";
    private String name = "";
    private String motto = "";
    private Date birthdate;
    private Gender gender;
    private Subject subject;
    private Status status;
    private int profilePictureId;
    private Boolean isAdmin = false;
    private String groupToken;

    public void setToken(String token) {
        this.token = token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
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

    public Date getBirthdate() {
        return birthdate;
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

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * Sets the user to adminstrator status.
     *
     * @param isAdmin Whether the user should be administrator.
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getGroupToken() {
        return groupToken;
    }

    public void setGroupToken(String groupToken) {
        this.groupToken = groupToken;
    }
}
