package edu.kit.mensameet.client.model;

//nicht festgelegt
import java.util.Date;

public class User {
    private static User instance = new User();
    private String token = "";
    private String username = "";
    private String motto = "";
    private Date birthDate;
    private Gender gender;
    private Subject subject;
    private Status status;
    private int profilePictureId;

    public static User getInstance() {
        return instance;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setmotto(String motto) {
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

    public String getUsername() {
        return username;
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

    public void clear() {
        instance = new User();
    }
}
