package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.SingleLiveEvent;
import edu.kit.mensameet.client.view.MensaMeetDisplayMode;

public class GroupItemHandler extends MensaMeetItemHandler {
    public GroupItemHandler(Group group, MensaMeetDisplayMode displayMode) {
        super();
    }

    public LiveData<Group> loadGroup() {
        return null;
    }

    public void joinGroup() {

        if (uiEventLiveData == null) {
            uiEventLiveData = new SingleLiveEvent<>();
        }

        uiEventLiveData.setValue(new Pair<MensaMeetItemHandler, StateInterface>(this, State.GROUP_JOINED));

    }

    public enum State implements StateInterface {
        GROUP_JOINED, XY, DEFAULT
    }
}
