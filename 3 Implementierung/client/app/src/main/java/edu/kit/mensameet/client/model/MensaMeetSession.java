package edu.kit.mensameet.client.model;

import android.content.Context;
import android.os.Handler;
import android.util.Pair;
import android.widget.Toast;

import java.util.List;

import edu.kit.mensameet.client.util.RequestUtil;
import edu.kit.mensameet.client.view.GroupJoinedActivity;
import edu.kit.mensameet.client.view.HomeActivity;
import edu.kit.mensameet.client.view.R;
import edu.kit.mensameet.client.viewmodel.LoginViewModel;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

/**
 * Singleton for the data of a mensa meet session.
 */
public class MensaMeetSession {

    private static MensaMeetSession instance = new MensaMeetSession();

    private MensaData mensaData;
    private User user;
    private List<Line> chosenLines;
    private MensaMeetTime chosenTime;
    private Group chosenGroup;
    private Group createdGroup;
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

    /**
     * Checks whether the user data is incomplete.
     *
     * @return Whether the user data is incomplete.
     */
    public Boolean userDataIncomplete() {

        // The user profile is incomplete

        return (user == null || user.getName() == null || user.getName().equals("") || user.getStatus() == null);

    }

    /**
     * Checks whether the data of the created group is incomplete.
     *
     * @return Whether the data of the created group is incomplete.
     */
    public Boolean createdGroupDataIncomplete(Context context) {

        // The created group data is incomplete

        return (createdGroup.getName() == null ||
                createdGroup.getName().equals("") ||
                createdGroup.getMotto() == null ||
                createdGroup.getMotto().equals("") ||
                createdGroup.getMeetingDate() == null ||
                createdGroup.getLine() == null ||
                createdGroup.getLine().equals("") ||
                createdGroup.getMaxMembers() < 1 ||
                createdGroup.getMaxMembers() > context.getResources().getInteger(R.integer.max_member_max));

    }

    public Line[] getMensaLines() {

        if (mensaData != null) {
            return mensaData.getLines();
        }

        return null;
    }

    public void initialize(User user) throws RequestUtil.RequestException {

        MensaData mensaData = RequestUtil.getMensaData();
        setMensaData(mensaData);
        setUser(user);
        setChosenLines(null);
        setChosenTime(null);
        setCreatedGroup(null);
        setUserToShow(null);

    }

    public void invalidate() {

        setMensaData(null);
        setUser(null);
        setChosenLines(null);
        setChosenTime(null);
        setCreatedGroup(null);
        setUserToShow(null);
    }

}
