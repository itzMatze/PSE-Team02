package edu.kit.mensameet.client.viewmodel;

import android.content.Context;
import android.util.Pair;

import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.SingleLiveEvent;

/**
 * Abstract basic view model.
 */
public abstract class MensaMeetViewModel extends ViewModel {

    /**
     SingleLiveEvent: A lifecycle-aware observable that sends only new updates after subscription
     use eventLiveData to pass a string to relevant activity, and it executes relevant functions
     */
    protected SingleLiveEvent<Pair<String, StateInterface>> eventLiveData = new SingleLiveEvent<>();

    /**
     * Gets the live data element to observe.
     *
     * @return Live data element.
     */
    public SingleLiveEvent<Pair<String, StateInterface>> getEventLiveData() {
        if (eventLiveData == null) {
            eventLiveData = new SingleLiveEvent<>();
        }
        return eventLiveData;
    }

    public void invalidateSession() {
        MensaMeetSession.getInstance().invalidate();
    }

    public Group getCreatedGroup() {
        return MensaMeetSession.getInstance().getCreatedGroup();
    }

    public void setCreatedGroup(Group createdGroup) {
        MensaMeetSession.getInstance().setCreatedGroup(createdGroup);
    }

    public MensaMeetTime getChosenTime() {
        return MensaMeetSession.getInstance().getChosenTime();
    }

    public List<Line> getChosenLines() {
        return MensaMeetSession.getInstance().getChosenLines();
    }

    public Boolean createdGroupDataIncomplete(Context context) {
        return MensaMeetSession.getInstance().createdGroupDataIncomplete(context);
    }

    public User getUser() {
        return MensaMeetSession.getInstance().getUser();
    }

    public Line[] getMensaLines() {
        return MensaMeetSession.getInstance().getMensaLines();
    }

    public User getUserToShow() {
        return MensaMeetSession.getInstance().getUserToShow();
    }

    public Boolean currentUserDataIncomplete(){
        return MensaMeetSession.getInstance().userDataIncomplete();
    }

    public enum State implements StateInterface {  }
}
