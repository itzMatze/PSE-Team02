package edu.kit.mensameet.client.view;

import android.content.Intent;
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
        super.onCreate(savedInstanceState);
        //[start]data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.setContext(this);
        //[end]data binding

        final RegisterActivity context = this;
        viewModel.getUiEventLiveData().observe(this, new Observer<Pair<MensaMeetViewModel, StateInterface>>() {
            @Override
            public void onChanged(Pair<MensaMeetViewModel, StateInterface> it) {
                if (it.second == RegisterViewModel.State.CREATE_ACCOUNT_SUCCESS_ID) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(context, "create acount succeed", Toast.LENGTH_LONG).show();
                    Intent toUserProfile = new Intent(context, UserActivity.class);
                    toUserProfile.putExtra(UID_ID, viewModel.getUid());
                    startActivity(toUserProfile);
                    finish();// todo: apply isLogIn(), that register and login page not visitable

                }   else if (it.second == RegisterViewModel.State.CREATE_ACCOUNT_FAILED_ID) {

                    Toast.makeText(context, "failed ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
