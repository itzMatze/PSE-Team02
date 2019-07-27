package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaMeetSession;

public class CreateGroupViewModel extends MensaMeetViewModel {

    private Group group;

    public void setGroup(Group group) {
        this.group = group;
    }

    public void saveGroupAndNext()
    {
        if (group != null) {
            MensaMeetSession.getInstance().setCreatedGroup(group);
            //HttpUtil.createGroup(group);
            /*if (http success){
                MensaMeetSession.getInstance().setChosenGroup(group);
            }*/

            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.GROUP_SAVED_NEXT));
        } else {
            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.ERROR_SAVING_GROUP));
        }

    }

    public void saveGroupLocallyAndBack()
    {
        if (group != null) {
            MensaMeetSession.getInstance().setCreatedGroup(group);
            //HttpUtil.createGroup(group);
            /*if (http success){
                MensaMeetSession.getInstance().setChosenGroup(group);
            }*/

            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.GROUP_SAVED_NEXT));
        }

        uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.BACK));

    }



    public enum State implements StateInterface {
        GROUP_SAVED_NEXT, BACK, ERROR_SAVING_GROUP, DEFAULT
    }

}
