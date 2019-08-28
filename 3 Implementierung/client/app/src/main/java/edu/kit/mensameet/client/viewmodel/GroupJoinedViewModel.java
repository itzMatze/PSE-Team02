package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.RequestUtil;

/**
 * View model for GroupJoinedActivity
 */
public class GroupJoinedViewModel extends MensaMeetViewModel {

    private Group group;
    private String groupToken;

    public Boolean setGroupByToken(String groupToken) {

        Group requestGroup = null;

        try {

            requestGroup = RequestUtil.getGroup(MensaMeetSession.getInstance().getUser().getToken(), groupToken);

        } catch (RequestUtil.RequestException e) {

            eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.ERROR_LOADING_GROUP));
            return false;

        }

        this.group = requestGroup;
        this.groupToken = groupToken;
        return true;
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

            // Reload user.

            User userUpdate = null;

            try {

                userUpdate = RequestUtil.getUser(MensaMeetSession.getInstance().getUser().getToken(), MensaMeetSession.getInstance().getUser().getToken());

            } catch (RequestUtil.RequestException e) {

                eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.RELOADING_USER_FAILED));
                return;

            }

            MensaMeetSession.getInstance().setUser(userUpdate);

            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.GROUP_LEFT));

        }
    }

    public String getGroupToken() {
        return groupToken;
    }

    public enum State implements StateInterface {
        GROUP_LEFT, ERROR_LOADING_GROUP, ERROR_LEAVING_GROUP, RELOADING_USER_FAILED
    }
}
