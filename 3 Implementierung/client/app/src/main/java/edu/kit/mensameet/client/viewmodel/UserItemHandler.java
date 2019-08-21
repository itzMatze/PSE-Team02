package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.RequestUtil;
import edu.kit.mensameet.client.view.MensaMeetItem;

/**
 * Handler for a UserItem.
 */
public class UserItemHandler extends MensaMeetItemHandler {

    private User user;
    private MensaMeetItem.DisplayMode displayMode;

    /**
     * Constructor.
     *
     * @param user
     * @param displayMode
     */
    public UserItemHandler(User user, MensaMeetItem.DisplayMode displayMode) {
        this.user = user;
        this.displayMode = displayMode;

    }

    public void showUser() {
        MensaMeetSession.getInstance().setUserToShow(user);
        eventLiveData.setValue(new Pair<String, StateInterface>(null, State.SHOW_USER));
    }

    public void deleteUser() {

        // the current user cannot delete himself!
        if (MensaMeetSession.getInstance().getUser().getToken() != user.getToken()) {
            // todo: replace first argument with real firebase token
            try {

                RequestUtil.deleteUser(MensaMeetSession.getInstance().getUser().getToken(), user.getToken());

            } catch (RequestUtil.RequestException e) {

                // todo: handle this
                eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.USER_DELETION_FAILED));
                return;

            }

            user = null;

            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.USER_DELETED));
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public enum State implements StateInterface {
        USER_JOINED, USER_DELETED, USER_DELETION_FAILED, SHOW_USER, DEFAULT
    }
}
