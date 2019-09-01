package edu.kit.mensameet.client.view;

import android.content.Intent;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowIntent;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.viewmodel.HomeViewModel;
import edu.kit.mensameet.client.viewmodel.UserViewModel;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)

@Config(sdk = Build.VERSION_CODES.O_MR1)
public class HomeActivityTest {

    private HomeViewModel spyViewModel;
    private HomeActivity activity;
    private ActivityController<HomeActivity> activityController;

    @Before
    public void setUp() {

        activityController = Robolectric.buildActivity(HomeActivity.class);
        activity = activityController.get();
    }

    @Test
    public void start_userNull_ActivityFinishes() {

        MensaMeetSession.getInstance().setUser(null);

        activityController.create();
        activityController.start();
        activityController.resume();

        assertTrue(activity.isFinishing());

    }

    @Test
    public void clickYourProfile_startUserActivity() {

        MensaMeetSession.getInstance().setUser(new User());

        activityController.create();
        activityController.start();
        activityController.resume();

        activity.findViewById(R.id.profile).performClick();

        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(UserActivity.class, shadowIntent.getIntentClass());

    }

    @Test
    public void clickLogout_logout() {

        MensaMeetSession.getInstance().setUser(new User());

        activityController.create();
        activityController.start();
        activityController.resume();

        activity.findViewById(R.id.logout).performClick();

        assertNull(MensaMeetSession.getInstance().getUser());

        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(BeginActivity.class, shadowIntent.getIntentClass());

    }
}
