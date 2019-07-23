package edu.kit.mensameet.client.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import edu.kit.mensameet.client.model.User;

public class UserItem extends MensaMeetItem<User> {

    public UserItem(Context context, MensaMeetDisplayMode displayMode, User objectData) {
        //TODO: Put displayMode into MensaMeetItem as static subclass
        super(context, displayMode, objectData);

    }

    public class ItemElements {
        static final int TITLE = 1;
        static final int CHECKBOX = 2;
        static final int MOTTO = 3;
    }

    @Override
    public View getView() {

        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setLayoutParams(super.WIDTH_MATCH_PARENT);
        view.setPadding(10, 10, 10, 10);

        if (super.displayMode == MensaMeetDisplayMode.BIG_EDITABLE ||
                super.displayMode == MensaMeetDisplayMode.BIG_NOTEDITABLE) {

            view.addView(super.createTextField(ItemElements.TITLE, super.TITLE_PARAMS, super.BIG_TEXT_SIZE));
            view.addView(super.createTextField(ItemElements.MOTTO, super.WIDTH_MATCH_PARENT, super.SMALL_TEXT_SIZE));

        } else if (super.displayMode == MensaMeetDisplayMode.SMALL ||
                super.displayMode == MensaMeetDisplayMode.SMALL_CHECKBOXES) {

            LinearLayout titleAndCheck = new LinearLayout(context);
            titleAndCheck.setOrientation(LinearLayout.HORIZONTAL);
            titleAndCheck.setLayoutParams(super.WIDTH_MATCH_PARENT);

            titleAndCheck.addView(super.createTextField(ItemElements.TITLE, super.TITLE_PARAMS, super.BIG_TEXT_SIZE));

            if (super.displayMode == MensaMeetDisplayMode.SMALL_CHECKBOXES) {
                titleAndCheck.addView(createItemCheckBox(ItemElements.CHECKBOX, super.CHECKBOX_PARAMS));
            }

            view.addView(titleAndCheck);

            view.addView(super.createTextField(ItemElements.MOTTO, super.WIDTH_MATCH_PARENT, super.SMALL_TEXT_SIZE));
        }

        return view;
    }

    @Override
    public void fillData(MensaMeetListAdapter.ViewHolder holder) {

        fillTextField(holder, ItemElements.TITLE, super.objectData.getUsername());
        fillTextField(holder, ItemElements.MOTTO, super.objectData.getMotto());

    }
}
