package edu.kit.mensameet.client.model;

import java.util.List;

/**
 * Singleton for the data of a mensa meet session.
 */
public class MensaMeetSession {

    private static MensaMeetSession instance = new MensaMeetSession();

    private MensaData mensaData;
    private User user;
    private List<Line> chosenLines;
    private MensaMeetTime chosenTime;
    private Group receivedGroups;
    private Group chosenGroup;
    private Group createdGroup;
    private String userToken;
    private User userToShow;

    private MensaMeetSession() {
    }

    /**
     * Returns the singleton.
     *
     * @return The singleton.
     */
    public static MensaMeetSession getInstance() {
        return instance;
    }

    /**
     * Sets the mensa data containing the lines.
     *
     * @param mensaData THe mensa data.
     */
    public void setMensaData(MensaData mensaData) {
        this.mensaData = mensaData;
    }

    /**
     * Sets the current user.
     *
     * @param user Current user.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets the lines chosen by the current user.
     *
     * @param chosenLines Lines chosen.
     */
    public void setChosenLines(List<Line> chosenLines) {
        this.chosenLines = chosenLines;
    }

    /**
     * Sets the time (interval) chosen by the current user.
     *
     * @param chosenTime Time (interval).
     */
    public void setChosenTime(MensaMeetTime chosenTime) {
        this.chosenTime = chosenTime;
    }

    /**
     * Sets the group the current user successfully joined.
     *
     * @param chosenGroup Joined group.
     */
    public void setChosenGroup(Group chosenGroup) {
        this.chosenGroup = chosenGroup;
    }

    public MensaData getMensaData() {
        return mensaData;
    }

    public User getUser() {
        if (user == null) {
            user = new User();
        }
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

    public Group getCreatedGroup() {
        return createdGroup;
    }

    /**
     * Sets a locally saved group sketch the current user has not sent to the server yet. Once successfully sent,
     * it becomes the chosen group.
     *
     * @param createdGroup
     */
    public void setCreatedGroup(Group createdGroup) {
        this.createdGroup = createdGroup;
    }

    public String getUserToken() {
        return userToken;
    }

    /**
     * Sets the user token.
     *
     * @param userToken User token.
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public User getUserToShow() {
        return userToShow;
    }

    /**
     * Sets the user to be shown in the profile view of ShowUserActivity.
     *
     * @return User to be shown.
     */
    public void setUserToShow(User userToShow) {
        this.userToShow = userToShow;
    }

    public Group getReceivedGroups() {
        return receivedGroups;
    }

    /**
     * Saves the groups received from the server after a query.
     *
     * @return Received groups.
     */
    public void setReceivedGroups(Group receivedGroups) {
        this.receivedGroups = receivedGroups;
    }

    /**
     * Checks whether the user data is complete.
     *
     * @return Whether the user data is complete.
     */
    public Boolean userDataIncomplete() {

        // The user profile is incomplete

        return (user == null || user.getName() == null || user.getName() == "" || user.getStatus() == null);

    }

    public Line[] getMensaLines() {

        if (mensaData != null) {
            return mensaData.getLines();
        }

        return null;
    }
}
