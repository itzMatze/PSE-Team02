package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import androidx.lifecycle.ViewModel;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;

public class UserViewModel extends MensaMeetViewModel {

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void saveUserAndNext()
    {
        if (user != null) {

            //HttpUtil.createUser(user);
            /*if (http success){
               MensaMeetSession.getInstance().setUser(user);
            }*/

            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.USER_SAVED_NEXT));
        } else {
            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.ERROR_SAVING_USER));
        }

    }

    public enum State implements StateInterface {
        USER_SAVED_NEXT, BACK, ERROR_SAVING_USER, DEFAULT
    }

}
