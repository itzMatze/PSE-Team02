package edu.kit.mensameet.client.viewmodel;

import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import java.util.Date;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.MensaMeetUtil;
import edu.kit.mensameet.client.util.RequestUtil;

/**
 * The view model behind UserActivity.
 */
public class UserViewModel extends MensaMeetViewModel {

    private User user;

    public UserViewModel() {

        // TODO: Remove everything after testing
        if (MensaMeetSession.getInstance().getUser() == null) {

            // Test group
            Group receivedGroup = RequestUtil.getGroup("43f501ec-f9d6-4007-9fc7-9e391ba8e94d");

            // if not existing, create it
            if (receivedGroup == null || receivedGroup.getToken() == "" || receivedGroup.getToken() == null) {

                Group newGroup = new Group();
                newGroup.setName("Das jüngste Gericht");
                newGroup.setMotto("Hauptsache alles frisch zubereitet!");
                newGroup.setMaxMembers(10);
                newGroup.setMeetingDate(MensaMeetTime.stringToTime("12:15"));
                newGroup.setToken("aaa");

                Group confirmedGroup = RequestUtil.createGroup(newGroup, "999");

            }

            // Test user
            User receivedUser = RequestUtil.getUser("999", "999");

            // if user not existing, create him
            if (receivedUser.getToken() == "" || receivedUser.getToken() == null) {

                user = new User();
                user.setIsAdmin(true);
                user.setToken("999");

                String res = RequestUtil.createUser("999");
                res = RequestUtil.updateUser(user);

            } else {
                user = receivedUser;
            }

            MensaMeetSession.getInstance().setUser(user);
        }




    }


    /**
     * Sets the user.
     *
     * @param user New user data.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * The user data is saved to the server.
     */
    public void saveUserAndNext()
    {
        String res;

        if (user != null) {

            res = RequestUtil.updateUser(user);
            if (res == null) {

                uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.ERROR_SAVING_USER));
                return;
            }

            // if everything went well
            MensaMeetSession.getInstance().setUser(user);

            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.USER_SAVED_NEXT));

        } else {

            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.ERROR_SAVING_USER));

        }

    }

    public enum State implements StateInterface {
        USER_SAVED_NEXT, BACK, ERROR_SAVING_USER, DEFAULT
    }

}
