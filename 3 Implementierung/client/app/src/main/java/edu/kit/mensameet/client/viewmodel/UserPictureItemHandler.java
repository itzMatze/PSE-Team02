package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.MensaMeetUserPicture;
import edu.kit.mensameet.client.model.MensaMeetUserPictureList;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.RequestUtil;
import edu.kit.mensameet.client.view.MensaMeetItem;
import edu.kit.mensameet.client.view.UserPictureAdapter;

/**
 * Handler for a UserPictureItem.
 */
public class UserPictureItemHandler extends MensaMeetItemHandler<MensaMeetUserPicture> {

    /**
     * Constructor.
     *
     * @param userPicture The MensaMeetUserPicture object.
     */
    public UserPictureItemHandler(MensaMeetUserPicture userPicture) {
        super(userPicture);
    }

    public MensaMeetUserPicture[] getPictures() {
        return MensaMeetUserPictureList.getInstance().getPictures();
    }

    public MensaMeetUserPicture getDefaultPicture() {
        return MensaMeetUserPictureList.getInstance().getDefaultPicture();
    }

    public enum State implements StateInterface {

    }
}
