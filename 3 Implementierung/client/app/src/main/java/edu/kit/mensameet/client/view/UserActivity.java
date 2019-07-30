package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.view.databinding.ActivityUserBinding;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;
import edu.kit.mensameet.client.viewmodel.UserViewModel;

/**
 * The activity for the current user to edit his personal profile. Is evoked right after successful registration
 * in {@link RegisterActivity}, before getting to {@link HomeActivity}. Later, it can always be accessed for changes in the profile.
 *
 */
public class UserActivity extends MensaMeetActivity {

    private UserViewModel viewModel;
    private ActivityUserBinding binding;
    /**
     * The item class containing the view of a single user profile.
     */
    private UserItem userItem;
    /**
     * Depending on the completeness of the user data, the activity shows different behavior, it has another mode.
     */
    private boolean userDataIncomplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        super.viewModel = viewModel;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        // The container to load the view of the user item into.
        LinearLayout container = findViewById(R.id.container);

        // The current user object.
        User user = MensaMeetSession.getInstance().getUser();
        if (user == null) {
            user = new User();
        }

        // The user item.
        userItem = new UserItem(this, MensaMeetItem.DisplayMode.BIG_EDITABLE, user);

        // The user item is added to the container.
        container.addView(userItem.getView());

        Toast.makeText(this, R.string.change_picture_by_click, Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);

        /* If no or incomplete user data is given, the user should not be able to go back or away, hence only the
            next button with caption 'save' is provided.
         */

        reloadData();

    }

    /**
     * Reloads the user data from MensaMeetSession.
     */
    @Override
    protected void reloadData() {

        User user = MensaMeetSession.getInstance().getUser();

        Toast.makeText(this, "Data reloaded.", Toast.LENGTH_SHORT).show();
        if (user != null) {

            userItem.setObjectData(user);
            userItem.fillObjectData();

        }

        // Rename next button.
        if (buttonNext != null) {
            buttonNext.setText(R.string.save);
        }

        userDataIncomplete = MensaMeetSession.getInstance().userDataIncomplete();

        if (userDataIncomplete) {

            // Hide other buttons.
            if (buttonBack != null) {
                buttonBack.setVisibility(View.GONE);
            }

            if (buttonHome != null) {
                buttonHome.setVisibility(View.GONE);
            }

        } else {

            /* The back button is used to discard changes */
            if (buttonBack != null) {
                buttonBack.setText(R.string.discard);
            }

        }

    }

    /**
     * How the activity reacts on the state changes of the view model.
     *
     * @param it The state data.
     */
    @Override
    protected void processStateChange(Pair<MensaMeetViewModel, StateInterface> it) {

        if (it.second == UserViewModel.State.USER_SAVED_NEXT) {
            Toast.makeText(this, R.string.user_saved, Toast.LENGTH_SHORT).show();
            gotoActivity(HomeActivity.class);
        } else if (it.second == UserViewModel.State.BACK) {
            onBackPressed();
        } else if (it.second ==  UserViewModel.State.ERROR_SAVING_USER) {
            Toast.makeText(this, R.string.error_saving_user, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * The next button is used for saving.
     */
    @Override
    public void onClickNext() {

        userItem.saveEditedObjectData();
        viewModel.setUser(userItem.getObjectData());
        viewModel.saveUserAndNext();

    }

    /**
     * The back button is used for discarding.
     */
    @Override
    public void onClickBack() {

        Toast.makeText(this, R.string.user_data_changes_discarded, Toast.LENGTH_SHORT).show();
        gotoActivity(HomeActivity.class);

    }

}
