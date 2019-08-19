package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import androidx.lifecycle.LiveData;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.RequestUtil;
import edu.kit.mensameet.client.util.SingleLiveEvent;
import edu.kit.mensameet.client.view.GroupItem;
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

        if (RequestUtil.addUserToGroup(group.getToken(), MensaMeetSession.getInstance().getUser().getToken()) != null) {
            MensaMeetSession.getInstance().getUser().setGroupToken(group.getToken());
            group.getUsers().add(MensaMeetSession.getInstance().getUser());
            MensaMeetSession.getInstance().setChosenGroup(group);
            uiEventLiveData.setValue(new Pair<MensaMeetItemHandler, StateInterface>(this, State.GROUP_JOINED));
        } else {
            uiEventLiveData.setValue(new Pair<MensaMeetItemHandler, StateInterface>(this, State.GROUP_JOIN_FAILED));
        }

    }

    /**
     * Method for deleting a group on the server.
     */
    public void deleteGroup() {

        String response = RequestUtil.deleteGroup(group.getToken(), MensaMeetSession.getInstance().getUser().getToken());
        // todo: request util functions should send a success boolean and an error message
        // supposed deletion was successful:
        // if the group the current user joined was deleted

        if (MensaMeetSession.getInstance().getUser().getGroupToken().equals(group.getToken())) {
            MensaMeetSession.getInstance().getUser().setGroupToken(null);
        }
        group = null;

        uiEventLiveData.setValue(new Pair<MensaMeetItemHandler, StateInterface>(this, State.GROUP_DELETED));
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public enum State implements StateInterface {
        GROUP_JOINED, GROUP_JOIN_FAILED, GROUP_DELETED, DEFAULT
    }
}
