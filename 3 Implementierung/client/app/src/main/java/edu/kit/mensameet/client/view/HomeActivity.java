package edu.kit.mensameet.client.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import edu.kit.mensameet.client.viewmodel.HomeViewModel;

public class HomeActivity extends MensaMeetActivity {
    private HomeViewModel homeViewModel = new HomeViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences sharedPrefs = getSharedPreferences("MensaMeetApp", Context.MODE_PRIVATE);
    }

    public void onGoEatClick(View v) {
        //gotoActivity(LineActivity.class);
    }

    public void onProfileClick(View v) {
        gotoActivity(UserActivity.class);
    }

    public void onLogoutClick(View v) {
        homeViewModel.logout(this);
        gotoActivity(BeginActivity.class);
    }

    public void onEditGroupsClick(View v) {
        gotoActivity(AdministerGroupsActivity.class);
    }

    public void onEditUsersClick(View v) {
        gotoActivity(AdministerUsersActivity.class);
    }
}
