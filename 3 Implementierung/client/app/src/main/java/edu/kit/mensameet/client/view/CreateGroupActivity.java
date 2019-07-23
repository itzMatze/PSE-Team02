package edu.kit.mensameet.client.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.view.databinding.ActivityCreateGroupBinding;
import edu.kit.mensameet.client.viewmodel.CreateGroupViewModel;


public class CreateGroupActivity extends MensaMeetActivity {

    private ActivityCreateGroupBinding binding;
    private CreateGroupViewModel viewModel;
    //todo: move to MensaMeetActivity
    private static final int SAVE_TIME_REQUEST = 5;
    private static final int HOME_MENU_REQUEST = 6;
    private static final String SAVE_TIME_ID = "saveTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_group);
        viewModel = ViewModelProviders.of(this).get(CreateGroupViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        /*
        decide which activity to start
         */
        final CreateGroupActivity context = this;
        final Intent backToHome = new Intent(this, HomeActivity.class);
        final Intent chooseTime = new Intent(this, SetTimeActivity.class);
        viewModel.getUiEventLiveData().observe(this, new Observer<Pair<CreateGroupViewModel, String>>() {
            @Override
            public void onChanged(@Nullable Pair<CreateGroupViewModel, String> it) {
                switch (it.second) {
                    case CreateGroupViewModel.CREATE_GROUP_ID:
                        context.startActivityForResult(backToHome, HOME_MENU_REQUEST);
                        break;
                    case CreateGroupViewModel.CREATE_GROUP_ADD_TIME_ID:
                        context.startActivityForResult(chooseTime, SAVE_TIME_REQUEST);
                        break;
                    case CreateGroupViewModel.CREATE_GROUP_TO_SELECT_GROUP_ID:
                        //TODO: add context.startActivityForResult(chooseTime, TO_SELECT_LINES_REQUEST);
                        break;
                    default:
                        break;
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SAVE_TIME_REQUEST:
                String time = data.getStringExtra(SAVE_TIME_ID);
                if (time != null) {
                    viewModel.addTime(time);
                }
                break;
            default:
                break;
        }
    }


}
