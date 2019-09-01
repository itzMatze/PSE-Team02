package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import java.util.List;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.util.RequestUtil;

/**
 * View model of SelectGroupActivity.
 */
public class SelectGroupViewModel extends MensaMeetViewModel {

    private List<Group> receivedGroups;

    public Boolean loadGroups() {

        if (getChosenTime() == null) {
            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.NO_TIME_CHOSEN));
            return false;
        }

        // prepare chosen list for server request
        List<Line> chosenLines = getChosenLines();

        if (chosenLines == null) {
            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.NO_LINES_CHOSEN));
            return false;
        }

        String[] chosenMealLines = new String[chosenLines.size()];

        for (int i = 0; i < chosenLines.size(); i++) {

            chosenMealLines[i] = chosenLines.get(i).getMealLine();

        }

        // send query to get groups by preferences
        List<Group> receivedGroups = null;
        try {

            receivedGroups = RequestUtil.getGroupByPrefferences(getUser().getToken(), getChosenTime(), chosenMealLines);

        } catch (RequestUtil.RequestException e) {

            eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.LOADING_GROUPS_FAILED));
            return false;

        }

        this.receivedGroups = receivedGroups;
        return true;

    }

    public List<Group> getReceivedGroups() {
        return receivedGroups;
    }

    public enum State implements StateInterface {
        LOADING_GROUPS_FAILED, NO_TIME_CHOSEN, NO_LINES_CHOSEN
    }
}
