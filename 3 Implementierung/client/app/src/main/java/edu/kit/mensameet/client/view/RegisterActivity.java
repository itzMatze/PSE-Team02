package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.view.databinding.ActivityRegisterBinding;
import edu.kit.mensameet.client.viewmodel.RegisterViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

/**
 * Activity with the registration form for new users.
 */
public class RegisterActivity extends MensaMeetActivity {

    private ActivityRegisterBinding binding;
    protected RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        super.viewModel = viewModel;

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!checkAccess()) {
            return;
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
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
        if (it.second == RegisterViewModel.State.CREATE_ACCOUNT_SUCCESS) {

            showMessage(this, R.string.registration_succeeded, it);

            finish();
            gotoActivity(UserActivity.class);

        } else if (it.second == RegisterViewModel.State.CREATE_ACCOUNT_FAILED) {

            showMessage(this, R.string.registration_failed, it);

        } else if (it.second == RegisterViewModel.State.ACCOUNT_CREATED_BUT_INITIALIZATION_FAILED) {

            showMessage(this, R.string.account_created_but_initialization_failed, it);

            // Invalidate created session data.
            viewModel.invalidateSession();
            finish();
            gotoActivity(BeginActivity.class);

        } else if (it.second == RegisterViewModel.State.LOADING_NEW_USER_FAILED) {

            showMessage(this, R.string.reloading_user_failed, it);
            // Invalidate created session data.
            viewModel.invalidateSession();

        } else if (it.second == RegisterViewModel.State.PASSWORDS_NOT_MATCHING) {

            showMessage(this, R.string.passwords_not_equal_message, it);

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
