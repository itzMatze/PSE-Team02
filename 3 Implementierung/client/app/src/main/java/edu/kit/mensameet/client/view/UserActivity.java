package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.view.databinding.ActivityUserBinding;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;
import edu.kit.mensameet.client.viewmodel.UserViewModel;

public class UserActivity extends MensaMeetActivity {
    private UserViewModel viewModel;
    private ActivityUserBinding binding;
    private UserItem userItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        super.viewModel = viewModel;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        LinearLayout container = findViewById(R.id.container);

        User user = MensaMeetSession.getInstance().getUser();
        if (user == null) {
            user = new User();
        }
        userItem = new UserItem(this, MensaMeetItem.DisplayMode.BIG_EDITABLE, user);
        container.addView(userItem.getView());

        reloadData();

        Toast.makeText(this, R.string.change_picture_by_click, Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);

        // If it is not the very first login
        if (MensaMeetSession.getInstance().getUser() != null) {

            if (buttonNext != null) {
                buttonNext.setText(R.string.save);
            }

            if (buttonBack != null) {
                buttonBack.setText(R.string.discard);
            }

        }

    }

    @Override
    protected void reloadData() {

        User user = MensaMeetSession.getInstance().getUser();

        if (user != null) {

            //Toast.makeText(this, MensaMeetSession.getInstance().getChosenLines().size(), Toast.LENGTH_SHORT).show();

            userItem.setObjectData(user);
            userItem.fillObjectData();

        }
    }

    @Override
    protected void processStateChange(Pair<MensaMeetViewModel, StateInterface> it) {
        if (it.second == UserViewModel.State.USER_SAVED_NEXT) {
            Toast.makeText(this, R.string.user_saved, Toast.LENGTH_SHORT).show();
        } else if (it.second == UserViewModel.State.BACK) {
            super.onBackPressed();
        } else if (it.second ==  UserViewModel.State.ERROR_SAVING_USER) {
            Toast.makeText(this, R.string.error_saving_user, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickNext() {
        userItem.saveEditedObjectData();
        MensaMeetSession.getInstance().setUser(userItem.getObjectData());
        viewModel.setUser(userItem.getObjectData());
        viewModel.saveUserAndNext();
    }

    @Override
    public void onClickBack() {
        userItem.saveEditedObjectData();
        MensaMeetSession.getInstance().setUser(userItem.getObjectData());
        viewModel.setUser(userItem.getObjectData());
        Toast.makeText(this, R.string.user_data_changes_discarded, Toast.LENGTH_SHORT).show();
        gotoActivity(SelectGroupActivity.class);
    }

}
