package edu.kit.mensameet.client.view;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.User;

public class UserList extends MensaMeetList<User> {

    public UserList(Context context, List<User> data, Boolean checkBoxes) {
        super(context, data, checkBoxes);
    }

    @Override
    protected List<MensaMeetItem> createItems() {
        List<MensaMeetItem> items = new ArrayList<MensaMeetItem>();
        MensaMeetDisplayMode displayMode;

        if (super.checkBoxes) {
            displayMode = MensaMeetDisplayMode.SMALL_CHECKBOXES;
        } else {
            displayMode = MensaMeetDisplayMode.SMALL;
        }

        for (User user : super.data) {
            UserItem item = new UserItem(super.context, displayMode, user);
            items.add(item);
        }

        return items;
    }
}
