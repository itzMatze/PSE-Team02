package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.RequestUtil;

/**
 * View model for CreateGroupActivity.
 */
public class CreateGroupViewModel extends MensaMeetViewModel {

    private Group group;

    public void setGroup(Group group) {
        this.group = group;
    }

    public void saveGroupAndNext() {

        if (group != null) {

            Group groupWithToken = null;

            try {

                groupWithToken = RequestUtil.createGroup(group,  MensaMeetSession.getInstance().getUser().getToken());

            } catch (RequestUtil.RequestException e) {

                eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.ERROR_SAVING_GROUP));
                return;

            }

            if (groupWithToken != null) {

                group = groupWithToken;

                try {

                    RequestUtil.addUserToGroup(group.getToken(), MensaMeetSession.getInstance().getUser().getToken());

                } catch (RequestUtil.RequestException e) {

                    eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.ERROR_SAVING_GROUP));
                    return;

                }

                MensaMeetSession.getInstance().getUser().setGroupToken(group.getToken());
                group.getUsers().add(MensaMeetSession.getInstance().getUser());
                MensaMeetSession.getInstance().setChosenGroup(group);

            }

            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.GROUP_SAVED_NEXT));

        } else {

            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.ERROR_SAVING_GROUP));

        }
    }

    public void saveGroupLocallyAndBack() {
        if (group != null) {
            MensaMeetSession.getInstance().setCreatedGroup(group);
            MensaMeetSession.getInstance().setChosenGroup(group);

            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.GROUP_SAVED_NEXT));
        }

        eventLiveData.setValue(new Pair<String, StateInterface>(null, State.BACK));
    }

    public enum State implements StateInterface {
        GROUP_SAVED_NEXT, BACK, ERROR_SAVING_GROUP, DEFAULT
    }
}
