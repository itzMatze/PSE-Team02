package edu.kit.mensameet.client.model;

import android.graphics.drawable.Drawable;
import android.media.Image;

import androidx.annotation.DrawableRes;

/**
 * Encapsulation of a user picture, since future implementations (bitmoji) might be different.
 */
public class MensaMeetUserPicture {
    private @DrawableRes int resourceId;
    private int pictureId;

    /**
     * Constructor.
     *
     * @param pictureId The picture Id.
     * @param resourceId The android resource Id.
     */
    public MensaMeetUserPicture(int pictureId, @DrawableRes int resourceId) {
        this.resourceId = resourceId;
        this.pictureId = pictureId;
    }

    /**
     * Gets the picture id transmitted to the server.
     *
     * @return Picture id.
     */
    public int getPictureId() {
        return pictureId;
    }

    /**
     * Gets the picture resource id for android.
     *
     * @return Resource id.
     */
    public @DrawableRes int getResourceId() {
        return resourceId;
    }

    /**
     * Compare whether another picture is identical. This is the case when their ids are identical.
     *
     * @param other The other picture.
     * @return Identity.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MensaMeetUserPicture)) {
            return false;
        }

        MensaMeetUserPicture that = (MensaMeetUserPicture) other;

        return (this.pictureId == that.pictureId);
    }
}
