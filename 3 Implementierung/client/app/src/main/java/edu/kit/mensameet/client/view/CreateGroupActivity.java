package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.view.databinding.ActivityCreateGroupBinding;
import edu.kit.mensameet.client.viewmodel.CreateGroupViewModel;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

public class CreateGroupActivity extends MensaMeetActivity {
    private CreateGroupViewModel viewModel;
    private ActivityCreateGroupBinding binding;
    private GroupItem groupItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(this).get(CreateGroupViewModel.class);
        super.viewModel = viewModel;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_group);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        LinearLayout container = findViewById(R.id.container);

        groupItem = new GroupItem(this, MensaMeetItem.DisplayMode.BIG_EDITABLE, new Group());
        container.addView(groupItem.getView());

        reloadData();

        super.onCreate(savedInstanceState);

    }

    @Override
    protected void reloadData() {

        Group sessionGroup = MensaMeetSession.getInstance().getCreatedGroup();

        if (sessionGroup != null) {

            //Toast.makeText(this, MensaMeetSession.getInstance().getChosenLines().size(), Toast.LENGTH_SHORT).show();

            groupItem.setObjectData(sessionGroup);
            groupItem.fillObjectData();

            //lineList.setSelectedObjects(MensaMeetSession.getInstance().getChosenLines());


        }
    }

    @Override
    protected void processStateChange(Pair<MensaMeetViewModel, StateInterface> it) {
        if (it.second == CreateGroupViewModel.State.GROUP_SAVED_NEXT) {
            Toast.makeText(this, R.string.group_saved, Toast.LENGTH_SHORT).show();
        } else if (it.second ==  CreateGroupViewModel.State.BACK) {
            gotoActivity(SelectGroupActivity.class);
        } else if (it.second ==  CreateGroupViewModel.State.ERROR_SAVING_GROUP) {
            Toast.makeText(this, R.string.error_saving_group, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickNext() {
        groupItem.saveEditedObjectData();
        MensaMeetSession.getInstance().setCreatedGroup(groupItem.getObjectData());
        viewModel.setGroup(groupItem.getObjectData());
        viewModel.saveGroupAndNext();
    }

    @Override
    public void onClickBack() {
        groupItem.saveEditedObjectData();
        MensaMeetSession.getInstance().setCreatedGroup(groupItem.getObjectData());
        viewModel.setGroup(groupItem.getObjectData());
        Toast.makeText(this, R.string.group_data_locally_saved, Toast.LENGTH_SHORT).show();
        gotoActivity(SelectGroupActivity.class);
    }
}
