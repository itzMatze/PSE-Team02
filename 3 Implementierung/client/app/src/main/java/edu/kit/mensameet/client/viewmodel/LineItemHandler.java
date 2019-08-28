package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.RequestUtil;
import edu.kit.mensameet.client.view.MensaMeetItem;

/**
 * Handler for a LineItem.
 */
public class LineItemHandler extends MensaMeetItemHandler<Line> {

    private User user;

    /**
     * Constructor.
     *
     * @param line
     */
    public LineItemHandler(Line line) {
        super(line);
    }

    public enum State implements StateInterface {

    }
}
