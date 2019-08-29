package edu.kit.mensameet.client.model;

import android.media.Image;

import edu.kit.mensameet.client.view.R;

/**
 * Singleton to save the ids and resource ids of the user pictures.
 */
public class MensaMeetUserPictureList {
    private static MensaMeetUserPictureList instance = new MensaMeetUserPictureList();
    private MensaMeetUserPicture[] pictures;

    private MensaMeetUserPictureList() {
        pictures = new MensaMeetUserPicture[]{
                new MensaMeetUserPicture(0, R.drawable.mm_burger),
                new MensaMeetUserPicture(1, R.drawable.mm_broccoli),
                new MensaMeetUserPicture(2, R.drawable.mm_cheese),
                new MensaMeetUserPicture(3, R.drawable.mm_pizza),
                new MensaMeetUserPicture(4, R.drawable.mm_carrot),
                new MensaMeetUserPicture(5, R.drawable.mm_cutlet),
        };
    }

    /**
     * Gets the singleton.
     *
     * @return The singleton.
     */
    public static MensaMeetUserPictureList getInstance() {
        return instance;
    }

    /**
     * Gets a picture by its id.
     *
     * @param id Id of the picture.
     * @return The picture.
     */
    public MensaMeetUserPicture getPictureById(int id) {

        for (MensaMeetUserPicture picture : pictures) {
            if (picture.getPictureId() == id) {
                return picture;
            }
        }

        return null;
    }

    /**
     * Gets the default picture.
     *
     * @return Default picture.
     */
    public MensaMeetUserPicture getDefaultPicture() {
        return pictures[0];
    }

    /**
     * Gets all pictures.
     *
     * @return All pictures.
     */
    public MensaMeetUserPicture[] getPictures() {
        return pictures;
    }
}
