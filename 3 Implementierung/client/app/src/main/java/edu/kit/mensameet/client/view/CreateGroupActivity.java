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

/**
 * Activity with the form to create a new group.
 */
public class CreateGroupActivity extends MensaMeetActivity {
    private CreateGroupViewModel viewModel;
    private ActivityCreateGroupBinding binding;
    /*
    Group item containing the form.
     */
    private GroupItem groupItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(this).get(CreateGroupViewModel.class);
        super.viewModel = viewModel;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_group);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        LinearLayout container = findViewById(R.id.container);

        // initialize group with chosen time and line
        Group createdGroup = MensaMeetSession.getInstance().getCreatedGroup();
        if (createdGroup == null) {
            createdGroup = new Group();
            createdGroup.setMeetingDate(MensaMeetSession.getInstance().getChosenTime().getStartTime());
            createdGroup.setLine(MensaMeetSession.getInstance().getChosenLines().get(0).getMealLine());
        }
        MensaMeetSession.getInstance().setCreatedGroup(createdGroup);

        // The item is instantiated as big and editable.
        groupItem = new GroupItem(this, MensaMeetItem.DisplayMode.BIG_EDITABLE, createdGroup);
        container.addView(groupItem.getView());

        //reloadData();

        super.onCreate(savedInstanceState);

    }

    /**
     * Hook method implementation to reload data from the session singleton.
     */
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

    /**
     * Live data communication with the view model.
     *
     * @param it Message.
     */
    @Override
    protected void processStateChange(Pair<String, StateInterface> it) {
        if (it.second == CreateGroupViewModel.State.GROUP_SAVED_NEXT) {
            Toast.makeText(this, R.string.group_saved, Toast.LENGTH_SHORT).show();
            gotoActivity(GroupJoinedActivity.class);
        } else if (it.second ==  CreateGroupViewModel.State.BACK) {
            gotoActivity(SelectGroupActivity.class);
        } else if (it.second ==  CreateGroupViewModel.State.ERROR_SAVING_GROUP) {
            Toast.makeText(this, R.string.error_saving_group, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * At next, the data is saved. The view model is asked to submit it to the server.
     */
    @Override
    public void onClickNext() {
        groupItem.saveEditedObjectData();
        viewModel.setGroup(groupItem.getObjectData());
        MensaMeetSession.getInstance().setCreatedGroup(groupItem.getObjectData());

        if (MensaMeetSession.getInstance().createdGroupDataIncomplete(this)) {
            Toast.makeText(this, R.string.no_field_empty, Toast.LENGTH_SHORT).show();
        } else {
            viewModel.saveGroupAndNext();
        }

    }

    /**
     * At back, the data is saved locally.
     */
    @Override
    public void onClickBack() {
        groupItem.saveEditedObjectData();
        MensaMeetSession.getInstance().setCreatedGroup(groupItem.getObjectData());
        viewModel.setGroup(groupItem.getObjectData());
        Toast.makeText(this, R.string.group_data_locally_saved, Toast.LENGTH_SHORT).show();
        gotoActivity(SelectGroupActivity.class);
    }
}
