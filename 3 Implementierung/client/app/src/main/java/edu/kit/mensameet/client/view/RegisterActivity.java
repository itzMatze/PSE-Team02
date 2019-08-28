package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.view.databinding.ActivityRegisterBinding;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.RegisterViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

/**
 * Activity with the registration form for new users.
 */
public class RegisterActivity extends MensaMeetActivity {

    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        super.viewModel = viewModel;

        // Illegal state to show activity, go back (if user is not null, there was another activity before.
        if (viewModel.getUser() != null) {
            onBackPressed();
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
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
        if (it.second == RegisterViewModel.State.CREATE_ACCOUNT_SUCCESS) {

            showMessage(this, R.string.registration_succeeded, it);

            finish();
            gotoActivity(UserActivity.class);

        } else if (it.second == RegisterViewModel.State.CREATE_ACCOUNT_FAILED) {

            showMessage(this, R.string.registration_failed, it);

        } else if (it.second == RegisterViewModel.State.INITIALIZATION_FAILED) {

            showMessage(this, R.string.initialization_failed, it);

            // Invalidate created session data.
            viewModel.invalidateSession();

        } else if (it.second == RegisterViewModel.State.LOADING_NEW_USER_FAILED) {

            showMessage(this, R.string.reloading_user_failed, it);
            // Invalidate created session data.
            viewModel.invalidateSession();

        } else if (it.second == RegisterViewModel.State.PASSWORDS_NOT_MATCHING) {

            showMessage(this, R.string.passwords_not_equal_message, it);

        }
    }

}
