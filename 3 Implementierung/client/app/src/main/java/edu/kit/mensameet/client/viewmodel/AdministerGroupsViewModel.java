package edu.kit.mensameet.client.viewmodel;

import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.kit.mensameet.client.model.Group;

public class AdministerGroupsViewModel extends MensaMeetViewModel {
    public LiveData<List<Group>> loadGroups() {
        return null;
    }

    public void deleteGroups(LiveData<List<Group>> groups) {

    }
}
