package edu.kit.mensameet.client.viewmodel;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.kit.mensameet.client.model.MensaMeetUserPicture;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.view.MensaMeetItem;

public class UserPictureViewHandler {
    public UserPictureViewHandler(LiveData<User> user, int profilePictureId, MensaMeetItem.DisplayMode viewMode) {

    }

    public LiveData<List<MensaMeetUserPicture>> loadAllUserPictures() {
        return null;
    }

    public LiveData<MensaMeetUserPicture> loadUserPicture() {
        return null;
    }

    public void savePicture(int userPictureId) {

    }
}
