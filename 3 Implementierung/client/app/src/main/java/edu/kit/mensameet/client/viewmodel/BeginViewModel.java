package edu.kit.mensameet.client.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import edu.kit.mensameet.client.view.R;

public class BeginViewModel extends MensaMeetViewModel {

    //Überprüft ob schon ein Username gespeichert ist, um herauszufinden, ob an dem Gerät schon ein Benutzer angemeldet ist
    public boolean isLoggedIn(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences("MensaMeetApp", Context.MODE_PRIVATE);
        String savedEmail = sharedPrefs.getString(context.getString(R.string.username_file_id), "");
        return !savedEmail.equals("");
    }
}
