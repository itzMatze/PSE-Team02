package edu.kit.mensameet.client.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.os.Bundle;

import edu.kit.mensameet.client.viewmodel.BeginViewModel;

public class BeginActivity extends MensaMeetActivity {
    BeginViewModel beginViewModel = new BeginViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //wenn auf dem Gerät bereits ein Benutzer angemeldet ist, wechsle direkt zur HomeActivity
        if (beginViewModel.isLoggedIn(this)) {
            gotoHome();
        }
        setContentView(R.layout.activity_begin);
    }

    public void onLoginClick(View v) {
        gotoActivity(LoginActivity.class);
    }

    public void onRegisterClick(View v) {
        gotoActivity(RegisterActivity.class);
    }

    public void onResetClick(View v) {
        //Leert den lokalen Speicher, nur für Testzwecke
        SharedPreferences sharedPrefs = getSharedPreferences("MensaMeetApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();
    }
}
