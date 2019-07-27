package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
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
import edu.kit.mensameet.client.view.databinding.ActivitySelectGroupBinding;
import edu.kit.mensameet.client.viewmodel.GroupItemHandler;
import edu.kit.mensameet.client.viewmodel.MensaMeetItemHandler;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.SelectGroupViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

public class SelectGroupActivity extends MensaMeetActivity {

    private SelectGroupViewModel viewModel;
    private ActivitySelectGroupBinding binding;

    private GroupList groupList;

    private SelectGroupActivity me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
        g1.setMeetingDate(MensaMeetTime.stringToDate("12:00"));
        g1.setLine("Linie 1");

        List<User> userList1 = new ArrayList<User>();
        User u1 = new User();
        u1.setUsername("User1");
        u1.setMotto("Motto1");
        userList1.add(u1);
        User u2 = new User();
        u2.setUsername("User2");
        u2.setMotto("Motto2");
        userList1.add(u2);
        g1.setUsers(userList1);

        dataList.add(g1);
        Group g2 = new Group();
        g2.setName("Gruppe2");
        g2.setMotto("Motto2");
        g2.setMeetingDate(MensaMeetTime.stringToDate("12:00"));
        g2.setLine("Linie 1");
        dataList.add(g2);

        Group g3 = new Group();
        g3.setName("Gruppe3");
        g3.setMotto("Motto3");
        g3.setMeetingDate(MensaMeetTime.stringToDate("12:00"));
        g3.setLine("Linie 1");
        dataList.add(g3);

        Group g4 = new Group();
        g4.setName("Gruppe3");
        g4.setMotto("Motto3");
        g4.setMeetingDate(MensaMeetTime.stringToDate("12:00"));
        g4.setLine("Linie 1");
        dataList.add(g4);

        groupList = new GroupList(
                this,
                dataList,
                MensaMeetList.DisplayMode.SINGLE_SELECT,
                true);

        container.addView(groupList.getView());

        for (int i = 0; i < groupList.getItemCount(); i++) {

            GroupItem item = (GroupItem)groupList.getItem(i);

            if (item != null) {
                GroupItemHandler handler = item.getHandler();

                handler.getUiEventLiveData().observe(this, new Observer<Pair<MensaMeetItemHandler, StateInterface>>() {
                    @Override
                    public void onChanged(@Nullable Pair<MensaMeetItemHandler, StateInterface> it) {

                        //Toast.makeText(me, "changed1", Toast.LENGTH_SHORT).show();

                        if (it.second == GroupItemHandler.State.GROUP_JOINED) {
                            Toast.makeText(me, "changed2", Toast.LENGTH_SHORT).show();
                            //gotoActivity(GroupJoinedActivity.class);
                        } else if (it.second == SelectGroupViewModel.State.BACK) {
                            gotoActivity(SetTimeActivity2.class);
                        }
                    }
                });
            }
        }

        super.onCreate(savedInstanceState);
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
    protected void onClickNext() {
        // nothing
    }

    @Override
    protected void onClickBack() {
        gotoActivity(SetTimeActivity2.class);
    }

}
