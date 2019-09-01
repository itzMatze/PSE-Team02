package edu.kit.mensameet.client.viewmodel;

import android.content.Context;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.kit.mensameet.client.model.MensaData;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.RequestUtil;

/**
 * View model for LoginActivity.
 */
public class LoginViewModel extends MensaMeetViewModel {

    private Context context;
    private MutableLiveData<String> email;
    private MutableLiveData<String> password;
    private String uid;

    /**
     * Method called after pressing login.
     *
     * @param item LoginViewModel.
     */
    public void onLoginClick(LoginViewModel item) {
        login(item);
    }

    /**
     * onClick method for test list classes button
     *
     * @param item LoginViewModel
     */
    public void onTestClick(LoginViewModel item) {

        User user = null;

        try {

            user = RequestUtil.getUser("", "OK9MdvhqfTgHzhb7YHsqrdqPZ1x2");

        } catch (RequestUtil.RequestException e) {

            eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.LOADING_USER_FAILED));
            return;

        }

        try {

            MensaMeetSession.getInstance().initialize(user);

        } catch (RequestUtil.RequestException e) {

            eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.INITIALIZATION_FAILED));
            return;

        }

        eventLiveData.setValue(new Pair<String, StateInterface>(null, State.LOG_IN_SUCCESS));

    }

    /**
     *
     * @return The email address of the user.
     */
    public MutableLiveData<String> getEmail() {
        if (email == null) {
            email = new MutableLiveData<>();
            email.setValue("");
        }
        return email;
    }

    /**
     *
     * @return The password of the user.
     */
    public MutableLiveData<String> getPassword() {
        if (password == null) {
            password = new MutableLiveData<>();
            password.setValue("");
        }
        return password;
    }

    /**
     * @return Uid which is set after account creation succeeded.
     */
    public String getUid() {
        return uid;
    }

    /**
     *
     * @param item
     */
    private void login(final LoginViewModel item) {

        String emailValue = email.getValue();
        String passwordValue = password.getValue();

        if (emailValue.equals("") || passwordValue.equals("")) {

            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.CREDENTIALS_INCOMPLETE));
            return;

        }

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email.getValue(), password.getValue())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            uid = firebaseUser.getUid();

                            User user = null;

                            try {

                                user = RequestUtil.getUser(uid, uid);

                            } catch (RequestUtil.RequestException e) {

                                mAuth.signOut();
                                eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.LOADING_USER_FAILED));
                                return;

                            }

                            try {

                                MensaMeetSession.getInstance().initialize(user);

                            } catch (RequestUtil.RequestException e) {

                                eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.INITIALIZATION_FAILED));
                                return;

                            }

                            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.LOG_IN_SUCCESS));

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {

                        eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.LOG_IN_FAILED));
                    }

                });
    }

    /**
     *
     * The states the view model uses to communicate with the view.
     */
    public enum State implements StateInterface {

        LOADING_USER_FAILED,
        LOG_IN_SUCCESS,
        LOG_IN_FAILED,
        INITIALIZATION_FAILED,
        CREDENTIALS_INCOMPLETE
    }
}
