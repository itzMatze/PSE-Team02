package edu.kit.mensameet.client.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.view.R;

public class BeginViewModel extends MensaMeetViewModel {
    public static final String RESET_LOCAL_DATA_ID = "resetLocalData";
    public static final String LOGIN_ID = "login";
    public static final String REGISTER_ID = "register";
    public static final String TEST_LIST_CLASSES_ID = "testListClasses";
    private SingleLiveEvent<Pair<BeginViewModel, String>> uiEventLiveData;//A lifecycle-aware observable that sends only new updates after subscription

    //Überprüft ob schon ein Username gespeichert ist, um herauszufinden, ob an dem Gerät schon ein Benutzer angemeldet ist
    public boolean isLoggedIn(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences("MensaMeetApp", Context.MODE_PRIVATE);
        String savedUsername = sharedPrefs.getString(context.getString(R.string.username_file_id), "");
        User.getInstance().setUsername(savedUsername);
        return !savedUsername.equals("");
    }

    /**
     * onClick method for reset all local data button
     * @param item BeginViewModel
     */
    public void onResetClick(BeginViewModel item) {
        uiEventLiveData.setValue(new Pair<>(item, RESET_LOCAL_DATA_ID));
    }

    /**
     * onClick method for register button
     * @param item BeginViewModel
     */
    public void onRegisterClick(BeginViewModel item) {
        uiEventLiveData.setValue(new Pair<>(item, REGISTER_ID));
    }

    /**
     * onClick method for login button
     * @param item BeginViewModel
     */
    public void onLoginClick(BeginViewModel item) {
        uiEventLiveData.setValue(new Pair<>(item, LOGIN_ID));
    }

    /**
     * onClick method for test list classes button
     * @param item BeginViewModel
     */
    public void onTestClick(BeginViewModel item) {
        uiEventLiveData.setValue(new Pair<>(item, TEST_LIST_CLASSES_ID));
    }

    /**
     * @return A lifecycle-aware observable that sends only new updates after subscription
     */
    public SingleLiveEvent<Pair<BeginViewModel, String>> getUiEventLiveData() {
        if(uiEventLiveData == null){
            uiEventLiveData = new SingleLiveEvent<>();
            uiEventLiveData.setValue(new Pair<BeginViewModel, String>(null, "default"));
        }
        return uiEventLiveData;
    }

}
