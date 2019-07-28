package edu.kit.mensameet.client.viewmodel;

import androidx.lifecycle.LiveData;

import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.view.MensaMeetItem;

public class UserItemHandler extends MensaMeetItemHandler {
    public UserItemHandler(LiveData<User> user, MensaMeetItem.DisplayMode viewMode) {

    }

    public LiveData<User> loadUser() {
        return null;
    }
}
