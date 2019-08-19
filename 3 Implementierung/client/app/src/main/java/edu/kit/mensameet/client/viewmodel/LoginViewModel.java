package edu.kit.mensameet.client.viewmodel;

import android.content.Context;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.kit.mensameet.client.model.MensaData;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.RequestUtil;
import edu.kit.mensameet.client.util.SingleLiveEvent;

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
        if (matchPattern()) {
            login(item);
        }
    }

    /**
     * onClick method for test list classes button
     *
     * @param item BeginViewModel
     */
    public void onTestClick(LoginViewModel item) {

        // TODO: Remove everything after testing

        MensaData mensaData = RequestUtil.getMensaData();
        MensaMeetSession.getInstance().setMensaData(mensaData);

        // Test user
        User user = RequestUtil.getUser("999", email.getValue());

        // if user not existing, create him
        if (user.getToken().equals("") || user.getToken() == null) {

            user = new User();
            user.setName(email.getValue());
            if (password.getValue() != null && !password.getValue().equals("")) {
                user.setIsAdmin(true);
            }
            user.setToken(email.getValue());

            String res = RequestUtil.createUser(email.getValue());
            res = RequestUtil.updateUser(user);

        }

        MensaMeetSession.getInstance().setUser(user);

        MensaMeetSession.getInstance().setChosenLines(null);
        MensaMeetSession.getInstance().setChosenTime(null);
        MensaMeetSession.getInstance().setCreatedGroup(null);
        MensaMeetSession.getInstance().setUserToShow(null);

        if (MensaMeetSession.getInstance().getUser() == null) {


        }

        if (MensaMeetSession.getInstance().userDataIncomplete()) {
            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(item, State.TEST_ID_USER));
        } else {
            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(item, State.TEST_ID_HOME));
        }

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
     * Helps to check the format of email and password.
     *
     * @return True if email and password not null and password equals passwordConfirm,
     * otherwise false.
     */
    private boolean matchPattern() {
        //todo manage to show errorCode in editText
        if (email.getValue() == null) {
            return false;
        } else if (password.getValue() == null) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param item
     */
    private void login(final LoginViewModel item) {

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email.getValue(), password.getValue())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // Sign in success, notify uiEventLiveData
                            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(LoginViewModel.this, State.LOG_IN_SUCCESS_ID));

                            FirebaseUser user = mAuth.getCurrentUser();

                            uid = user.getUid();

                        } else {

                            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(LoginViewModel.this, State.LOG_IN_FAILED_ID));

                        }
                    }
                });
    }

    /**
     *
     * The states the view model uses to communicate with the view.
     */
    public enum State implements StateInterface {
        /**
         * If login was successful.
         */
        LOG_IN_SUCCESS_ID,
        /**
         * If login failed.
         */
        LOG_IN_FAILED_ID,
        TEST_ID_USER,
        TEST_ID_HOME
    }
}
