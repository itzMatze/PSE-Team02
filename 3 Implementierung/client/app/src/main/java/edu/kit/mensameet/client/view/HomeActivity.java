package edu.kit.mensameet.client.view;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;

public class HomeActivity extends MensaMeetActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onGoEatClick(View v) {
        //Intent intent = new Intent(this, LineActivity.class);
        //startActivity(intent);
    }

    public void onProfileClick(View v) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    public void onLogoutClick(View v) {
        Intent intent = new Intent(this, BeginActivity.class);
        startActivity(intent);
    }

    public void onEditGroupsClick(View v) {
        Intent intent = new Intent(this, AdministerGroupsActivity.class);
        startActivity(intent);
    }

    public void onEditUsersClick(View v) {
        Intent intent = new Intent(this, AdministerUsersActivity.class);
        startActivity(intent);
    }
}
