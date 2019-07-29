package edu.kit.mensameet.client.model;

import androidx.annotation.StringRes;

import edu.kit.mensameet.client.view.R;

/**
 * Genders. Including string ids for android.
 */
public enum Gender {
    MALE(R.string.gender_male),
    FEMALE(R.string.gender_female),
    OTHER(R.string.gender_other);

    public final @StringRes
    int id;

    private Gender(@StringRes int id) {
        this.id = id;
    }

    public static Gender valueOfId(@StringRes int id) {
        for (Gender e : values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}