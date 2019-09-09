package edu.kit.mensameet.client.view;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.User;

/**
 * A list view of users.
 */
public class UserList extends MensaMeetList<User> {

    public UserList(Context context, List<User> data, MensaMeetList.DisplayMode displayMode, Boolean dividers, Boolean scrolling) {
        super(context, data, displayMode, dividers, scrolling);
    }

    @Override
    protected List<MensaMeetItem<User>> createItems() {
        List<MensaMeetItem<User>> items = new ArrayList<>();

        for (User user : super.data) {
            UserItem item = new UserItem(context, MensaMeetItem.DisplayMode.SMALL, user);
            items.add(item);
        }

        return items;
    }

    @Override
    public void onItemClick(View view, int position) {
        ((UserItem) adapter.getItem(position)).getHandler().showUser();
    }
}
