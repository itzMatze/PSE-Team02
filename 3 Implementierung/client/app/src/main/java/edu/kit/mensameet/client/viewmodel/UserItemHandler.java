package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.RequestUtil;
import edu.kit.mensameet.client.view.MensaMeetItem;

/**
 * Handler for a UserItem.
 */
public class UserItemHandler extends MensaMeetItemHandler<User> {

    /**
     * Constructor.
     *
     * @param user
     */
    public UserItemHandler(User user) {
        super(user);
    }

    public void showUser() {
        MensaMeetSession.getInstance().setUserToShow(objectData);
        eventLiveData.setValue(new Pair<String, StateInterface>(null, State.SHOW_USER));
    }

    public void deleteUser() {

        // the current user cannot delete himself!
        if (MensaMeetSession.getInstance().getUser().getToken() != objectData.getToken()) {
            // todo: replace first argument with real firebase token
            try {

                RequestUtil.deleteUser(MensaMeetSession.getInstance().getUser().getToken(), objectData.getToken());

            } catch (RequestUtil.RequestException e) {

                eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.USER_DELETION_FAILED));
                return;

            }

            /*

            // Delete user on Firebase.

            final FirebaseAuth mAuth = FirebaseAuth.getInstance();
            try {
                mAuth.deleteUser(objectData.getToken());
            } catch (FirebaseAuthException e) {
                eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.USER_DELETED_SERVER_NOT_FIREBASE));
                return;
            }*/

            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.USER_DELETED));
        }
    }

    public enum State implements StateInterface {
        USER_DELETED, USER_DELETION_FAILED, SHOW_USER, USER_DELETED_SERVER_NOT_FIREBASE
    }
}
