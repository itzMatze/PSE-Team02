package edu.kit.mensameet.client.viewmodel;

import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.kit.mensameet.client.model.Group;

public class GroupListHandler extends MensaMeetListHandler {
    public GroupListHandler(LiveData<List<Group>> groupList) {

    }
}
