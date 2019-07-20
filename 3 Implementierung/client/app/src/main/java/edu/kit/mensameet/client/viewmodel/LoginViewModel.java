package edu.kit.mensameet.client.viewmodel;

import androidx.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import edu.kit.mensameet.client.util.SingleLiveEvent;
import edu.kit.mensameet.client.view.R;

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
        uiEventLiveData.setValue(new Pair<LoginViewModel, String>(item,LOG_IN_ID));
    }

    public int login() {
        int offlineCode = offlineLogin();
        int onlineCode = onlineLogin();
        //hier können die beiden Codes abgefragt werden und dann ein gesammelter Fehlercode zurückgegeben werden,
        //dieser kann beispielsweise einfach zweistellig sein
        return offlineCode;
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

    private int offlineLogin() {
        //überprüft Gültigkeit der email und des Passworts, 1 heißt email ungültig, 2 heißt Passwort ungültig
        SharedPreferences sharedPrefs = context.getSharedPreferences("MensaMeetApp", Context.MODE_PRIVATE);
        String savedEmail = sharedPrefs.getString(context.getString(R.string.username_file_id), "");
        //das Passwort wird in der finalen Version nicht mehr offline gespeichert sondern immer mit dem Server abgeglichen
        String savedPassword = sharedPrefs.getString(context.getString(R.string.password_file_id), "");
        //todo: save return code as final static or in resource
        if (!userName.getValue().equals(savedEmail) || userName.getValue().length() == 0) {
            return 1;
        } else if (!password.getValue().equals(savedPassword) || password.getValue().length() == 0) {
            return 2;
        } else {
            return 0;
        }
    }

    private int onlineLogin() {
        return 0;
    }

    public void passwordForgotten() {

    }
}
