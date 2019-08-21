package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.view.databinding.ActivityBeginBinding;
import edu.kit.mensameet.client.viewmodel.BeginViewModel;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

/**
 * The activity at the beginning of a MensaMeet session.
 */
public class BeginActivity extends MensaMeetActivity {

    private ActivityBeginBinding binding;
    private BeginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_begin);
        viewModel = ViewModelProviders.of(this).get(BeginViewModel.class);
        super.viewModel = viewModel;
        observeLiveData();
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        //if there is already a user logged in on this device, switch directly to the HomeActivity
        if (viewModel.isLoggedIn(this)) {
            gotoHome();
        }

    }

    /** Hook method for livedata processing
     *
     * @param it Message.
     */
    @Override
    protected void processStateChange(Pair<String, StateInterface> it) {

        /*
            decide which activity to start
         */

        if (it.second == BeginViewModel.State.LOGIN_ID) {
            gotoActivity(LoginActivity.class);
        } else if (it.second == BeginViewModel.State.REGISTER_ID) {
            gotoActivity(RegisterActivity.class);
        } else if (it.second == BeginViewModel.State.TEST_ID) { // todo: remove after testing
            gotoActivity(UserActivity.class);
        }
    }
}
