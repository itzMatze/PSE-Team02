package edu.kit.mensameet.client.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.viewmodel.HomeViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

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

        viewModel.goEat();

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

    }

    /** Hook method for livedata processing
     *
     * @param it Message.
     */
    protected void processStateChange(Pair<String, StateInterface> it) {
        if (it.second == HomeViewModel.State.TO_USER) {
            gotoActivity(UserActivity.class);
        } else if (it.second == HomeViewModel.State.TO_SELECT_LINES) {
            gotoActivity(SelectLinesActivity.class);
        } else if (it.second == HomeViewModel.State.TO_GROUP_JOINED) {
            gotoActivity(GroupJoinedActivity.class);
        } else if (it.second == HomeViewModel.State.TO_BEGIN) {
            gotoActivity(BeginActivity.class);
        }
    }


}
