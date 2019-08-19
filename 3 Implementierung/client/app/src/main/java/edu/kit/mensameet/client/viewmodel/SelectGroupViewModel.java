package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.RequestUtil;
import edu.kit.mensameet.client.util.SingleLiveEvent;

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
        List<Group> receivedGroups = RequestUtil.getGroupByPrefferences(MensaMeetSession.getInstance().getChosenTime(), chosenMealLines);

        MensaMeetSession.getInstance().setReceivedGroups(receivedGroups);

    }

    public void setSelectedGroup(Group selectedGroup) {

        this.selectedGroup = selectedGroup;

        uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.GROUP_JOINED));
    }

    public enum State implements StateInterface {
        NO_GROUP_SELECTED, GROUP_JOINED, BACK
    }
}
