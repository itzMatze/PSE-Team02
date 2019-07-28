package edu.kit.mensameet.client.view;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.User;

public class UserList extends MensaMeetList<User> {

    public UserList(Context context, List<User> data, MensaMeetList.DisplayMode displayMode, Boolean dividers) {
        super(context, data, displayMode, false);
    }

    @Override
    protected List<MensaMeetItem<User>> createItems() {
        List<MensaMeetItem<User>> items = new ArrayList<>();

        for (User user : super.data) {
            UserItem item = new UserItem(super.context, MensaMeetItem.DisplayMode.SMALL, user);
            items.add(item);
        }

        return items;
    }
}
