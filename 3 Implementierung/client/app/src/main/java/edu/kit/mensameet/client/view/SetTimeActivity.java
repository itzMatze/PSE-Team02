package edu.kit.mensameet.client.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Pair;
import edu.kit.mensameet.client.view.databinding.ActivitySetTimeBinding;
import edu.kit.mensameet.client.viewmodel.SetTimeViewModel;

public class SetTimeActivity extends MensaMeetActivity{
    private ActivitySetTimeBinding binding;
    private SetTimeViewModel viewModel;
    private static final int SAVE_TIME_REQUEST = 5;
    private static final String SAVE_TIME_ID = "saveTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_set_time);
        viewModel = ViewModelProviders.of(this).get(SetTimeViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        final SetTimeActivity context = this;

        /*
        decide which activity to start
         */
        viewModel.getUiEventLiveData().observe(this, new Observer<Pair<SetTimeViewModel, String>>() {
            @Override
            public void onChanged(@Nullable Pair<SetTimeViewModel, String> it) {
                switch(it.second){
                    case "saveTime":
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(SAVE_TIME_ID,viewModel.getTimeString());
                        setResult(SAVE_TIME_REQUEST, resultIntent);
                        finish();
                        break;
                    default:
                        break;
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case SAVE_TIME_REQUEST:
                break;
            default:
                break;

        }
    }


}
