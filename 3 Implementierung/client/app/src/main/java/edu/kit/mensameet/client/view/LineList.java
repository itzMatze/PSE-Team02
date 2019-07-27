package edu.kit.mensameet.client.view;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.Line;

//TODO: scrollable?

public class LineList extends MensaMeetList<Line> {

    public LineList(Context context, List<Line> data, MensaMeetList.DisplayMode displayMode, Boolean dividers) {
        super(context, data, displayMode, true);
    }

    @Override
    protected List<MensaMeetItem<Line>> createItems() {
        List<MensaMeetItem<Line>> items = new ArrayList<>();

        for (Line line : super.data) {
            LineItem item = new LineItem(super.context, MensaMeetDisplayMode.SMALL, line);
            items.add(item);
        }

        return items;
    }
}
