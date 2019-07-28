package edu.kit.mensameet.client.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.SelectLinesViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

public abstract class MensaMeetActivity extends AppCompatActivity {

    protected MensaMeetViewModel viewModel;
    protected LinearLayout navView;
    protected Button buttonHome;
    protected Button buttonBack;
    protected Button buttonNext;

    protected BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //reloadData();

        super.onCreate(savedInstanceState);

        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        /*mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        onClickHome();
                        return true;
                    case R.id.navigation_back:
                        onClickBack();
                        return true;
                    case R.id.navigation_next:
                        onClickNext();
                        return true;
                }
                return false;
            }
        };*/

        buttonHome = (Button)findViewById(R.id.navigation_home);
        if (buttonHome != null) {
            buttonHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickHome();
                }
            });
        }

        buttonBack = (Button)findViewById(R.id.navigation_back);
        if (buttonBack != null) {
            buttonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBack();
                }
            });
        }
        buttonNext = (Button)findViewById(R.id.navigation_next);
        if (buttonNext != null) {
            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickNext();
                }
            });
        }

        /* if (navView != null) {
            navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            //entfernt die grüne Färbung in der BottomNavigation Leiste
            //navView.setItemIconTintList(null);
        } */

        if (viewModel != null) {
            viewModel.getUiEventLiveData2().observe(this, new Observer<Pair<MensaMeetViewModel, StateInterface>>() {
                @Override
                public void onChanged(@Nullable Pair<MensaMeetViewModel, StateInterface> it) {

                    processStateChange(it);

                }
            });
        }



    }

    protected void processStateChange(Pair<MensaMeetViewModel, StateInterface> it) {

    }

    protected void beforeGotoHome() {

    }

    public void onClickHome() {
        beforeGotoHome();
        gotoHome();
    }

    public void onClickNext() {};
    // TODO: Set stub methods to abstract once all activities are changed

    public void onClickBack() {};

    protected void reloadData() {};

    @Override
    protected void onResume() {
        //Toast.makeText(this, "onresume", Toast.LENGTH_SHORT).show();

        reloadData();

        super.onResume();

    }

    protected void gotoHome() {
        // TODO: do not hardcode the name of the home activity instead, fetch homeActivity from MensaMeetSession

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