package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.view.databinding.ActivityLoginBinding;
import edu.kit.mensameet.client.viewmodel.HomeViewModel;
import edu.kit.mensameet.client.viewmodel.LoginViewModel;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

/**
 * Activity for the user login.
 */
public class LoginActivity extends MensaMeetActivity {
    //todo
    public static final String UID_ID = "uid";
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        super.viewModel = viewModel;
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        super.onCreate(savedInstanceState);

    }

    /** Hook method for livedata processing
     *
     * @param it Message.
     */
    protected void processStateChange(Pair<String, StateInterface> it) {
        if (it.second == LoginViewModel.State.LOG_IN_SUCCESS_ID) {
            // log in success, update UI with the logged-in user's information
            Toast.makeText(this, R.string.login_succeeded, Toast.LENGTH_LONG).show();
                   /*Intent toHome = new Intent(context, HomeActivity.class);
                   toHome.putExtra(UID_ID, viewModel.getUid());
                   startActivity(toHome);*/
            gotoActivity(HomeActivity.class);
            finish();// todo: apply isLogIn(), that register and login page not visitable

        } else if (it.second == LoginViewModel.State.LOG_IN_FAILED_ID) {
            String errorMessage = getResources().getString(R.string.login_failed) + ": " + it.first;
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        } else if (it.second == LoginViewModel.State.TEST_ID_HOME) { //todo: remove after testing

            gotoActivity(HomeActivity.class);
        } else if (it.second == LoginViewModel.State.TEST_ID_USER) {

            gotoActivity(UserActivity.class);
        }
    }
}
