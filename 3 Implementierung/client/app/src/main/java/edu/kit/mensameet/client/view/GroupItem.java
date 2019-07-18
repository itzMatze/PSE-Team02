package edu.kit.mensameet.client.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import edu.kit.mensameet.client.model.Group;

public class GroupItem extends MensaMeetItem<Group> {

    private UserList userList;

    public GroupItem(Context context, MensaMeetDisplayMode displayMode, Group objectData) {
        super(context, displayMode, objectData);

        userList = new UserList(context, objectData.getUsers(), false);

    }

    public class ItemElements {
        static final int TITLE = 1;
        static final int CHECKBOX = 2;
        static final int MOTTO = 3;
        static final int USERLIST = 4;
    }

    @Override
    public View getView() {

        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setLayoutParams(super.WIDTH_MATCH_PARENT);
        view.setPadding(10, 10, 10 ,10);

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

        // User list

        LinearLayout userListContainer = new LinearLayout(context);
        userListContainer.setOrientation(LinearLayout.VERTICAL);
        userListContainer.setLayoutParams(super.WIDTH_MATCH_PARENT);
        userListContainer.setId(ItemElements.USERLIST);

        view.addView(userListContainer);

        return view;
    }

    @Override
    public void fillData(MensaMeetListAdapter.ViewHolder holder) {

        fillTextField(holder, ItemElements.TITLE, super.objectData.getName());
        fillTextField(holder, ItemElements.MOTTO, super.objectData.getMotto());
        fillSublist(holder, ItemElements.USERLIST, userList);

    }

    //TODO: fillData with view as parameter
}
