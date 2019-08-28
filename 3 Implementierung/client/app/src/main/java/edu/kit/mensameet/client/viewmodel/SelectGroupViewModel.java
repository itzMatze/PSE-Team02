package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import java.util.List;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.RequestUtil;

/**
 * View model of SelectGroupActivity.
 */
public class SelectGroupViewModel extends MensaMeetViewModel {

    private List<Group> receivedGroups;

    public Boolean loadGroups() {
        // prepare chosen list for server request
        List<Line> chosenLines = MensaMeetSession.getInstance().getChosenLines();
        String[] chosenMealLines = new String[chosenLines.size()];

        for (int i = 0; i < chosenLines.size(); i++) {

            chosenMealLines[i] = chosenLines.get(i).getMealLine();

        }

        // send query to get groups by preferences
        List<Group> receivedGroups = null;
        try {

            receivedGroups = RequestUtil.getGroupByPrefferences(MensaMeetSession.getInstance().getUser().getToken(), MensaMeetSession.getInstance().getChosenTime(), chosenMealLines);

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
        LOADING_GROUPS_FAILED
    }
}
