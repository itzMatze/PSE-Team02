package edu.kit.mensameet.client.viewmodel;

import androidx.lifecycle.LiveData;

import edu.kit.mensameet.client.model.User;

public class UserViewModel extends MensaMeetViewModel {
    public UserViewModel(LiveData<User> user, boolean editable) {

    }

    public LiveData<User> loadUser() {
        return null;
    }

    public void saveUser(LiveData<User> user) {

    }
}
