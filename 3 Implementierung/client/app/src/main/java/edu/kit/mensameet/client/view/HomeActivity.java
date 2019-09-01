package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

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

        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        super.initializeViewModel(viewModel);

        if (!checkAccess()) {
            return;
        };

        setContentView(R.layout.activity_home);

        observeLiveData();
        initializeButtons();

        //SharedPreferences sharedPrefs = getSharedPreferences("MensaMeetApp", Context.MODE_PRIVATE);
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
            finish();
            gotoActivity(BeginActivity.class);
        }
    }

    @Override
    protected Boolean checkAccess() {

        // Illegal state to show activity, go back.
        if (viewModel.getUser() == null) {
            finish();
            return false;
        }
        return true;
    }

}
