package edu.kit.mensameet.client.viewmodel;

import android.content.Context;
import android.util.Pair;

/**
 * View model of BeginActivity.
 */
public class BeginViewModel extends MensaMeetViewModel {

    //checks if there is already an username stored locally to find out, if an user is already logged in at this device
    public boolean isLoggedIn(Context context) {
/*
        SharedPreferences sharedPrefs = context.getSharedPreferences("MensaMeetApp", Context.MODE_PRIVATE);
        String savedUsername = sharedPrefs.getString(context.getString(R.string.username_file_id), "");
        MensaMeetSession.getInstance().getUser().setName(savedUsername);
        return !savedUsername.equals("");
        */
return false;
    }

    /**
     * onClick method for reset all local data button
     *
     * @param item BeginViewModel
     */
    public void onResetClick(BeginViewModel item) {
        eventLiveData.setValue(new Pair<String, StateInterface>(null, State.RESET_LOCAL_DATA_ID));
    }

    /**
     * onClick method for register button
     *
     * @param item BeginViewModel
     */
    public void onRegisterClick(BeginViewModel item) {
        eventLiveData.setValue(new Pair<String, StateInterface>(null, State.REGISTER_ID));
    }

    /**
     * onClick method for login button
     *
     * @param item BeginViewModel
     */
    public void onLoginClick(BeginViewModel item) {
        eventLiveData.setValue(new Pair<String, StateInterface>(null, State.LOGIN_ID));
    }

    public enum State implements StateInterface {
        TEST_LIST_CLASSES_ID, LOGIN_ID, REGISTER_ID, RESET_LOCAL_DATA_ID, TEST_ID
    }

}
