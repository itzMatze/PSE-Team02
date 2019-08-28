package edu.kit.mensameet.client.model;

import android.content.Context;

import androidx.annotation.StringRes;

import edu.kit.mensameet.client.view.R;

/**
 * Enumeration for the professional status of a user.
 */
public enum Status implements IdEnum {
    PROFESSOR(R.string.status_professor),
    COLLEGE_STUDENT(R.string.status_college_student),
    APPRENTICE(R.string.status_apprentice),
    STUDENT(R.string.status_student),
    OTHER(R.string.status_other);

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

    public static Status valueOfString(Context context, String string) {
        for (Status e : values()) {
            if (context.getResources().getString(e.id) == string) {
                return e;
            }
        }
        return null;
    }

    @Override
    public int getId() {
        return id;
    }}