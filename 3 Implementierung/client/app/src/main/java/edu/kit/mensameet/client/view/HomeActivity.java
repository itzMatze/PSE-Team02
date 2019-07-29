package edu.kit.mensameet.client.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import edu.kit.mensameet.client.model.FoodType;
import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.Meal;
import edu.kit.mensameet.client.model.MensaData;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.viewmodel.HomeViewModel;
import edu.kit.mensameet.client.viewmodel.SelectLinesViewModel;

/**
 * The central activity of the application, accessible by the home button from almost every other activity.
 *
 */
public class HomeActivity extends MensaMeetActivity {

    private HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        super.viewModel = viewModel;

        viewModel.initializeSession();

        // Initialize MensaMeetSession, TODO: normally at login or registration
        User me = new User();
        me.setIsAdmin(true);
        MensaMeetSession.getInstance().setUser(me);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        SharedPreferences sharedPrefs = getSharedPreferences("MensaMeetApp", Context.MODE_PRIVATE);
    }

    /**
     * When the button "Go eating" is clicked.
     *
     * @param v The current view.
     */
    public void onGoEatClick(View v) {

        // If no group is yet chosen,
        if (MensaMeetSession.getInstance().getChosenGroup() == null) {

            gotoActivity(SelectLinesActivity.class);

        } else {

            gotoActivity(GroupJoinedActivity.class);

        }
    }

    /**
     * When the profile button is clicked.
     *
     * @param v The current View.
     */
    public void onProfileClick(View v) {

        gotoActivity(UserActivity.class);

    }

    /**
     * When the logout button is clicked.
     *
     * @param v The current view.
     */
    public void onLogoutClick(View v) {

        viewModel.logout(this);
        gotoActivity(BeginActivity.class);

    }

}
