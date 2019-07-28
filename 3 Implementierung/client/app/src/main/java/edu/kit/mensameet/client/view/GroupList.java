package edu.kit.mensameet.client.view;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.Group;

public class GroupList extends MensaMeetList<Group> {

    public GroupList(Context context, List<Group> data, DisplayMode displayMode, Boolean dividers) {
        super(context, data, displayMode, dividers);
    }

    @Override
    protected List<MensaMeetItem<Group>> createItems() {
        List<MensaMeetItem<Group>> items = new ArrayList<>();

        for (Group group : super.data) {
            GroupItem item = new GroupItem(super.context, MensaMeetItem.DisplayMode.SMALL, group);
            items.add(item);
        }

        return items;
    }
}
