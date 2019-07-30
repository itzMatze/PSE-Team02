package edu.kit.mensameet.client.model;

import android.content.Context;

import androidx.annotation.StringRes;

import java.util.HashMap;
import java.util.Map;

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

    public static Gender valueOfString(Context context, String string) {
        for (Gender e : values()) {
            if (context.getResources().getString(e.id) == string) {
                return e;
            }
        }
        return null;
    }
}