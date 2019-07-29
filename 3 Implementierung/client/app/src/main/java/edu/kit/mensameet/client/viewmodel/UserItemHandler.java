package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import androidx.lifecycle.LiveData;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.SingleLiveEvent;
import edu.kit.mensameet.client.view.GroupItem;
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
        uiEventLiveData.setValue(new Pair<MensaMeetItemHandler, StateInterface>(this, State.SHOW_USER));
    }

    public void deleteUser() {

        //int error = deleteUser(user);

        uiEventLiveData.setValue(new Pair<MensaMeetItemHandler, StateInterface>(this, State.USER_DELETED));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public enum State implements StateInterface {
        USER_JOINED, USER_DELETED, SHOW_USER, DEFAULT
    }
}
