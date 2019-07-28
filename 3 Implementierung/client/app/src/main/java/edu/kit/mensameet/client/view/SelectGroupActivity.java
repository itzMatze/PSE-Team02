package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.SingleLiveEvent;
import edu.kit.mensameet.client.view.databinding.ActivitySelectGroupBinding;
import edu.kit.mensameet.client.viewmodel.GroupItemHandler;
import edu.kit.mensameet.client.viewmodel.MensaMeetItemHandler;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.SelectGroupViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;
import edu.kit.mensameet.client.viewmodel.UserItemHandler;

public class SelectGroupActivity extends MensaMeetActivity {

    protected SingleLiveEvent<StateInterface> activityEventLiveData;

    public SingleLiveEvent<StateInterface> getActivityEventLiveData() {
        if (activityEventLiveData == null) {
            activityEventLiveData = new SingleLiveEvent<>();
            activityEventLiveData.setValue(State.DEFAULT);
        }
        return activityEventLiveData;
    }

    public enum State implements StateInterface {DEFAULT, NEXT}

    private SelectGroupViewModel viewModel;
    private ActivitySelectGroupBinding binding;

    private GroupList groupList;

    private SelectGroupActivity me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getActivityEventLiveData().observe(this, new Observer<StateInterface>() {
            @Override
            public void onChanged(StateInterface stateInterface) {
                if (stateInterface == State.NEXT) {
                    gotoActivity(GroupJoinedActivity.class);
                }
            }
        });

        viewModel = ViewModelProviders.of(this).get(SelectGroupViewModel.class);
        super.viewModel = viewModel;
        me = this;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_group);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        RelativeLayout container = findViewById(R.id.container);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(CreateGroupActivity.class);
            }
        });

        List<Group> dataList = new ArrayList<Group>();

        Group g1 = new Group();
        g1.setName("Gruppe1");
        g1.setMotto("Motto1");
        g1.setMeetingDate(MensaMeetTime.stringToTime("12:00"));
        g1.setLine("Linie 1");

        List<User> userList1 = new ArrayList<User>();
        User u1 = new User();
        u1.setName("User1");
        u1.setMotto("Motto1");
        u1.setProfilePictureId(2);
        userList1.add(u1);
        User u2 = new User();
        u2.setName("User2");
        u2.setMotto("Motto2");
        userList1.add(u2);
        g1.setUsers(userList1);

        dataList.add(g1);
        Group g2 = new Group();
        g2.setName("Gruppe2");
        g2.setMotto("Motto2");
        g2.setMeetingDate(MensaMeetTime.stringToTime("12:00"));
        g2.setLine("Linie 1");
        dataList.add(g2);

        Group g3 = new Group();
        g3.setName("Gruppe3");
        g3.setMotto("Motto3");
        g3.setMeetingDate(MensaMeetTime.stringToTime("12:00"));
        g3.setLine("Linie 1");
        dataList.add(g3);

        Group g4 = new Group();
        g4.setName("Gruppe3");
        g4.setMotto("Motto3");
        g4.setMeetingDate(MensaMeetTime.stringToTime("12:00"));
        g4.setLine("Linie 1");
        dataList.add(g4);

        groupList = new GroupList(
                this,
                dataList,
                MensaMeetList.DisplayMode.SINGLE_SELECT,
                true);

        container.addView(groupList.getView());

        GroupItem item;

        for (int i = 0; i < groupList.getItemCount(); i++) {

            item = (GroupItem) groupList.getItem(i);

            if (item != null) {
                GroupItemHandler handler = item.getHandler();

                handler.getUiEventLiveData().observe(this, new Observer<Pair<MensaMeetItemHandler, StateInterface>>() {
                    @Override
                    public void onChanged(@Nullable Pair<MensaMeetItemHandler, StateInterface> it) {

                        //Toast.makeText(me, "changed1", Toast.LENGTH_SHORT).show();

                        if (it.second == GroupItemHandler.State.GROUP_JOINED) {
                            Toast.makeText(me, "changed2", Toast.LENGTH_SHORT).show();

                            gotoActivity(GroupJoinedActivity.class);
                        } else if (it.second == GroupItemHandler.State.GROUP_DELETED) {
                            //new group by preferences request to refresh session data and restart activity

                            //getGroupsByPreferences(MensaMeetSession.getInstance().getChosenTime(), MensaMeetSession.getInstance().getChosenLines());
                            finish();
                            startActivity(getIntent());
                        }
                    }
                });
            }

            for (int j = 0; j < item.getUserList().getItemCount(); j++) {

                UserItem userItem = (UserItem) item.getUserList().getItem(j);

                if (userItem != null) {

                    UserItemHandler userItemHandler = (UserItemHandler) userItem.getHandler();

                    userItemHandler.getUiEventLiveData().observe(this, new Observer<Pair<MensaMeetItemHandler, StateInterface>>() {
                        @Override
                        public void onChanged(@Nullable Pair<MensaMeetItemHandler, StateInterface> it) {

                            if (it.second == UserItemHandler.State.SHOW_USER) {
                                gotoActivity(ShowUserActivity.class);
                            }
                        }
                    });
                }
            }
        }

        super.onCreate(savedInstanceState);

        if (buttonNext != null) {
            if (buttonHome != null) {
                buttonHome.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            }
            buttonNext.setVisibility(View.GONE);
        }

        Toast.makeText(me, R.string.create_button_description, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void reloadData() {

    }

    @Override
    protected void processStateChange(Pair<MensaMeetViewModel, StateInterface> it) {
        if (it.second == SelectGroupViewModel.State.GROUP_JOINED) {
            //gotoActivity(GroupSelectedActivity.class);
        } else if (it.second == SelectGroupViewModel.State.BACK) {
            gotoActivity(SetTimeActivity2.class);
        }
    }

    @Override
    public void onClickBack() {
        gotoActivity(SetTimeActivity2.class);
    }
}
