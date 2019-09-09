package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.kit.mensameet.client.view.databinding.ActivitySelectGroupBinding;
import edu.kit.mensameet.client.viewmodel.GroupItemHandler;
import edu.kit.mensameet.client.viewmodel.SelectGroupViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;
import edu.kit.mensameet.client.viewmodel.UserItemHandler;
import edu.kit.mensameet.client.viewmodel.UserViewModel;

/**
 * Activity showing the groups fitting to the lines and time of the user and offering the possibility to join a group.
 */
public class SelectGroupActivity extends MensaMeetActivity {

    protected SelectGroupViewModel viewModel;
    private ActivitySelectGroupBinding binding;

    private GroupList groupList;
    private RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(SelectGroupViewModel.class);
        super.initializeViewModel(viewModel);
/*
    }

    @VisibleForTesting
    protected void setViewModel(SelectGroupViewModel viewModel) {
        this.viewModel = viewModel;
        super.initializeViewModel(viewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
*/
        if (!checkAccess()) {
            return;
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_group);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        observeLiveData();
        initializeButtons();

        if (buttonNext != null) {
            if (buttonHome != null) {
                buttonHome.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

            }
            // no next button
            buttonNext.setVisibility(View.GONE);
        }

        container = findViewById(R.id.container);

        // Floating action button for adding a group
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(CreateGroupActivity.class);
            }
        });

        /*

        // Mock data

        List<Group> dataList = new ArrayList<Group>();

        Group g1 = new Group();
        g1.setName("Mensaphilosophen");
        g1.setMotto("Du bist, was du isst!");
        g1.setMeetingDate(MensaMeetTime.stringToTime("12:00"));
        g1.setLine("LINE_ONE");
        g1.setMaxMembers(10);
        List<User> userList1 = new ArrayList<User>();
        User u1 = new User();
        u1.setName("Tim");
        u1.setMotto("isst gerne Fisch");
        u1.setProfilePictureId(2);
        userList1.add(u1);
        User u2 = new User();
        u2.setName("Alice");
        u2.setMotto("seeking for Bob");
        userList1.add(u2);
        g1.setUsers(userList1);
        dataList.add(g1);

        Group g2 = new Group();
        g2.setName("Neu am KIT");
        g2.setMotto("Hier könnt ihr neue Leute kennenlernen!");
        g2.setMeetingDate(MensaMeetTime.stringToTime("12:30"));
        g2.setLine("LINE_FOUR_FIVE");
        g2.setMaxMembers(15);
        List<User> userList2 = new ArrayList<User>();
        User u21 = new User();
        u21.setName("Petra");
        u21.setMotto("(^u^)");
        u21.setProfilePictureId(4);
        userList2.add(u21);
        g2.setUsers(userList2);
        dataList.add(g2);

        Group g3 = new Group();
        g3.setName("Dating");
        g3.setMotto("Liebe geht durch den Magen.");
        g3.setMeetingDate(MensaMeetTime.stringToTime("11:30"));
        g3.setLine("LINE_TWO");
        g3.setMaxMembers(8);
        List<User> userList3 = new ArrayList<User>();
        User u31 = new User();
        u31.setName("Theo");
        u31.setMotto("I <3 Linie 2");
        u31.setProfilePictureId(1);
        userList3.add(u31);
        User u32 = new User();
        u32.setName("Carsten");
        u32.setMotto("Wo sind hier die ganzen Frauen??");
        u32.setProfilePictureId(1);
        userList3.add(u32);
        g3.setUsers(userList3);
        dataList.add(g3);

        Group g4 = new Group();
        g4.setName("Spätes Frühstück");
        g4.setMotto("Guten Morgen");
        g4.setMeetingDate(MensaMeetTime.stringToTime("12:00"));
        g4.setLine("CAFETARIA");
        g4.setMaxMembers(10);
        List<User> userList4 = new ArrayList<User>();
        User u41 = new User();
        u41.setName("Lena");
        u41.setMotto("Schlaflose Nächte...");
        u41.setProfilePictureId(0);
        userList4.add(u41);
        g4.setUsers(userList4);
        dataList.add(g4);

        //MensaMeetSession.getInstance().setReceivedGroups(dataList);

        */

        if (viewModel.loadGroups() == false) {
            return;
        }

        groupList = new GroupList(
                this,
                viewModel.getReceivedGroups(),
                MensaMeetList.DisplayMode.SINGLE_SELECT,
                true,
                true);

        container.addView(groupList.getView());

        GroupItem item;

        // The live data of every group item handler and user item handler inside the groups must be observed.

        for (int i = 0; i < groupList.getItemCount(); i++) {

            item = (GroupItem)groupList.getItem(i);

            if (item != null) {
                GroupItemHandler handler = item.getHandler();

                handler.getEventLiveData().observe(this, new Observer<Pair<String, StateInterface>>() {
                    @Override
                    public void onChanged(@Nullable Pair<String, StateInterface> it) {

                        if (it.second == GroupItemHandler.State.GROUP_JOINED) {

                            finish();
                            gotoActivity(GroupJoinedActivity.class);

                        } else if (it.second == GroupItemHandler.State.GROUP_JOIN_FAILED) {

                            showMessage(SelectGroupActivity.this, R.string.group_join_failed, it);

                        } else if (it.second == GroupItemHandler.State.GROUP_DELETED) {

                            showMessage(SelectGroupActivity.this, R.string.group_deleted, it);

                            finish();
                            startActivity(getIntent());

                        } else if (it.second == GroupItemHandler.State.GROUP_DELETION_FAILED) {

                            showMessage(SelectGroupActivity.this, R.string.group_deletion_failed, it);

                        } else if (it.second == GroupItemHandler.State.RELOADING_USER_FAILED) {

                            showMessage(SelectGroupActivity.this, R.string.reloading_user_failed, it);

                            // Logout.
                            viewModel.invalidateSession();
                            finish();
                            gotoActivity(HomeActivity.class);
                        }
                    }
                });

            }

            for (int j = 0; j < item.getUserList().getItemCount(); j++) {

                UserItem userItem = (UserItem)item.getUserList().getItem(j);

                if (userItem != null) {

                    UserItemHandler userItemHandler = (UserItemHandler)userItem.getHandler();

                    userItemHandler.getEventLiveData().observe(this, new Observer<Pair<String, StateInterface>>() {
                        @Override
                        public void onChanged(@Nullable Pair<String, StateInterface> it) {

                            if (it.second == UserItemHandler.State.SHOW_USER) {

                                gotoActivity(ShowUserActivity.class);

                            } else if (it.second == UserItemHandler.State.USER_DELETED) {

                                showMessage(SelectGroupActivity.this, R.string.user_deleted, it);

                                finish();
                                startActivity(getIntent());

                            } else if (it.second == UserItemHandler.State.USER_DELETED_SERVER_NOT_FIREBASE) {

                                showMessage(SelectGroupActivity.this, R.string.user_deleted_server_not_firebase, it);

                                finish();
                                startActivity(getIntent());

                            } else if (it.second == UserItemHandler.State.USER_DELETION_FAILED) {

                                showMessage(SelectGroupActivity.this, R.string.user_deletion_failed, it);

                            }
                        }
                    });

                }


            }

        }

        Toast.makeText(this, R.string.create_button_description, Toast.LENGTH_LONG).show();

    }

    protected void observeGroupAndUserLiveData() {

    }

    /**
 * Communcation with the view model
 */
    @Override
    protected void processStateChange(Pair<String, StateInterface> it) {
        if (it.second == SelectGroupViewModel.State.LOADING_GROUPS_FAILED) {

            showMessage(this, R.string.loading_groups_failed, it);

        } else if (it.second == SelectGroupViewModel.State.NO_TIME_CHOSEN) {

            showMessage(this, R.string.no_time_chosen, it);

        } else if (it.second == SelectGroupViewModel.State.NO_LINES_CHOSEN) {

            showMessage(this, R.string.no_lines_chosen, it);

        }
    }

    @Override
    protected Boolean checkAccess() {
        // Illegal state to show activity, go back.
        if (viewModel.currentUserDataIncomplete() || viewModel.getUser().getGroupToken() != null) {
            finish();
            return false;
        }
        return true;
    }

    @Override
    public void onClickBack() {
        finish();
        gotoActivity(SetTimeActivity.class);
    }

    // Communication live data states
    public enum State implements StateInterface { DEFAULT, NEXT }

}
