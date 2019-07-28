package edu.kit.mensameet.client.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.viewmodel.HomeViewModel;

public class HomeActivity extends MensaMeetActivity {
    private HomeViewModel homeViewModel = new HomeViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initialize MensaMeetSession, TODO: normally at login or registration
        User me = new User();
        me.setAdmin(true);
        MensaMeetSession.getInstance().setUser(me);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences sharedPrefs = getSharedPreferences("MensaMeetApp", Context.MODE_PRIVATE);
    }

    public void onGoEatClick(View v) {

        if (MensaMeetSession.getInstance().getChosenGroup() == null) {
            gotoActivity(SelectLinesActivity.class);
        } else {
            gotoActivity(GroupJoinedActivity.class);
        }
    }

    public void onProfileClick(View v) {
        gotoActivity(UserActivity.class);
    }

    public void onLogoutClick(View v) {
        homeViewModel.logout(this);
        gotoActivity(BeginActivity.class);
    }

    /*

    public void onEditGroupsClick(View v) {
        gotoActivity(AdministerGroupsActivity.class);
    }

    public void onEditUsersClick(View v) {
        gotoActivity(AdministerUsersActivity.class);
    }*/
}
