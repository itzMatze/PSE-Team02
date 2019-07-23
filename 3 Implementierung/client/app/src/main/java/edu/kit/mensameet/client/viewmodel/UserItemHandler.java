package edu.kit.mensameet.client.viewmodel;

import androidx.lifecycle.LiveData;

import edu.kit.mensameet.client.view.MensaMeetDisplayMode;
import edu.kit.mensameet.client.model.User;

public class UserItemHandler extends MensaMeetItemHandler {
    public UserItemHandler(LiveData<User> user, MensaMeetDisplayMode viewMode) {

    }

    public LiveData<User> loadUser() {
        return null;
    }
}
