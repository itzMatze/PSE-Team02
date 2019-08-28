package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;

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
        super.viewModel = viewModel;

        // Illegal state to show activity, go back (if user is not null, there was another activity before.
        if (viewModel.getUser() != null) {
            onBackPressed();
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        observeLiveData();
        initializeButtons();

    }

    /** Hook method for livedata processing
     *
     * @param it Message.
     */
    protected void processStateChange(Pair<String, StateInterface> it) {
        if (it.second == LoginViewModel.State.LOG_IN_SUCCESS) {

            showMessage(this, R.string.login_succeeded, it);

            finish();
            gotoActivity(HomeActivity.class);

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
}
