package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;

public class GroupJoinedViewModel extends MensaMeetViewModel {

    private Group group;

    public void setGroup(Group group) {
        this.group = group;
    }

    public void leaveGroup() {
        if (group != null) {

            //removeUserFromGroup(MensaMeetSession.getInstance().getUser(), group);
            /*if (http success){
                MensaMeetSession.getInstance().setChosenGroup(null);
            }*/

            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.GROUP_LEFT));
        }
    }

    public enum State implements StateInterface {
        GROUP_LEFT, DEFAULT
    }
}