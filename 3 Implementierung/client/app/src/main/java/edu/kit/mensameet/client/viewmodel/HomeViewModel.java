package edu.kit.mensameet.client.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import edu.kit.mensameet.client.model.FoodType;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.Meal;
import edu.kit.mensameet.client.model.MensaData;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.RequestUtil;

/**
 * The view model behind home activity.
 */
public class HomeViewModel extends MensaMeetViewModel {

    /**
     * Handles the logout of the user.
     *
     * @param context Current context.
     */
    public void logout(Context context) {

        MensaMeetSession.getInstance().invalidate();
        SharedPreferences sharedPrefs = context.getSharedPreferences("MensaMeetApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();

    }


}
