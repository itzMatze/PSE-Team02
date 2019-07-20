package edu.kit.mensameet.client.viewmodel;

import androidx.lifecycle.LiveData;
import java.util.List;
import edu.kit.mensameet.client.model.User;

public class AdministerUsersViewModel extends MensaMeetViewModel {
    public LiveData<List<User>> loadUsers() {
        return null;
    }

    public void deleteUsers(LiveData<List<User>> users) {

    }
}
