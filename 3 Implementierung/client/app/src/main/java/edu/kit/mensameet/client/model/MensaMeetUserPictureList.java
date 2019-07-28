package edu.kit.mensameet.client.model;

import android.media.Image;

import edu.kit.mensameet.client.view.R;

public class MensaMeetUserPictureList {
    private static MensaMeetUserPictureList instance = new MensaMeetUserPictureList();
    private MensaMeetUserPicture[] pictures;

    private MensaMeetUserPictureList() {
        pictures = new MensaMeetUserPicture[]{
                new MensaMeetUserPicture(0, R.drawable.arrow_left),
                new MensaMeetUserPicture(1, R.drawable.arrow_right),
                new MensaMeetUserPicture(1, R.drawable.common_google_signin_btn_text_light_normal),
                new MensaMeetUserPicture(1, R.drawable.burger),
                new MensaMeetUserPicture(1, R.drawable.common_full_open_on_phone),
        };
    }

    public static MensaMeetUserPictureList getInstance() {
        return instance;
    }

    public MensaMeetUserPicture getPictureById(int id) {

        for(MensaMeetUserPicture picture : pictures) {
            if (picture.getPictureId() == id) {
                return picture;
            }
        }

        return null;
    }

    public MensaMeetUserPicture getDefaultPicture() {
        return pictures[0];
    }

    public MensaMeetUserPicture[] getPictures() {
        return pictures;
    }


}
