package edu.kit.mensameet.client.model;

import java.util.List;

public class MensaMeetSession {
    private static MensaMeetSession instance = new MensaMeetSession();
    private List<User> allUsers;
    private List<Group> allGroups;
    private MensaData mensaData;
    private User user;
    private List<Line> chosenLines;
    private MensaMeetTime chosenTime;
    private Group chosenGroup;

    private MensaMeetSession() {}

    public static MensaMeetSession getInstance() {
        if (instance == null) {
            instance = new MensaMeetSession();
        }
        return instance;
    }

    public void setAllUsers(List<User> allUsers) {
        this.allUsers = allUsers;
    }

    public void setAllGroups(List<Group> allGroups) {
        this.allGroups = allGroups;
    }

    public void setMensaData(MensaData mensaData) {
        this.mensaData = mensaData;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setChosenLines(List<Line> chosenLines) {
        this.chosenLines = chosenLines;
    }

    public void setChosenTime(MensaMeetTime chosenTime) {
        this.chosenTime = chosenTime;
    }

    public void setChosenGroup(Group chosenGroup) {
        this.chosenGroup = chosenGroup;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public List<Group> setAllGroups() {
        return allGroups;
    }

    public MensaData setMensaData() {
        return mensaData;
    }

    public User setUser() {
        return user;
    }

    public List<Line> setChosenLines() {
        return chosenLines;
    }

    public MensaMeetTime setChosenTime() {
        return chosenTime;
    }

    public Group setChosenGroup() {
        return chosenGroup;
    }
}