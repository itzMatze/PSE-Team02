package edu.kit.mensameet.client.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.view.databinding.ActivityGroupJoinedBinding;
import edu.kit.mensameet.client.viewmodel.GroupItemHandler;
import edu.kit.mensameet.client.viewmodel.GroupJoinedViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;
import edu.kit.mensameet.client.viewmodel.UserItemHandler;

/**
 * Activity shown when the user has joined a group. Then it replaces SelectLinesActivity until the meeting is over or when the user leaves the group.
 */
public class GroupJoinedActivity extends MensaMeetActivity {

    private GroupJoinedViewModel viewModel;
    private ActivityGroupJoinedBinding binding;
    private GroupItem groupItem;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(GroupJoinedViewModel.class);
        super.initializeViewModel(viewModel);

        // Illegal state to show activity, go back.
        if (viewModel.currentUserDataIncomplete()) {
            onBackPressed();
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_joined);
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

        if (buttonBack != null) {
            buttonBack.setText(R.string.leave_group);
        }

        container = findViewById(R.id.container);


        String groupToken = viewModel.getUser().getGroupToken();

        if (groupToken == null || groupToken.equals("")) {

            Toast.makeText(GroupJoinedActivity.this, R.string.no_group_joined, Toast.LENGTH_LONG).show();

            finish();
            gotoActivity(HomeActivity.class);
            return;
        }

        if (viewModel.setGroupByToken(groupToken) == false) {
            return;
        }

        Group joinedGroup = viewModel.getGroup();

        if (joinedGroup == null) {
            Toast.makeText(GroupJoinedActivity.this, R.string.no_group_joined, Toast.LENGTH_LONG).show();

            finish();
            gotoActivity(HomeActivity.class);

            return;
        }

        groupItem = new GroupItem(this, MensaMeetItem.DisplayMode.BIG_NOTEDITABLE,
                joinedGroup);

        container.addView(groupItem.getView());

        groupItem.fillObjectData();

        groupItem.getHandler().getEventLiveData().observe(this, new Observer<Pair<String, StateInterface>>() {
            @Override
            public void onChanged(@Nullable Pair<String, StateInterface> it) {

                if (it.second == GroupItemHandler.State.GROUP_DELETED) {

                    showMessage(GroupJoinedActivity.this, R.string.group_deleted, it);

                    finish();
                    gotoActivity(HomeActivity.class);

                } else if (it.second == GroupItemHandler.State.GROUP_DELETION_FAILED) {

                    showMessage(GroupJoinedActivity.this, R.string.group_deletion_failed, it);

                } else if (it.second == GroupItemHandler.State.RELOADING_USER_FAILED) {

                    showMessage(GroupJoinedActivity.this, R.string.reloading_user_failed, it);

                    // Logout.
                    viewModel.invalidateSession();
                    finish();
                    gotoActivity(HomeActivity.class);
                }
            }
        });

        for (int j = 0; j < groupItem.getUserList().getItemCount(); j++) {

            UserItem userItem = (UserItem)groupItem.getUserList().getItem(j);

            if (userItem != null) {

                UserItemHandler userItemHandler = userItem.getHandler();

                userItemHandler.getEventLiveData().observe(this, new Observer<Pair<String, StateInterface>>() {
                    @Override
                    public void onChanged(@Nullable Pair<String, StateInterface> it) {

                        if (it.second == UserItemHandler.State.SHOW_USER) {

                            gotoActivity(ShowUserActivity.class);

                        } else if (it.second == UserItemHandler.State.USER_DELETED) {

                            showMessage(GroupJoinedActivity.this, R.string.user_deleted, it);

                            finish();
                            startActivity(getIntent());

                        } else if (it.second == UserItemHandler.State.USER_DELETED_SERVER_NOT_FIREBASE) {

                            showMessage(GroupJoinedActivity.this, R.string.user_deleted_server_not_firebase, it);

                            finish();
                            startActivity(getIntent());

                        } else if (it.second == UserItemHandler.State.USER_DELETION_FAILED) {

                            showMessage(GroupJoinedActivity.this, R.string.user_deletion_failed, it);

                        }

                    }
                });

            }


        }

    }

    @Override
    protected void processStateChange(Pair<String, StateInterface> it) {
        if (it.second == GroupJoinedViewModel.State.GROUP_LEFT) {

            finish();
            gotoActivity(HomeActivity.class);
 
        } else if (it.second == GroupJoinedViewModel.State.ERROR_LOADING_GROUP) {

            showMessage(this, R.string.error_loading_group, it);

            finish();
            gotoActivity(HomeActivity.class);

        } else if (it.second == GroupJoinedViewModel.State.ERROR_LEAVING_GROUP) {

            showMessage(this, R.string.error_leaving_group, it);

        } else if (it.second == GroupItemHandler.State.RELOADING_USER_FAILED) {

            showMessage(this, R.string.reloading_user_failed, it);

            // Logout.
            viewModel.invalidateSession();
            finish();
            gotoActivity(HomeActivity.class);
        }
    }

    @Override
    public void onClickBack() {

        new AlertDialog.Builder(this)
                .setTitle(R.string.leave_group)
                .setMessage(R.string.really_leave_group)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        viewModel.leaveGroup();

                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}
