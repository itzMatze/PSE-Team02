package edu.kit.mensameet.client.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.view.R;

/**
 * Class {@code RegisterViewModel} is responsible for preparing and managing the data for {@code RegisterActivity} Activity.
 * It also handles the communication of the Activity with the rest of the application
 */
public class RegisterViewModel extends MensaMeetViewModel {
    private MutableLiveData<String> userName;
    private MutableLiveData<String> password;


    public int register(String email, String password, String passwordConfirm, Context context) {
        int offlineCode = offlineRegister(email, password, passwordConfirm, context);
        int onlineCode = onlineRegister();
        //hier können die beiden Codes abgefragt werden und dann ein gesammelter Fehlercode zurückgegeben werden,
        //dieser kann beispielsweise einfach zweistellig sein
        return offlineCode;
    }

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
            User.getInstance().setUsername(username);
            return 0;
        }
    }

    /**
     * @return a integer presenting error Status
     */
    private int onlineRegister() {
        return 0;
    }
}
