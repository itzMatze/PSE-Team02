package edu.kit.mensameet.client.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.view.GroupJoinedActivity;
import edu.kit.mensameet.client.view.SelectLinesActivity;
import edu.kit.mensameet.client.view.UserActivity;

/**
 * The view model behind home activity.
 */
public class HomeViewModel extends MensaMeetViewModel {

    /**
     * Handles the logout of the user.
     *
     * @param context Current context.
     */
    public void logout(Context context) {

        MensaMeetSession.getInstance().invalidate();

        eventLiveData.setValue(new Pair<String, StateInterface>(null, State.TO_BEGIN));

        SharedPreferences sharedPrefs = context.getSharedPreferences("MensaMeetApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();

    }

    public void goEat() {

        // Check if user data complete
        if (MensaMeetSession.getInstance().userDataIncomplete()) {
            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.TO_USER));
        }


        if (MensaMeetSession.getInstance().getUser().getGroupToken() == null) {
            // If no group is yet chosen
            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.TO_SELECT_LINES));

        } else {
            // If a group is chosen
            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.TO_GROUP_JOINED));

        }
    }

    /**
     *
     * The states the view model uses to communicate with the view.
     */
    public enum State implements StateInterface {
        TO_BEGIN,
        TO_USER,
        TO_SELECT_LINES,
        TO_GROUP_JOINED
    }

}
