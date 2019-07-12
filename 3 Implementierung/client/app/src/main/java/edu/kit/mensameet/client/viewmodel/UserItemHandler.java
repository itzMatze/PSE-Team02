package edu.kit.mensameet.client.viewmodel;

import android.arch.lifecycle.LiveData;
import edu.kit.mensameet.client.view.MensaMeetViewMode;
import edu.kit.mensameet.client.model.User;

public class UserItemHandler extends MensaMeetItemHandler {
    public UserItemHandler(LiveData<User> user, MensaMeetViewMode viewMode) {

    }

    public LiveData<User> loadUser() {
        return null;
    }
}
