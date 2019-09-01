package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.view.databinding.ActivityLoginBinding;
import edu.kit.mensameet.client.viewmodel.LoginViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

/**
 * Activity for the user login.
 */
public class LoginActivity extends MensaMeetActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        super.initializeViewModel(viewModel);

        if (!checkAccess()) {
            return;
        };

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        observeLiveData();
        initializeButtons();

        // Hide other buttons.
        if (buttonNext != null) {
            buttonNext.setVisibility(View.GONE);
        }

        if (buttonHome != null) {
            buttonHome.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClickBack() {
        gotoActivity(BeginActivity.class);
    }

    /** Hook method for livedata processing
     *
     * @param it Message.
     */
    protected void processStateChange(Pair<String, StateInterface> it) {
        if (it.second == LoginViewModel.State.LOG_IN_SUCCESS) {

            showMessage(this, R.string.login_succeeded, it);

            if (viewModel.currentUserDataIncomplete()) {
                finish();
                gotoActivity(UserActivity.class);
            } else {
                finish();
                gotoActivity(HomeActivity.class);
            }


        } else if (it.second == LoginViewModel.State.LOG_IN_FAILED) {

            showMessage(this, R.string.login_failed, it);

        } else if (it.second == LoginViewModel.State.CREDENTIALS_INCOMPLETE) {

            showMessage(this, R.string.credentials_incomplete, it);

        } else if (it.second == LoginViewModel.State.INITIALIZATION_FAILED) {

            showMessage(this, R.string.initialization_failed, it);

            // Invalidate created session data.
            viewModel.invalidateSession();

        } else if (it.second == LoginViewModel.State.LOADING_USER_FAILED) {

            showMessage(this, R.string.reloading_user_failed, it);

            // Invalidate created session data.
            viewModel.invalidateSession();

        }

    }

    @Override
    protected Boolean checkAccess() {
        // Illegal state to show activity, go back (if user is not null, there was another activity before.
        if (viewModel.getUser() != null) {
            finish();
            return false;
        }
        return true;
    }
}
