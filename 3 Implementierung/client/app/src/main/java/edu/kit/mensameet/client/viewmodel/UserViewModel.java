package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import edu.kit.mensameet.client.model.MensaData;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.RequestUtil;

/**
 * The view model behind UserActivity.
 */
public class UserViewModel extends MensaMeetViewModel {

    private User user;

    public UserViewModel() {

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

            try {

                RequestUtil.updateUser(user);

            } catch (RequestUtil.RequestException e) {

                eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage()                                  , State.ERROR_SAVING_USER));
                return;

            }

            // if everything went well
            MensaMeetSession.getInstance().setUser(user);

            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.USER_SAVED_NEXT));

        } else {

            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.ERROR_SAVING_USER));

        }

    }

    public enum State implements StateInterface {
        USER_SAVED_NEXT, BACK, ERROR_SAVING_USER, DEFAULT
    }

}
