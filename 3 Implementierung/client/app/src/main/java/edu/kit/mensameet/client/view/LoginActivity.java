package edu.kit.mensameet.client.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.Nullable;
import android.util.Pair;
import android.os.Bundle;
import android.widget.Toast;
import edu.kit.mensameet.client.view.databinding.ActivityLoginBinding;
import edu.kit.mensameet.client.viewmodel.LoginViewModel;

public class LoginActivity extends MensaMeetActivity {
    //todo
    public static final String UID_ID = "uid";
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        /*
        decide which activity to start
         */
        final LoginActivity context = this;
        viewModel.getUiEventLiveData().observe(this, new Observer<Pair<LoginViewModel, String>>() {
            @Override
            public void onChanged(@Nullable Pair<LoginViewModel, String> it) {
                switch(it.second){
                    case LoginViewModel.LOG_IN_SUCCESS_ID:
                        // log in success, update UI with the logged-in user's information
                        Toast.makeText(context,"create acount succeed",Toast.LENGTH_LONG).show();
                        Intent toHome = new Intent(context, HomeActivity.class);
                        toHome.putExtra(UID_ID, viewModel.getUid());
                        startActivity(toHome);
                        finish();// todo: apply isLogIn(), that register and login page not visitable
                        break;
                    case LoginViewModel.LOG_IN_FAILD_ID:
                        Toast.makeText(context,"failed ",Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }


}
