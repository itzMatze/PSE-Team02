package edu.kit.mensameet.client.view;

import android.content.Intent;
import android.util.Pair;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowToast;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.Status;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.viewmodel.LoginViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)

public class LoginActivityTest {

    @Test
    public void onCreate_userNotNull_ActivityFinishes () {

        MensaMeetSession.getInstance().setUser(new User());

        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);

        assertTrue(activity.isFinishing());

    }

    @Test
    public void clickBack_startBeginActivity () {

        MensaMeetSession.getInstance().setUser(null);
        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);

        activity.findViewById(R.id.navigation_back).performClick();

        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(BeginActivity.class, shadowIntent.getIntentClass());

    }

    @Test
    public void clickLogin_oneEditTextEmpty_errorMessage() {

        MensaMeetSession.getInstance().setUser(null);
        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);
        // TextEdits both stay empty.

        activity.findViewById(R.id.login).performClick();

        assertEquals(activity.getResources().getString(R.string.credentials_incomplete),
                ShadowToast.getTextOfLatestToast());

    }

    @Test
    public void loginSuccess_initializationFails_notLoggedIn() {

        MensaMeetSession.getInstance().setUser(null);
        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);

        Pair<String, StateInterface> it = new Pair<String, StateInterface>(null, LoginViewModel.State.INITIALIZATION_FAILED);
        activity.processStateChange(it);

        assertEquals(null, MensaMeetSession.getInstance().getUser());
    }

    @Test
    public void loginSuccess_userDataComplete_startHomeActivity() {

        MensaMeetSession.getInstance().setUser(null);
        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);

        User userFromServer = new User();
        // Do not complete necessary user data.
        userFromServer.setName("Name");
        userFromServer.setStatus(Status.APPRENTICE);
        MensaMeetSession.getInstance().setUser(userFromServer);

        Pair<String, StateInterface> it = new Pair<String, StateInterface>(null, LoginViewModel.State.LOG_IN_SUCCESS);
        activity.processStateChange(it);

        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(HomeActivity.class, shadowIntent.getIntentClass());
    }

    @Test
    public void loginSuccess_userDataIncomplete_startUserActivity() {

        MensaMeetSession.getInstance().setUser(null);
        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);

        User userFromServer = new User();
        // Do not complete necessary user data.
        userFromServer.setName("");
        userFromServer.setStatus(Status.APPRENTICE);
        MensaMeetSession.getInstance().setUser(userFromServer);

        Pair<String, StateInterface> it = new Pair<String, StateInterface>(null, LoginViewModel.State.LOG_IN_SUCCESS);
        activity.processStateChange(it);

        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(UserActivity.class, shadowIntent.getIntentClass());
    }

}
