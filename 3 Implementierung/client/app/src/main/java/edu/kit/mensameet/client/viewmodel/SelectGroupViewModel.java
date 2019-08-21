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

    private Group selectedGroup = null;

    public void loadGroups() {
        // prepare chosen list for server request
        List<Line> chosenLines = MensaMeetSession.getInstance().getChosenLines();
        String[] chosenMealLines = new String[chosenLines.size()];

        for (int i = 0; i < chosenLines.size(); i++) {

            chosenMealLines[i] = chosenLines.get(i).getMealLine();

        }

        // send query to get groups by preferences
        List<Group> receivedGroups = null;
        try {

            receivedGroups = RequestUtil.getGroupByPrefferences(MensaMeetSession.getInstance().getChosenTime(), chosenMealLines);

        } catch (RequestUtil.RequestException e) {

            // todo: handle this in activity
            eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.LOADING_GROUPS_FAILED));
            return;

        }

        MensaMeetSession.getInstance().setReceivedGroups(receivedGroups);

    }

    public void setSelectedGroup(Group selectedGroup) {

        this.selectedGroup = selectedGroup;

        eventLiveData.setValue(new Pair<String, StateInterface>(null, State.GROUP_JOINED));
    }

    public enum State implements StateInterface {
        NO_GROUP_SELECTED, GROUP_JOINED, BACK, LOADING_GROUPS_FAILED
    }
}
