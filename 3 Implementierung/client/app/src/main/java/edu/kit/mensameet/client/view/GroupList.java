package edu.kit.mensameet.client.view;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.Group;

public class GroupList extends MensaMeetList<Group> {

    public GroupList(Context context, List<Group> data, Boolean checkBoxes) {
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

        for (Group group : super.data) {
            GroupItem item = new GroupItem(super.context, displayMode, group);
            items.add(item);
        }

        return items;
    }
}
