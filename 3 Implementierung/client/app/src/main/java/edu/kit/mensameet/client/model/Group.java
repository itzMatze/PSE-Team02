package edu.kit.mensameet.client.model;

//nicht festgelegt
import java.util.Date;
import java.util.List;

public class Group {
    private String id;
    private String name;
    private String motto;
    private int maxMembers;
    private String line;
    private Date meetingDate;
    private List<User> users;
}
