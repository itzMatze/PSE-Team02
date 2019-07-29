package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.view.databinding.ActivityGroupJoinedBinding;
import edu.kit.mensameet.client.viewmodel.GroupJoinedViewModel;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

public class GroupJoinedActivity extends MensaMeetActivity {

    private GroupJoinedViewModel viewModel;
    private ActivityGroupJoinedBinding binding;
    private GroupItem groupItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(this).get(GroupJoinedViewModel.class);
        super.viewModel = viewModel;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_joined);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        LinearLayout container = findViewById(R.id.container);

        groupItem = new GroupItem(this, MensaMeetItem.DisplayMode.BIG_NOTEDITABLE,
                MensaMeetSession.getInstance().getChosenGroup());
        container.addView(groupItem.getView());

        super.onCreate(savedInstanceState);

        if (buttonNext != null) {
            if (buttonHome != null) {
                buttonHome.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            }
            buttonNext.setVisibility(View.GONE);
        }

        if (buttonBack != null) {
            buttonBack.setText(R.string.leave_group);
        }
    }

    @Override
    protected void reloadData() {

    }

    @Override
    protected void processStateChange(Pair<MensaMeetViewModel, StateInterface> it) {
        if (it.second == GroupJoinedViewModel.State.GROUP_LEFT) {
            this.gotoActivity(HomeActivity.class);
        }
    }

    @Override
    public void onClickBack() {

        viewModel.leaveGroup();
    }
}
