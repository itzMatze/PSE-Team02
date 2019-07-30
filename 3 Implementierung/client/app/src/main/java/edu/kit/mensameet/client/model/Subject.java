package edu.kit.mensameet.client.model;

import androidx.annotation.StringRes;

import edu.kit.mensameet.client.view.R;

/**
 * Enumeration for university subjects.
 */
public enum Subject {
    INFORMATICS(R.string.subject_it),
    ECONOMY(R.string.subject_economy),
    MATH(R.string.subject_math),
    PHYSICS(R.string.subject_physics),
    CHEMISTRY(R.string.subject_chemistry),
    BIOLOGY(R.string.subject_biolgy),
    LINGUISTIC(R.string.subject_linguistic);

    public final @StringRes
    int id;

    private Subject(@StringRes int id) {
        this.id = id;
    }

    public static Subject valueOfId(@StringRes int id) {
        for (Subject e : values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}