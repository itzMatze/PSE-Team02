package edu.kit.mensameet.client.view;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;

import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.viewmodel.MensaMeetItemHandler;
import edu.kit.mensameet.client.viewmodel.StateInterface;

public class LineItem extends MensaMeetItem<Line> {

    public LineItem(Context context, MensaMeetDisplayMode displayMode, Line objectData) {
        //TODO: Put displayMode into MensaMeetItem as static subclass
        super(context, displayMode, objectData);
    }

    @Override
    protected void processStateChange(Pair<MensaMeetItemHandler, StateInterface> it) {

    }

    public class ItemElements {
        static final int NAME = 1;
        static final int MEALS = 2;
    }

    @Override
    public View createView() {

        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setLayoutParams(super.WIDTH_MATCH_PARENT);
        view.setPadding(30, 20, 30, 20);

        // Element: name
        view.addView(super.createTextField(ItemElements.NAME, super.WIDTH_MATCH_PARENT, super.BIG_TEXT_SIZE));

        // Element: meals
        view.addView(super.createTextField(ItemElements.MEALS, super.WIDTH_MATCH_PARENT, super.SMALL_TEXT_SIZE));

        return view;
    }

    @Override
    public void fillObjectData() {
        //TODO: since fillData is data-related, outsource it to viewmodel/handler
        super.fillTextField(ItemElements.NAME, super.objectData.getName());

        String mealsText = "";
        for (int i = 0; i < objectData.getMeals().length; i++) {
            mealsText += objectData.getMeals()[i].getName() + "\n";
        }

        super.fillTextField(ItemElements.MEALS, mealsText);
    }

}
