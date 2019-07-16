package edu.kit.mensameet.client.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import edu.kit.mensameet.client.view.databinding.ActivitySetTimeBinding;
import edu.kit.mensameet.client.viewmodel.SetTimeViewModel;

public class SetTimeActivity extends MensaMeetActivity{
    private ActivitySetTimeBinding binding;
    private SetTimeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_set_time);
        viewModel = ViewModelProviders.of(this).get(SetTimeViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

    }


}
