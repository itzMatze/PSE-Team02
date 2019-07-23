package edu.kit.mensameet.client.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.view.databinding.ActivityRegisterBinding;
import edu.kit.mensameet.client.viewmodel.RegisterViewModel;

public class RegisterActivity extends MensaMeetActivity {

    //todo
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
        viewModel.getUiEventLiveData().observe(this, new Observer<Pair<RegisterViewModel, String>>() {
            @Override
            public void onChanged(Pair<RegisterViewModel, String> it) {
                switch (it.second) {
                    case RegisterViewModel.CREATE_ACCOUNT_SUCCESS_ID:
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(context, "create acount succeed", Toast.LENGTH_LONG).show();
                        Intent toHome = new Intent(context, HomeActivity.class);
                        toHome.putExtra(UID_ID, viewModel.getUid());
                        startActivity(toHome);
                        finish();// todo: apply isLogIn(), that register and login page not visitable
                        break;
                    case RegisterViewModel.CREATE_ACCOUNT_FAILED_ID:
                        Toast.makeText(context, "failed ", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
