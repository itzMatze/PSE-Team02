package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.model.MensaMeetSession;
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

        viewModel = ViewModelProviders.of(this).get(BeginViewModel.class);
        super.initializeViewModel(viewModel);

        // Illegal state to show activity, go back (if user is not null, there was another activity before.
        if (viewModel.getUser() != null) {
            onBackPressed();
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_begin);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        observeLiveData();
        initializeButtons();

        /*
        //if there is already a user logged in on this device, switch directly to the HomeActivity
        if (viewModel.isLoggedIn(this)) {
            gotoHome();
        }
        */

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

        if (it.second == BeginViewModel.State.LOGIN) {
            gotoActivity(LoginActivity.class);
        } else if (it.second == BeginViewModel.State.REGISTER) {
            gotoActivity(RegisterActivity.class);
        }
    }
}
