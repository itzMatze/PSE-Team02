package edu.kit.mensameet.client.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

/**
 * This is the abstract base activity for MensaMeet, summarizing the basic functions as LiveData notification between
 * activity and view model shared by all activities.
 *
 */
public abstract class MensaMeetActivity extends AppCompatActivity {

    protected MensaMeetViewModel viewModel;
    protected Button buttonHome;
    protected Button buttonBack;
    protected Button buttonNext;

    protected BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Always keep app in portrait position
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    protected void initializeButtons() {

        buttonHome = (Button)findViewById(R.id.navigation_home);
        if (buttonHome != null) {
            buttonHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickHome();
                }
            });
        }

        // Click listener for back button
        buttonBack = (Button)findViewById(R.id.navigation_back);
        if (buttonBack != null) {
            buttonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBack();
                }
            });
        }

        // Click listener for next button
        buttonNext = (Button)findViewById(R.id.navigation_next);
        if (buttonNext != null) {
            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickNext();
                }
            });
        }

    }

    protected void initializeViewModel(MensaMeetViewModel viewModel) {

        this.viewModel = viewModel;

    }

    protected void observeLiveData() {
        // Initializing livedata communication between view model and activity
        if (viewModel != null) {
            viewModel.getEventLiveData().observe(this, new Observer<Pair<String, StateInterface>>() {
                @Override
                public void onChanged(@Nullable Pair<String, StateInterface> it) {

                    processStateChange(it);

                }
            });
        }
    }

    /** Hook method for livedata processing
     *
     * @param it Message.
     */
    protected void processStateChange(Pair<String, StateInterface> it) {

    }

    /**
     * Hook method before redirecting to home activity.
      */

    protected void beforeGotoHome() {

    }

    /**
     * Click method for home button.
      */
    public void onClickHome() {
        beforeGotoHome();
        gotoHome();
    }

    /**
     * Click method for next button.
     */
    public void onClickNext() {};

    /**
     * Click method for back button.
     */
    public void onClickBack() {};

    // Method for redirecting home.
    protected void gotoHome() {
        // TODO: do not hardcode the name of the home activity instead, fetch homeActivity from MensaMeetSession

        gotoActivity(HomeActivity.class);
    }

    // General method for
    protected void gotoActivity(Class<?> activity) {
        if (activity != null) {
            Intent intent = new Intent(this, activity);
            startActivity(intent);
        }
    }

    /**
     * Shows a toast message using eventLiveData content.
     *
     * @param context activity
     * @param id string resource id of the message to be shown
     * @param it eventLiveData content
     */
    protected void showMessage(Context context, @StringRes int id, Pair<String, StateInterface> it) {

        String message = getResources().getString(id);

        if (it.first != null && !it.first.equals("")) {
            message += ": " + it.first;
        }

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    }

    /**
     * Checks if the activity can be accessed.
     *
     * @return if the activity can be accessed.
     */
    protected Boolean checkAccess() {
        return false;
    }

    @Override
    public void onBackPressed() {
        // Deactivate Android back button.
    }

    protected void goBack() {
        super.onBackPressed();
    }
}