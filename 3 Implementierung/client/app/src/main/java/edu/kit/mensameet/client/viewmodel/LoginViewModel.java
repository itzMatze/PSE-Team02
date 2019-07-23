package edu.kit.mensameet.client.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import android.content.Context;
import android.util.Pair;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.kit.mensameet.client.util.SingleLiveEvent;
import edu.kit.mensameet.client.view.R;

public class LoginViewModel extends MensaMeetViewModel {
    //todo: move this later to MensaMeetViewModel
    public static final String LOG_IN_SUCCESS_ID = "logInSuccess";
    public static final String LOG_IN_FAILD_ID = "logInFailed";

    //[START]data binding
    private Context context;
    private MutableLiveData<String> email;
    private MutableLiveData<String> password;
    //[END]data binding
    /*
     SingleLiveEvent: A lifecycle-aware observable that sends only new updates after subscription
     use uiEventLiveData to pass a string to relevant activity, and it executes relevant functions
      */
    private SingleLiveEvent<Pair<LoginViewModel, String>> uiEventLiveData;

    //TODO:Pass information to other activity
    private String uid;

    /**
     * onClick method for log in
     *
     * @param item LoginViewModel
     */
    public void onLoginClick(LoginViewModel item) {
        if (matchPattern()) {
            login(item);
        }
    }

    /**
     * @return email
     */
    public MutableLiveData<String> getEmail() {
        if (email == null) {
            email = new MutableLiveData<>();
            email.setValue("");
        }
        return email;
    }

    /**
     * @return password
     */
    public MutableLiveData<String> getPassword() {
        if (password == null) {
            password = new MutableLiveData<>();
            password.setValue("");
        }
        return password;
    }

    /**
     * @return A lifecycle-aware observable that sends only new updates after subscription
     */
    public SingleLiveEvent<Pair<LoginViewModel, String>> getUiEventLiveData() {
        if (uiEventLiveData == null) {
            uiEventLiveData = new SingleLiveEvent<>();
            uiEventLiveData.setValue(new Pair<LoginViewModel, String>(null, "default"));
        }
        return uiEventLiveData;
    }

    /**
     * @return uid, uid is set after create account succeed
     */
    public String getUid() {
        return uid;
    }

    /**
     * help to check format of email and password
     *
     * @return true if email and password not null and password equals passwordConfirm
     * otherwise false
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

    private void login(final LoginViewModel item) {

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email.getValue(), password.getValue())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, notify uiEventLiveData
                            uiEventLiveData.setValue(new Pair<>(item, LOG_IN_SUCCESS_ID));
                            FirebaseUser user = mAuth.getCurrentUser();
                            uid = user.getUid();
                        } else {
                            uiEventLiveData.setValue(new Pair<>(item, LOG_IN_FAILD_ID));
                        }
                    }
                });
    }
}
