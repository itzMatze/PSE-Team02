package edu.kit.mensameet.client.model;

import java.util.List;

public class MensaMeetSession {
    private List<User> allUsers;
    private List<Group> allGroups;
    private MensaData mensaData;
    private User user;
    private List<Line> chosenLines;
    private MensaMeetTime chosenTime;
    private Group chosenGroup;
}
