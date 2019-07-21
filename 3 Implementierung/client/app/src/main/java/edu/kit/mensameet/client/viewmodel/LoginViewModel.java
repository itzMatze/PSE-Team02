package edu.kit.mensameet.client.viewmodel;

import androidx.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Pair;
import edu.kit.mensameet.client.util.SingleLiveEvent;

public class LoginViewModel extends MensaMeetViewModel {
    //todo: move this later to MensaMeetViewModel
    public static final String LOG_IN_ID = "logIn";

    private MutableLiveData<String> userName;
    private MutableLiveData<String> password;
    private SingleLiveEvent<Pair<LoginViewModel, String>> uiEventLiveData;//A lifecycle-aware observable that sends only new updates after subscription

    public Context context;// to visit SharedPreferences
    /**
     * onClick method for log in
     * @param item LoginViewModel
     */
    public void onLoginClick(LoginViewModel item) {
        uiEventLiveData.setValue(new Pair<>(item,LOG_IN_ID));
    }

    /**
     * @return userName
     */
    public MutableLiveData<String> getUserName() {
        if (userName == null){
            userName = new MutableLiveData<>();
            userName.setValue("");
        }
        return userName;
    }

    /**
     * @return password
     */
    public MutableLiveData<String> getPassword() {
        if (password == null){
            password = new MutableLiveData<>();
            password.setValue("");
        }
        return password;
    }

    /**
     * @return A lifecycle-aware observable that sends only new updates after subscription
     */
    public SingleLiveEvent<Pair<LoginViewModel, String>> getUiEventLiveData() {
        if(uiEventLiveData == null){
            uiEventLiveData = new SingleLiveEvent<>();
            uiEventLiveData.setValue(new Pair<LoginViewModel, String>(null, "default"));
        }
        return uiEventLiveData;
    }

}
