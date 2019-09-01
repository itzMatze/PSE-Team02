package edu.kit.mensameet.client.view;

import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowIntent;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.Status;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.viewmodel.StateInterface;
import edu.kit.mensameet.client.viewmodel.UserViewModel;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)

@Config(sdk = Build.VERSION_CODES.O_MR1)
public class UserActivityTest {

    private UserViewModel spyViewModel;
    private UserActivity activity;
    private ActivityController<UserActivity> activityController;

    @Before
    public void setUp() {
        spyViewModel = spy(new UserViewModel());
        doReturn(true).when(spyViewModel).loadUser();
        doNothing().when(spyViewModel).saveUserAndNext();

        activityController = Robolectric.buildActivity(UserActivity.class);
        activity = activityController.get();
    }

    @Test
    public void onCreate_userNull_ActivityFinishes() {

        MensaMeetSession.getInstance().setUser(null);

        activityController.create();
        // Inject spyViewModel.
        activity.setViewModel(spyViewModel);
        activityController.start();
        activityController.resume();

        assertTrue(activity.isFinishing());

    }

    @Test
    public void userDataIncomplete_noDiscardButton() {

        MensaMeetSession.getInstance().setUser(new User());

        activityController.create();
        // Inject spyViewModel.
        activity.setViewModel(spyViewModel);
        activityController.start();
        activityController.resume();

        assertEquals(View.GONE, activity.buttonBack.getVisibility());

    }

    @Test
    public void clickDiscard_startHomeActivityAndNoDataSaved() {

        // Complete user data.
        User user = new User();
        user.setName("Old username");
        user.setStatus(Status.APPRENTICE);
        MensaMeetSession.getInstance().setUser(user);

        activityController.create();
        // Inject spyViewModel.
        activity.setViewModel(spyViewModel);
        activityController.start();
        activityController.resume();

        TextView nameField = activity.findViewById((int)R.string.field_name);
        nameField.setText("New username");

        activity.buttonBack.performClick();

        // Changes not saved.
        assertEquals("Old username", MensaMeetSession.getInstance().getUser().getName());

        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(HomeActivity.class, shadowIntent.getIntentClass());

    }

    @Test
    public void clickHome_startHomeActivityAndNoDataSaved() {

        // Complete user data.
        User user = new User();
        user.setName("Old username");
        user.setStatus(Status.APPRENTICE);
        MensaMeetSession.getInstance().setUser(user);

        activityController.create();
        // Inject spyViewModel.
        activity.setViewModel(spyViewModel);
        activityController.start();
        activityController.resume();

        TextView nameField = activity.findViewById((int)R.string.field_name);
        nameField.setText("New username");

        activity.buttonHome.performClick();

        // Changes not saved.
        assertEquals("Old username", MensaMeetSession.getInstance().getUser().getName());

        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(HomeActivity.class, shadowIntent.getIntentClass());

    }

    @Test
    public void clickSave_callSaveUserAndNext() {

        MensaMeetSession.getInstance().setUser(new User());

        activityController.create();
        // Inject spyViewModel.
        activity.setViewModel(spyViewModel);
        activityController.start();
        activityController.resume();

        activity.buttonNext.performClick();

        verify(spyViewModel).saveUserAndNext();

    }

    @Test
    public void updateUserSuccess_startHomeActivity() {

        MensaMeetSession.getInstance().setUser(new User());

        activityController.create();
        // Inject spyViewModel.
        activity.setViewModel(spyViewModel);
        activityController.start();
        activityController.resume();

        Pair<String, StateInterface> it = new Pair<String, StateInterface>(null, UserViewModel.State.USER_SAVED_NEXT);
        spyViewModel.getEventLiveData().setValue(it);

        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(HomeActivity.class, shadowIntent.getIntentClass());

    }

    @Test
    public void loadUserFailed_logout() {

        MensaMeetSession.getInstance().setUser(new User());

        activityController.create();
        // Inject spyViewModel.
        activity.setViewModel(spyViewModel);
        activityController.start();
        activityController.resume();

        Pair<String, StateInterface> it = new Pair<String, StateInterface>(null, UserViewModel.State.RELOADING_USER_FAILED);
        spyViewModel.getEventLiveData().setValue(it);

        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(BeginActivity.class, shadowIntent.getIntentClass());

    }

    @After
    public void cleanUp() {
        activityController.destroy();
    }
}
