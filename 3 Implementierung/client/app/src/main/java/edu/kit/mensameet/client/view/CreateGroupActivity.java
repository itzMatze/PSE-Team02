package edu.kit.mensameet.client.view;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.databinding.DataBindingUtil;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.util.Pair;

import edu.kit.mensameet.client.view.databinding.ActivityCreateGroupBinding;
import edu.kit.mensameet.client.viewmodel.CreateGroupViewModel;


public class CreateGroupActivity extends MensaMeetActivity {

    private ActivityCreateGroupBinding binding;
    private CreateGroupViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_group);
        viewModel = ViewModelProviders.of(this).get(CreateGroupViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        final MensaMeetActivity context = this;
        final Intent intent1 = new Intent(this, HomeActivity.class);

        viewModel.getUiEventLiveData().observe(this, new Observer<Pair<CreateGroupViewModel, String>>() {
            @Override
            public void onChanged(@Nullable Pair<CreateGroupViewModel, String> it) {

                switch(it.second){
                    case "createGroup":
                        context.startActivity(intent1);
                        break;
                    default:
                        break;
                }

            }
        });

    }


}
