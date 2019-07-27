package edu.kit.mensameet.client.model;

import androidx.annotation.StringRes;

import edu.kit.mensameet.client.view.R;

public enum Status {
    MEAT(R.string.status_meat),
    VEGETARIAN(R.string.status_vegetarian),
    VEGAN(R.string.status_vegan),
    PESCETARIAN(R.string.status_pescetarian);

    public final @StringRes
    int id;

    private Status(@StringRes int id) {
        this.id = id;
    }

    public static Status valueOfId(@StringRes int id) {
        for (Status e : values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}