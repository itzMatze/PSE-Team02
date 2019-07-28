package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import androidx.lifecycle.LiveData;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.SingleLiveEvent;
import edu.kit.mensameet.client.view.GroupItem;
import edu.kit.mensameet.client.view.MensaMeetItem;

public class GroupItemHandler extends MensaMeetItemHandler {

    private Group group;
    private MensaMeetItem.DisplayMode displayMode;

    public GroupItemHandler(Group group, MensaMeetItem.DisplayMode displayMode) {
        this.group = group;
        this.displayMode = displayMode;

    }

    public LiveData<Group> loadGroup() {
        return null;
    }

    public void joinGroup() {

        //int error = addUserToGroup(MensaMeetSession.getInstance().getUser(), group);

        //if (error == 0) {
        // MensaMeetSession.getInstance().setChosenGroup(group);...

        MensaMeetSession.getInstance().setChosenGroup(group);
        uiEventLiveData.setValue(new Pair<MensaMeetItemHandler, StateInterface>(this, State.GROUP_JOINED));

    }

    public void deleteGroup() {

        //int error = deleteGroup(group);

        uiEventLiveData.setValue(new Pair<MensaMeetItemHandler, StateInterface>(this, State.GROUP_DELETED));
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public enum State implements StateInterface {
        GROUP_JOINED, GROUP_DELETED, DEFAULT
    }
}
