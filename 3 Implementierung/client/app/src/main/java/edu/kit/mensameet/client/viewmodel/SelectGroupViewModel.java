package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.SingleLiveEvent;

/**
 * View model of SelectGroupActivity.
 */
public class SelectGroupViewModel extends MensaMeetViewModel {

    private Group selectedGroup = null;

    public void setSelectedGroup(Group selectedGroup) {

        this.selectedGroup = selectedGroup;

        uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.GROUP_JOINED));
    }

    public enum State implements StateInterface {
        NO_GROUP_SELECTED, GROUP_JOINED, BACK
    }
}
