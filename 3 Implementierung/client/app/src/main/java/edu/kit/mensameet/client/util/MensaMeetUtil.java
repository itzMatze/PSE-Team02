package edu.kit.mensameet.client.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import edu.kit.mensameet.client.view.SpinnerItem;

public class MensaMeetUtil {

    public static final LinearLayout.LayoutParams WIDTH_MATCH_PARENT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    public static void applyStyle(View v, int styleResId) {
        Context context = v.getContext();

        if (v instanceof TextView) {
            ((TextView) v).setTextAppearance(context, styleResId);
        }

        int[] attrs = new int[]{
                android.R.attr.layout_marginLeft,     // 0
                android.R.attr.layout_marginTop,    // 1
                android.R.attr.layout_marginRight,   // 2
                android.R.attr.layout_marginBottom,   // 3
                android.R.attr.paddingLeft, //4
                android.R.attr.paddingTop, //5
                android.R.attr.paddingRight, //6
                android.R.attr.paddingBottom //7
        };

        final TypedArray arr = context.obtainStyledAttributes(styleResId, attrs);
        try {

            ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(WIDTH_MATCH_PARENT);
            }

            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) layoutParams;

            lp.setMargins(arr.getDimensionPixelSize(0, 0), // Left
                    arr.getDimensionPixelSize(1, 0), // Top
                    arr.getDimensionPixelSize(2, 0),  // Right
                    arr.getDimensionPixelSize(3, 0)); // Bottom

            v.setLayoutParams(lp);

            v.setPadding(arr.getInt(4, 0), // Left
                    arr.getInt(5, 0), // Top
                    arr.getInt(6, 0), // Right
                    arr.getInt(7, 0)); // Bottom

            v.requestLayout();
        } finally {
            arr.recycle();
        }
    }

    public static int getIndexInSpinner(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {

            Object item = spinner.getItemAtPosition(i);
            if (item != null &&
                    item instanceof SpinnerItem) {

                if (((SpinnerItem)item).getValue() == null) {
                    if (value == null) {
                        return i;
                    }
                } else if (((SpinnerItem)item).getValue().equals(value)) {
                    return i;
                }

            }

        }

        return 0;
    }

}
