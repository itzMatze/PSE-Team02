package edu.kit.mensameet.client.viewmodel;

import android.arch.lifecycle.LiveData;

import edu.kit.mensameet.client.model.Group;

public class GroupItemHandler extends MensaMeetItemHandler {
    public GroupItemHandler(LiveData<Group> group, boolean bigView) {

    }

    public LiveData<Group> loadGroup() {
        return null;
    }

    public void joinGroup() {

    }
}
