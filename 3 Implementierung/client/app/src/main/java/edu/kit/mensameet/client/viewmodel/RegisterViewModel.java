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

import edu.kit.mensameet.client.util.SingleLiveEvent;

/**
 * Class {@code RegisterViewModel} is responsible for preparing and managing the data for {@code RegisterActivity} Activity.
 * It also handles the communication of the Activity with the rest of the application
 */
public class RegisterViewModel extends MensaMeetViewModel {
    //todo
    public static final String CREATE_ACCOUNT_SUCCESS_ID = "createAccountSuccess";
    public static final String CREATE_ACCOUNT_FAILED_ID = "createAccountFailed";

    //[START]data binding
    private Context context;//todo: for getError
    private MutableLiveData<String> email;
    private MutableLiveData<String> password;
    private MutableLiveData<String> passwordConfirm;
    //[END]data binding
    /*
     SingleLiveEvent: A lifecycle-aware observable that sends only new updates after subscription
     use uiEventLiveData to pass a string to relevant activity, and it executes relevant functions
      */
    private SingleLiveEvent<Pair<RegisterViewModel, String>> uiEventLiveData;

    //TODO:Pass information to other activity
    private String uid;

    //todo: used to setError in Activity:  private MutableLiveData<String> error;

    /**
     * todo:
     * NOT FINAL VERSION!
     */
    public void onRegisterClick(RegisterViewModel item) {
        if (matchPattern()) {
            createAccount(item);
        }
    }

    /*
    private int offlineRegister(String username, String password, String passwordConfirm, Context context) {
        //überprüft Gültigkeit der email und des Passworts, 1 heißt email ungültig, 2 heißt Passwort ungültig,
        //3 heißt Passwörter stimmen nicht überein
        if (username.length() == 0) {
            return 1;
        } else if (password.length() == 0) {
            return 2;
        } else if (!password.equals(passwordConfirm)) {
            return 3;
        } else {
            //speichert die login Daten, Context muss übergeben werden,
            //weil SharedPreferences nur auf einem Context Objekt funktioniert und eine Activity ein solches Objekt ist
            SharedPreferences sharedPrefs = context.getSharedPreferences("MensaMeetApp", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(context.getString(R.string.username_file_id), username);
            //das Passwort wird in der finalen Version nicht mehr offline gespeichert sondern immer mit dem Server abgeglichen
            editor.putString(context.getString(R.string.password_file_id), password);
            editor.commit();
            User.getInstance().setName(username);
            return 0;
        }
    }
    */

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
     * @return passwordconfirm
     */
    public MutableLiveData<String> getPasswordConfirm() {
        if (passwordConfirm == null) {
            passwordConfirm = new MutableLiveData<>();
            passwordConfirm.setValue("");
        }
        return passwordConfirm;
    }

    /**
     * @return A lifecycle-aware observable that sends only new updates after subscription
     */
    public SingleLiveEvent<Pair<RegisterViewModel, String>> getUiEventLiveData() {
        if (uiEventLiveData == null) {
            uiEventLiveData = new SingleLiveEvent<>();
            uiEventLiveData.setValue(new Pair<RegisterViewModel, String>(null, "default"));
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
     * @param context Context
     */
    public void setContext(Context context) {
        this.context = context;
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
        } else if (!passwordConfirm.getValue().equals(password.getValue())) {
            return false;
        }
        return true;
    }

    private void createAccount(final RegisterViewModel item) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email.getValue(), password.getValue())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uiEventLiveData.setValue(new Pair<>(item, CREATE_ACCOUNT_SUCCESS_ID));
                            // Sign in success, notify uiEventLiveData
                            FirebaseUser user = mAuth.getCurrentUser();
                            //save uid
                            uid = user.getUid();
                        } else {
                            // If sign in fails, notify uiEventLiveData›
                            uiEventLiveData.setValue(new Pair<>(item, CREATE_ACCOUNT_FAILED_ID));
                        }
                    }
                });
    }
}
