package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.RequestUtil;
import edu.kit.mensameet.client.view.MensaMeetItem;

/**
 * Handler for a GroupItem.
 */
public class GroupItemHandler extends MensaMeetItemHandler {

    private Group group;
    private MensaMeetItem.DisplayMode displayMode;

    /**
     * Constructor.
     *
     * @param group The Group object.
     * @param displayMode Item display mode.
     */
    public GroupItemHandler(Group group, MensaMeetItem.DisplayMode displayMode) {
        this.group = group;
        this.displayMode = displayMode;

    }

    /**
     * Method to send a join request to the server for the group.
     */
    public void joinGroup() {

        try {

            RequestUtil.addUserToGroup(group.getToken(), MensaMeetSession.getInstance().getUser().getToken());

        } catch (RequestUtil.RequestException e) {

            eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.GROUP_JOIN_FAILED));
            return;

        }

        MensaMeetSession.getInstance().getUser().setGroupToken(group.getToken());
        group.getUsers().add(MensaMeetSession.getInstance().getUser());
        MensaMeetSession.getInstance().setChosenGroup(group);
        eventLiveData.setValue(new Pair<String, StateInterface>(null, State.GROUP_JOINED));

    }

    /**
     * Method for deleting a group on the server.
     */
    public void deleteGroup() {

        try {

            RequestUtil.deleteGroup(group.getToken(), MensaMeetSession.getInstance().getUser().getToken());

        } catch (RequestUtil.RequestException e) {

            eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.GROUP_DELETE_FAILED));
            return;
        }

        if (MensaMeetSession.getInstance().getUser().getGroupToken().equals(group.getToken())) {
            MensaMeetSession.getInstance().getUser().setGroupToken(null);
        }
        group = null;

        eventLiveData.setValue(new Pair<String, StateInterface>(null, State.GROUP_DELETED));
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public enum State implements StateInterface {
        GROUP_JOINED, GROUP_JOIN_FAILED, GROUP_DELETED, GROUP_DELETE_FAILED, DEFAULT
    }
}
