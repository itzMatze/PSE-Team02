package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MealLines;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.MensaMeetTime;
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

        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(CreateGroupViewModel.class);
        super.initializeViewModel(viewModel);

        // Illegal state to show activity, go back.
        if (viewModel.currentUserDataIncomplete()) {
            onBackPressed();
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_group);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        observeLiveData();
        initializeButtons();

        LinearLayout container = findViewById(R.id.container);

        // initialize group with chosen time and line
        Group createdGroup = viewModel.getCreatedGroup();

        if (createdGroup == null) {
            createdGroup = new Group();

            MensaMeetTime time = viewModel.getChosenTime();
            if (time == null) {
                // Default value
                time = new MensaMeetTime(MensaMeetTime.stringToTime("12:00"));
            }

            createdGroup.setMeetingDate(time.getStartTime());

            List<Line> lines = viewModel.getChosenLines();
            String mealLine;
            if (lines == null) {
                // Default value
                mealLine = MealLines.values()[0].toString();
            } else {
                mealLine = lines.get(0).getMealLine();
            }

            createdGroup.setLine(mealLine);
        }

        viewModel.setCreatedGroup(createdGroup);

        // The item is instantiated as big and editable.
        groupItem = new GroupItem(this, MensaMeetItem.DisplayMode.BIG_EDITABLE, createdGroup);
        container.addView(groupItem.getView());

        Toast.makeText(this, R.string.delete_created_group_by_leaving, Toast.LENGTH_LONG).show();
    }

    /**
     * Hook method implementation to reload data.
     */
    @Override
    protected void reloadData() {

        Group createdGroup = viewModel.getCreatedGroup();

        if (createdGroup != null) {

            groupItem.setObjectData(createdGroup);
            groupItem.fillObjectData();

        }
    }

    /**
     * Live data communication with the view model.
     *
     * @param it Message.
     */
    @Override
    protected void processStateChange(Pair<String, StateInterface> it) {
        if (it.second == CreateGroupViewModel.State.GROUP_SAVED) {

            showMessage(this, R.string.group_saved, it);

            finish();
            gotoActivity(GroupJoinedActivity.class);

        } else if (it.second ==  CreateGroupViewModel.State.ERROR_SAVING_GROUP) {

            showMessage(this, R.string.error_saving_group, it);

        } else if (it.second ==  CreateGroupViewModel.State.RELOADING_USER_FAILED) {

            showMessage(this, R.string.reloading_user_failed, it);

            // Logout.
            viewModel.invalidateSession();
            finish();
            gotoActivity(HomeActivity.class);

        }
    }

    /**
     * At next, the data is saved. The view model is asked to submit it to the server.
     */
    @Override
    public void onClickNext() {
        groupItem.saveEditedObjectData();
        viewModel.setCreatedGroup(groupItem.getObjectData());

        if (viewModel.createdGroupDataIncomplete(this)) {
            Toast.makeText(this, R.string.no_field_empty, Toast.LENGTH_LONG).show();
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
        viewModel.setCreatedGroup(groupItem.getObjectData());

        Toast.makeText(this, R.string.group_data_locally_saved, Toast.LENGTH_LONG).show();
        gotoActivity(SelectGroupActivity.class);
    }
}
