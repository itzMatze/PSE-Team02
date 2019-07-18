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

    public List<Group> getAllGroups() {
        return allGroups;
    }

    public MensaData getMensaData() {
        return mensaData;
    }

    public User getUser() {
        return user;
    }

    public List<Line> getChosenLines() {
        return chosenLines;
    }

    public MensaMeetTime getChosenTime() {
        return chosenTime;
    }

    public Group getChosenGroup() {
        return chosenGroup;
    }
}
