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

        //to visit SharedPreferences from view model
        viewModel.context = this;

        /*
        decide which activity to start
         */
        final LoginActivity context = this;
        viewModel.getUiEventLiveData().observe(this, new Observer<Pair<LoginViewModel, String>>() {
            @Override
            public void onChanged(@Nullable Pair<LoginViewModel, String> it) {
                switch(it.second){
                    case LoginViewModel.LOG_IN_ID:
                        int returnCode = viewModel.login();
                        if (returnCode == 0) {
                            Intent intent = new Intent(context, HomeActivity.class);
                            startActivity(intent);
                        }else if(returnCode == 1) {
                            Toast toast = Toast.makeText(getApplicationContext(),"something wrong1",Toast.LENGTH_SHORT);
                            toast.show();
                        }else{
                            Toast toast = Toast.makeText(getApplicationContext(),"something wrong2",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        break;
                    default:
                        Toast toast = Toast.makeText(getApplicationContext(),"something wrong3",Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                }
            }
        });
    }


}
