package edu.kit.mensameet.client.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import edu.kit.mensameet.client.view.R;

public class LoginViewModel extends MensaMeetViewModel {
    public int login(String email, String password, Context context) {
        int offlineCode = offlineLogin(email, password, context);
        int onlineCode = onlineLogin(email, password, context);
        //hier können die beiden Codes abgefragt werden und dann ein gesammelter Fehlercode zurückgegeben werden,
        //dieser kann beispielsweise einfach zweistellig sein
        return offlineCode;
    }

    private int offlineLogin(String username, String password, Context context) {
        //überprüft Gültigkeit der email und des Passworts, 1 heißt email ungültig, 2 heißt Passwort ungültig
        SharedPreferences sharedPrefs = context.getSharedPreferences("MensaMeetApp", Context.MODE_PRIVATE);
        String savedEmail = sharedPrefs.getString(context.getString(R.string.username_file_id), "");
        //das Passwort wird in der finalen Version nicht mehr offline gespeichert sondern immer mit dem Server abgeglichen
        String savedPassword = sharedPrefs.getString(context.getString(R.string.password_file_id), "");
        if (!username.equals(savedEmail) || username.length() == 0) {
            return 1;
        } else if (!password.equals(savedPassword) || password.length() == 0) {
            return 2;
        } else {
            return 0;
        }
    }

    private int onlineLogin(String username, String password, Context context) {
        return 0;
    }

    public void passwordForgotten() {

    }
}
