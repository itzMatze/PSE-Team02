package edu.kit.mensameet.client.viewmodel;

import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.kit.mensameet.client.model.User;

public class UserListHandler extends MensaMeetListHandler {
    public UserListHandler(LiveData<List<User>> userList) {

    }
}
