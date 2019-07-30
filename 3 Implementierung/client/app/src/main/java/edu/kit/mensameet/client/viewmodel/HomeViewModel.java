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

        SharedPreferences sharedPrefs = context.getSharedPreferences("MensaMeetApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();

    }

    /**
     * Initializes the singleton MensaMeetSession where the session data is saved.
     *
     */
    public void initializeSession() {

        // Load the daily menu of the mensa.

        // MensaData mensaData = HttpUtil.getMensaData();
        // MensaMeetSession.getInstance().setMensaData(mensaData);

        // Mock data
        /*Meal[] linie1Meals = new Meal[]{new Meal("Schnitzel", (float) 2.60, new FoodType[]{FoodType.VEGAN,})};
        Line linie1 = new Line("Linie 1", linie1Meals);
        Meal[] linie2Meals = new Meal[]{new Meal("Salat", (float) 2.60, new FoodType[]{FoodType.VEGAN,})};
        Line linie2 = new Line("Linie 2", linie2Meals);
        Meal[] linie3Meals = new Meal[]{new Meal("Wurst", (float) 2.60, new FoodType[]{FoodType.VEGAN,})};
        Line linie3 = new Line("Linie 3", linie3Meals);
        MensaMeetSession.getInstance().setMensaData(new MensaData(new Line[]{linie1, linie2, linie3}));*/

        MensaData mensaData = RequestUtil.getMensaData();
        MensaMeetSession.getInstance().setMensaData(mensaData);

        MensaMeetSession.getInstance().setChosenLines(null);
        MensaMeetSession.getInstance().setChosenTime(null);
        MensaMeetSession.getInstance().setChosenGroup(null);
        MensaMeetSession.getInstance().setCreatedGroup(null);
        MensaMeetSession.getInstance().setUserToken(null);
        MensaMeetSession.getInstance().setUserToShow(null);

    }

}
