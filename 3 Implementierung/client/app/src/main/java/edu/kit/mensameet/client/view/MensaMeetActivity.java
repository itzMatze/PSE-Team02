package edu.kit.mensameet.client.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

/**
 * This is the abstract base activity for MensaMeet, summarizing the basic functions as LiveData notification between
 * activity and view model shared by all activities.
 *
 */
public abstract class MensaMeetActivity extends AppCompatActivity {

    protected MensaMeetViewModel viewModel;
    protected LinearLayout navView;
    protected Button buttonHome;
    protected Button buttonBack;
    protected Button buttonNext;

    protected BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Center title
        centerTitle();

        // Always keep app in portrait position
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        buttonHome = (Button)findViewById(R.id.navigation_home);
        if (buttonHome != null) {
            buttonHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickHome();
                }
            });
        }

        // Click listener for back button
        buttonBack = (Button)findViewById(R.id.navigation_back);
        if (buttonBack != null) {
            buttonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBack();
                }
            });
        }

        // Click listener for next button
        buttonNext = (Button)findViewById(R.id.navigation_next);
        if (buttonNext != null) {
            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickNext();
                }
            });
        }

        observeLiveData();

    }

    protected void observeLiveData() {
        // Initializing livedata communication between view model and activity
        if (viewModel != null) {
            viewModel.getEventLiveData().observe(this, new Observer<Pair<String, StateInterface>>() {
                @Override
                public void onChanged(@Nullable Pair<String, StateInterface> it) {

                    processStateChange(it);

                }
            });
        }
    }

    /** Hook method for livedata processing
     *
     * @param it Message.
     */
    protected void processStateChange(Pair<String, StateInterface> it) {

    }

    /**
     * Hook method before redirecting to home activity.
      */

    protected void beforeGotoHome() {

    }

    /**
     * Click method for home button.
      */
    public void onClickHome() {
        beforeGotoHome();
        gotoHome();
    }

    /**
     * Click method for next button.
     */
    public void onClickNext() {};
    // TODO: Set stub methods to abstract once all activities are changed

    /**
     * Click method for back button.
     */
    public void onClickBack() {};

    // Hook method for Data reload.
    protected void reloadData() {};

    // Called when app is resumed.
    @Override
    protected void onResume() {

        // Data reload needed.
        reloadData();

        super.onResume();

    }

    // Method for redirecting home.
    protected void gotoHome() {
        // TODO: do not hardcode the name of the home activity instead, fetch homeActivity from MensaMeetSession

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    // General method for
    protected void gotoActivity(Class<?> activity) {
        if (activity != null) {
            Intent intent = new Intent(this, activity);
            startActivity(intent);
        }
    }

    // Centering for the title.
    private void centerTitle() {
        /*ArrayList<View> textViews = new ArrayList<>();

        getWindow().getDecorView().findViewsWithText(textViews, getTitle(), View.FIND_VIEWS_WITH_TEXT);

        if (textViews.size() > 0) {
            AppCompatTextView appCompatTextView = null;
            if(textViews.size() == 1) {
                appCompatTextView = (AppCompatTextView) textViews.get(0);
            } else {
                for(View v : textViews) {
                    if(v.getParent() instanceof Toolbar) {
                        appCompatTextView = (AppCompatTextView) v;
                        break;
                    }
                }
            }

            if(appCompatTextView != null) {
                ViewGroup.LayoutParams params = appCompatTextView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                appCompatTextView.setLayoutParams(params);
                appCompatTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        } */
    }
}