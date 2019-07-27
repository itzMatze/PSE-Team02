package edu.kit.mensameet.client.model;

import android.graphics.drawable.Drawable;
import android.media.Image;

import androidx.annotation.DrawableRes;

public class MensaMeetUserPicture {
    private @DrawableRes int resourceId;
    private int pictureId;

    public MensaMeetUserPicture(int pictureId, @DrawableRes int resourceId) {
        this.resourceId = resourceId;
        this.pictureId = pictureId;
    }

    public int getPictureId() {
        return pictureId;
    }

    public @DrawableRes int getResourceId() {
        return resourceId;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MensaMeetUserPicture)) {
            return false;
        }

        MensaMeetUserPicture that = (MensaMeetUserPicture) other;

        return (this.pictureId == that.pictureId);
    }
}
