package edu.kit.mensameet.client.viewmodel;

import android.content.Context;
import android.util.Pair;

import java.util.Date;
import java.util.List;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.model.MensaMeetUserPicture;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.RequestUtil;

/**
 * View model for CreateGroupActivity.
 */
public class CreateGroupViewModel extends MensaMeetViewModel {

    public void saveGroupAndNext() {

        Group group = MensaMeetSession.getInstance().getCreatedGroup();

        if (group != null) {

            Group groupWithToken = null;

            // Create group.

            try {

                groupWithToken = RequestUtil.createGroup(group,  MensaMeetSession.getInstance().getUser().getToken());

            } catch (RequestUtil.RequestException e) {

                eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.ERROR_SAVING_GROUP));
                return;

            }

            if (groupWithToken != null) {

                // Join group.

                try {

                    RequestUtil.addUserToGroup(groupWithToken.getToken(), MensaMeetSession.getInstance().getUser().getToken());

                } catch (RequestUtil.RequestException e) {

                    eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.ERROR_SAVING_GROUP));
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
                eventLiveData.setValue(new Pair<String, StateInterface>(null, State.GROUP_SAVED));

            } else {

                eventLiveData.setValue(new Pair<String, StateInterface>(null, State.ERROR_SAVING_GROUP));

            }



        } else {

            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.ERROR_SAVING_GROUP));

        }
    }

    public enum State implements StateInterface {
        GROUP_SAVED, ERROR_SAVING_GROUP, RELOADING_USER_FAILED
    }
}
