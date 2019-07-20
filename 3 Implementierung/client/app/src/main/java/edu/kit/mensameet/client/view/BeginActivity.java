package edu.kit.mensameet.client.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.os.Bundle;
import edu.kit.mensameet.client.view.databinding.ActivityBeginBinding;
import edu.kit.mensameet.client.viewmodel.BeginViewModel;

public class BeginActivity extends MensaMeetActivity {
    private ActivityBeginBinding binding;
    private BeginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_begin);
        viewModel = ViewModelProviders.of(this).get(BeginViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);
        //wenn auf dem Gerät bereits ein Benutzer angemeldet ist, wechsle direkt zur HomeActivity
        if (viewModel.isLoggedIn(this)) {
            gotoHome();
        }

         /*
        decide which activity to start
         */
        final MensaMeetActivity context = this;
        viewModel.getUiEventLiveData().observe(this, new Observer<Pair<BeginViewModel, String>>() {
            @Override
            public void onChanged(@Nullable Pair<BeginViewModel, String> it) {
                switch(it.second){
                    case BeginViewModel.LOGIN_ID:
                        context.gotoActivity(LoginActivity.class);
                        break;
                    case BeginViewModel.REGISTER_ID:
                        context.gotoActivity(RegisterActivity.class);
                        break;
                    case BeginViewModel.RESET_LOCAL_DATA_ID:
                        SharedPreferences sharedPrefs = getSharedPreferences("MensaMeetApp", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.clear();
                        editor.commit();
                        break;
                    case BeginViewModel.TEST_LIST_CLASSES_ID:
                        gotoActivity(TestActivity.class);
                        break;
                    default:
                        break;
                }

            }
        });
    }

}
