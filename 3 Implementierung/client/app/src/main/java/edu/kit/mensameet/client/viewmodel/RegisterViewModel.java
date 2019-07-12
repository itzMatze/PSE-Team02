package edu.kit.mensameet.client.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import edu.kit.mensameet.client.view.R;

public class RegisterViewModel extends MensaMeetViewModel {
    public int register(String email, String password, String passwordConfirm, Context context) {
        int offlineCode = offlineRegister(email, password, passwordConfirm, context);
        int onlineCode = onlineRegister(email, password, passwordConfirm, context);
        //hier können die beiden Codes abgefragt werden und dann ein gesammelter Fehlercode zurückgegeben werden,
        //dieser kann beispielsweise einfach zweistellig sein
        return offlineCode;
    }

    private int offlineRegister(String email, String password, String passwordConfirm, Context context) {
        //überprüft Gültigkeit der email und des Passworts, 1 heißt email ungültig, 2 heißt Passwort ungültig,
        //3 heißt Passwörter stimmen nicht überein
        if (email.length() == 0) {
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
            editor.putString(context.getString(R.string.username_file_id), email);
            //das Passwort wird in der finalen Version nicht mehr offline gespeichert sondern immer mit dem Server abgeglichen
            editor.putString(context.getString(R.string.password_file_id), password);
            editor.commit();
            return 0;
        }
    }

    private int onlineRegister(String email, String password, String passwordConfirm, Context context) {
        return 0;
    }
}
