package edu.kit.mensameet.client.view;

import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class MensaMeetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void gotoHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    protected void gotoActivity(Class<?> activity) {
        if (activity != null) {
            Intent intent = new Intent(this, activity);
            startActivity(intent);
        }
    }

    protected void deactivateNavigationButton(View v) {
        v.setAlpha(0.2f);
        v.setClickable(false);
    }
}