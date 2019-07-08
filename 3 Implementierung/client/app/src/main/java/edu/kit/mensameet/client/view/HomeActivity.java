package edu.kit.mensameet.client.view;

import android.view.View;
import android.os.Bundle;

public class HomeActivity extends MensaMeetActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onGoEatClick(View v) {
        //gotoActivity(LineActivity.class);
    }

    public void onProfileClick(View v) {
        gotoActivity(UserActivity.class);
    }

    public void onLogoutClick(View v) {
        gotoActivity(BeginActivity.class);
    }

    public void onEditGroupsClick(View v) {
        gotoActivity(AdministerGroupsActivity.class);
    }

    public void onEditUsersClick(View v) {
        gotoActivity(AdministerUsersActivity.class);
    }
}
