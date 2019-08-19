package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.os.Handler;
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
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.RequestUtil;
import edu.kit.mensameet.client.view.databinding.ActivityGroupJoinedBinding;
import edu.kit.mensameet.client.viewmodel.GroupItemHandler;
import edu.kit.mensameet.client.viewmodel.GroupJoinedViewModel;
import edu.kit.mensameet.client.viewmodel.MensaMeetItemHandler;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;
import edu.kit.mensameet.client.viewmodel.UserItemHandler;

/**
 * Activity shown when the user has joined a group. Then it replaces SelectLinesActivity until the meeting is over or when the user leaves the group.
 */
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

        String groupToken = MensaMeetSession.getInstance().getUser().getGroupToken();

        if (groupToken == null || groupToken.equals("")) {

            Toast.makeText(GroupJoinedActivity.this, R.string.no_group_joined, Toast.LENGTH_SHORT).show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after 5 seconds
                    // restart activity to reload all data
                    finish();
                    gotoActivity(HomeActivity.class);
                }
            }, 2000);
        }

        Group chosenGroup =  RequestUtil.getGroup(MensaMeetSession.getInstance().getUser().getToken(), groupToken);

        // todo: remove after request error handling is done
        if (chosenGroup == null) {
            Toast.makeText(GroupJoinedActivity.this, R.string.no_group_joined, Toast.LENGTH_SHORT).show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after 5 seconds
                    // restart activity to reload all data
                    finish();
                    gotoActivity(HomeActivity.class);
                }
            }, 2000);
        }

        viewModel.setGroup(chosenGroup);

        groupItem = new GroupItem(this, MensaMeetItem.DisplayMode.BIG_NOTEDITABLE,
                chosenGroup);
        container.addView(groupItem.getView());

        groupItem.fillObjectData();

        groupItem.getHandler().getUiEventLiveData().observe(this, new Observer<Pair<MensaMeetItemHandler, StateInterface>>() {
            @Override
            public void onChanged(@Nullable Pair<MensaMeetItemHandler, StateInterface> it) {

                if (it.second == GroupItemHandler.State.GROUP_JOINED) {

                    gotoActivity(GroupJoinedActivity.class);

                } else if (it.second == GroupItemHandler.State.GROUP_DELETED) {

                    Toast.makeText(GroupJoinedActivity.this, R.string.group_deleted, Toast.LENGTH_SHORT).show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            // Actions to do after 5 seconds
                            // restart activity to reload all data
                            finish();
                            gotoActivity(HomeActivity.class);
                        }
                    }, 2000);



                }
            }
        });

        for (int j = 0; j < groupItem.getUserList().getItemCount(); j++) {

            UserItem userItem = (UserItem)groupItem.getUserList().getItem(j);

            if (userItem != null) {

                UserItemHandler userItemHandler = (UserItemHandler)userItem.getHandler();

                userItemHandler.getUiEventLiveData().observe(this, new Observer<Pair<MensaMeetItemHandler, StateInterface>>() {
                    @Override
                    public void onChanged(@Nullable Pair<MensaMeetItemHandler, StateInterface> it) {

                        if (it.second == UserItemHandler.State.SHOW_USER) {
                            gotoActivity(ShowUserActivity.class);
                        } else if (it.second == UserItemHandler.State.USER_DELETED) {

                            Toast.makeText(GroupJoinedActivity.this, R.string.user_deleted, Toast.LENGTH_SHORT).show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    // Actions to do after 5 seconds
                                    // restart activity to reload all data
                                    finish();
                                    startActivity(getIntent());
                                }
                            }, 2000);

                        }
                    }
                });

            }


        }

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
