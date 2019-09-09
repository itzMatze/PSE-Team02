package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.RequestUtil;
import edu.kit.mensameet.client.view.MensaMeetItem;

/**
 * Handler for a GroupItem.
 */
public class GroupItemHandler extends MensaMeetItemHandler<Group> {

    /**
     * Constructor.
     *
     * @param group The Group object.
     */
    public GroupItemHandler(Group group) {
        super(group);
    }

    /**
     * Method to send a join request to the server for the group.
     */
    public void joinGroup() {

        // Join group.

        try {

            RequestUtil.addUserToGroup(objectData.getToken(), MensaMeetSession.getInstance().getUser().getToken());

        } catch (RequestUtil.RequestException e) {

            eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.GROUP_JOIN_FAILED));
            return;

        }

        // Reload user.

        User userUpdate = null;

        try {

            userUpdate = RequestUtil.getUser(MensaMeetSession.getInstance().getUser().getToken(), MensaMeetSession.getInstance().getUser().getToken());

        } catch (RequestUtil.RequestException e) {

            eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.RELOADING_USER_FAILED));
            return;

        }

        MensaMeetSession.getInstance().setUser(userUpdate);

        eventLiveData.setValue(new Pair<String, StateInterface>(null, State.GROUP_JOINED));

    }

    /**
     * Method for deleting a group on the server.
     */
    public void deleteGroup() {

        try {

            RequestUtil.deleteGroup(objectData.getToken(), MensaMeetSession.getInstance().getUser().getToken());

        } catch (RequestUtil.RequestException e) {

            eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.GROUP_DELETION_FAILED));
            return;
        }

        // If deleted group was the current user's group.

        if (MensaMeetSession.getInstance().getUser().getGroupToken() != null && MensaMeetSession.getInstance().getUser().getGroupToken().equals(objectData.getToken())) {

            // Reload user.

            User userUpdate = null;

            try {

                userUpdate = RequestUtil.getUser(MensaMeetSession.getInstance().getUser().getToken(), MensaMeetSession.getInstance().getUser().getToken());

            } catch (RequestUtil.RequestException e) {

                eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.RELOADING_USER_FAILED));
                return;

            }

            MensaMeetSession.getInstance().setUser(userUpdate);
        }

        objectData = null;

        eventLiveData.setValue(new Pair<String, StateInterface>(null, State.GROUP_DELETED));
    }

    public enum State implements StateInterface {
        GROUP_JOINED, GROUP_JOIN_FAILED, GROUP_DELETED, GROUP_DELETION_FAILED, RELOADING_USER_FAILED
    }
}
