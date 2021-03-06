package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.view.databinding.ActivityShowUserBinding;
import edu.kit.mensameet.client.viewmodel.ShowUserViewModel;

/**
 * The activity to show a user's profile. The user is transmitted
 * */
public class ShowUserActivity extends MensaMeetActivity {

    protected ShowUserViewModel viewModel;
    private ActivityShowUserBinding binding;
    private UserItem userItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ShowUserViewModel.class);
        initializeViewModel(viewModel);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!checkAccess()) {
            return;
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_user);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        observeLiveData();
        initializeButtons();

        if (buttonNext != null) {
            if (buttonHome != null) {
                buttonHome.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            }
            buttonNext.setVisibility(View.GONE);
        }

        LinearLayout container = findViewById(R.id.container);

        User user = viewModel.getUserToShow();

        userItem = new UserItem(this, MensaMeetItem.DisplayMode.BIG_NOTEDITABLE, user);

        container.addView(userItem.getView());

        userItem.fillObjectData();
    }

    @Override
    protected Boolean checkAccess() {
        // Illegal state to show activity, go back.
        if (viewModel.currentUserDataIncomplete() || viewModel.getUserToShow() == null) {
            finish();
            return false;
        }
        return true;
    }

    @Override
    public void onClickBack() {
        goBack();
    }
}
