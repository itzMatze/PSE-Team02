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

    public static final String UID_ID = "uid";
    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //[start]data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        super.viewModel = viewModel;
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.setContext(this);
        //[end]data binding

        super.onCreate(savedInstanceState);

    }


    /** Hook method for livedata processing
     *
     * @param it Message.
     */
    protected void processStateChange(Pair<String, StateInterface> it) {
        if (it.second == RegisterViewModel.State.CREATE_ACCOUNT_SUCCESS_ID) {
            // Sign in success, update UI with the signed-in user's information
            Toast.makeText(this, R.string.registration_succeeded, Toast.LENGTH_LONG).show();
            gotoActivity(UserActivity.class);
            finish();// todo: apply isLogIn(), that register and login page not visitable

        }   else if (it.second == RegisterViewModel.State.CREATE_ACCOUNT_FAILED_ID) {

            String errorMessage = getResources().getString(R.string.registration_failed) + ": " + it.first;
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

}
