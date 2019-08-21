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

                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            uid = firebaseUser.getUid();

                            User user = null;

                            try {

                                user = RequestUtil.getUser("", uid);

                            } catch (RequestUtil.RequestException e) {

                                eventLiveData.setValue(new Pair<String, StateInterface>(e.getLocalizedMessage(), State.LOG_IN_FAILED_ID));
                                return;

                            }

                            if (MensaMeetSession.getInstance().initialize(user)) {
                                eventLiveData.setValue(new Pair<String, StateInterface>(null, State.LOG_IN_SUCCESS_ID));
                            } else {
                                //todo: handle it!
                                eventLiveData.setValue(new Pair<String, StateInterface>(null, State.INITIALIZATION_FAILED));
                            }


                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {

                        String errorMessage = e.getLocalizedMessage();
                        eventLiveData.setValue(new Pair<String, StateInterface>(errorMessage, State.LOG_IN_FAILED_ID));
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
        TEST_ID_HOME,
        INITIALIZATION_FAILED
    }
}
