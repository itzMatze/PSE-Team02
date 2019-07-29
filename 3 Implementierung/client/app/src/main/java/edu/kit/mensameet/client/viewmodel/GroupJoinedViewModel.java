package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.RequestUtil;

public class GroupJoinedViewModel extends MensaMeetViewModel {

    private Group group = MensaMeetSession.getInstance().getChosenGroup();

    public void setGroup(Group group) {
        this.group = group;
    }

    public void leaveGroup() {
        if (group != null) {
            //todo: get group from user
            RequestUtil.removeUserFromGroup(MensaMeetSession.getInstance().getUser().getToken(), group.getToken());
            MensaMeetSession.getInstance().setChosenGroup(null);
            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.GROUP_LEFT));


            //removeUserFromGroup(MensaMeetSession.getInstance().getUser(), group);
            /*if (http success){
                MensaMeetSession.getInstance().setChosenGroup(null);
            }*/

        }
    }

    public enum State implements StateInterface {
        GROUP_LEFT, DEFAULT
    }
}
