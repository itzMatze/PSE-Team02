package edu.kit.mensameet.client.view;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.Line;

/**
 * List of lines.
 */
public class LineList extends MensaMeetList<Line> {

    /**
     * Constructor
     *
     * @param context Context of the parent.
     * @param data Data list.
     * @param displayMode List display mode.
     * @param dividers Whether dividers should be displayed.
     */
    public LineList(Context context, List<Line> data, MensaMeetList.DisplayMode displayMode, Boolean dividers, Boolean scrolling) {
        super(context, data, displayMode, dividers, scrolling);
    }

    @Override
    protected List<MensaMeetItem<Line>> createItems() {
        List<MensaMeetItem<Line>> items = new ArrayList<>();

        for (Line line : super.data) {
            LineItem item = new LineItem(super.context, MensaMeetItem.DisplayMode.SMALL, line);
            items.add(item);
        }

        return items;
    }
}
