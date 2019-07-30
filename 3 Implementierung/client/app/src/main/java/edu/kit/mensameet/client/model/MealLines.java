package edu.kit.mensameet.client.model;

import android.content.Context;

import androidx.annotation.StringRes;

import edu.kit.mensameet.client.view.R;

/**
 * Enum of mensa lines.
 */
public enum MealLines {
    LINE_ONE(R.string.line_one),
    LINE_TWO(R.string.line_two),
    LINE_THREE(R.string.line_three),
    LINE_FOUR_FIVE(R.string.line_four_five),
    CUTLET_BAR(R.string.line_cutlet_bar),
    LINE_SIX(R.string.line_six),
    LATE_LINE(R.string.line_late_line),
    CURRY_LINE(R.string.line_curry_line),
    CAFETARIA(R.string.line_cafeteria),
    CAFETARIA_LATE(R.string.line_cafetaria_late),
    PIZZA_LINE(R.string.line_pizza_line),
    PIZZA_LINE_PASTA(R.string.line_pizza_line_pasta),
    PIZZA_LINE_SALAD(R.string.line_salad);

    public final @StringRes
    int id;

    private MealLines(@StringRes int id) {
        this.id = id;
    }

    public static MealLines valueOfId(@StringRes int id) {
        for (MealLines e : values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

    public static MealLines valueOfString(Context context, String string) {
        for (MealLines e : values()) {
            if (context.getResources().getString(e.id) == string) {
                return e;
            }
        }
        return null;
    }
}
