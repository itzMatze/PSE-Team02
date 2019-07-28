package edu.kit.mensameet.client.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;

public class UserList extends MensaMeetList<User> {

    public UserList(Context context, List<User> data, MensaMeetList.DisplayMode displayMode, Boolean dividers) {
        super(context, data, displayMode, false);
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
        ((UserItem)adapter.getItem(position)).getHandler().showUser();
    }
}
