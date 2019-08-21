package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.RequestUtil;

/**
 * View model for GroupJoinedActivity
 */
public class GroupJoinedViewModel extends MensaMeetViewModel {

    private Group group;

    public void setGroupByToken(String groupToken) {

        Group requestGroup = null;

        try {

            requestGroup = RequestUtil.getGroup(MensaMeetSession.getInstance().getUser().getToken(), groupToken);

        } catch (RequestUtil.RequestException e) {

        }

        this.group = requestGroup;
    }

    public Group getGroup() {
        return group;
    }

    public void leaveGroup() {
        if (group != null) {

            try {

                RequestUtil.removeUserFromGroup(MensaMeetSession.getInstance().getUser().getToken(), group.getToken());

            } catch (RequestUtil.RequestException e) {

                eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.ERROR_LEAVING_GROUP));
                return;

            }
            //todo: instead of manipulating local data, reload it from the server after every change
            MensaMeetSession.getInstance().getUser().setGroupToken(null);

            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.GROUP_LEFT));

        }
    }

    public enum State implements StateInterface {
        GROUP_LEFT, ERROR_LEAVING_GROUP, DEFAULT
    }
}
