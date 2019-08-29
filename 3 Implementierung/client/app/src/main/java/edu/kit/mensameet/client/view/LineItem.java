package edu.kit.mensameet.client.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MealLines;
import edu.kit.mensameet.client.viewmodel.LineItemHandler;
import edu.kit.mensameet.client.viewmodel.StateInterface;

/**
 * The representation of a single line.
 */
public class LineItem extends MensaMeetItem<Line> {

    private LineItemHandler handler;

    /**
     * Constructor.
     *
     * @param context   Context of the parent.
     * @param displayMode   Item display mode.
     * @param objectData The Line.
     */
    public LineItem(Context context, DisplayMode displayMode, Line objectData) {

        super(context, displayMode, objectData);

        handler = new LineItemHandler(objectData);
        super.initializeHandler(handler);
    }

    @Override
    public View createView() {

        final int BIGGER_FONT_SIZE = context.getResources().getInteger(R.integer.font_size_medium);
        final int SMALLER_FONT_SIZE = context.getResources().getInteger(R.integer.font_size_small);

        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setLayoutParams(WIDTH_MATCH_PARENT);

        int leftPadding = context.getResources().getInteger(R.integer.item_standard_padding_left_px);
        int topPadding = context.getResources().getInteger(R.integer.item_standard_padding_top_px);
        int rightPadding = context.getResources().getInteger(R.integer.item_standard_padding_right_px);
        int bottomPadding = context.getResources().getInteger(R.integer.item_standard_padding_bottom_px);

        view.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);

        // Element: name
        TextView nameField = (TextView)createTextField(R.string.field_name, WIDTH_MATCH_PARENT, BIGGER_FONT_SIZE);
        // TextView is the parent class of EditText and includes it
        nameField.setTypeface(nameField.getTypeface(), Typeface.BOLD);
        view.addView(nameField);

        // Element: meals
        view.addView(createTextField(R.string.field_meals, WIDTH_MATCH_PARENT, SMALLER_FONT_SIZE));

        return view;
    }

    @Override
    public void fillObjectData() {

        Line objectData = handler.getObjectData();

        if(objectData == null) {
            return;
        }

        fillTextField(R.string.field_name, context.getResources().getString(MealLines.valueOf(objectData.getMealLine()).getId()));

        String mealsText = "";
        for (int i = 0; i < objectData.getMeals().length; i++) {
            mealsText += objectData.getMeals()[i] + "\n\n";
        }

        fillTextField(R.string.field_meals, mealsText);


    }
}
