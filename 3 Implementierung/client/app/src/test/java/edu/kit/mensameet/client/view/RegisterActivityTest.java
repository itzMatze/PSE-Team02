package edu.kit.mensameet.client.view;

import android.content.Intent;
import android.util.Pair;
import android.widget.EditText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowToast;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.viewmodel.RegisterViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)

public class RegisterActivityTest {

    @Test
    public void onCreate_userNotNull_ActivityFinishes () {

        MensaMeetSession.getInstance().setUser(new User());

        RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);

        assertTrue(activity.isFinishing());

    }

    @Test
    public void clickBack_startBeginActivity () {

        MensaMeetSession.getInstance().setUser(null);
        RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);

        activity.findViewById(R.id.navigation_back).performClick();

        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(BeginActivity.class, shadowIntent.getIntentClass());

    }

    @Test
    public void clickLogin_passwordsNotEqual_errorMessage() {

        MensaMeetSession.getInstance().setUser(null);
        RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);
        ((EditText)activity.findViewById(R.id.email)).setText("user@user.de");
        ((EditText)activity.findViewById(R.id.password)).setText("password");
        ((EditText)activity.findViewById(R.id.password)).setText("pass");

        activity.findViewById(R.id.login).performClick();

        assertEquals(activity.getResources().getString(R.string.passwords_not_equal_message),
                ShadowToast.getTextOfLatestToast());

    }

    @Test
    public void loginSuccess_startUserActivity() {

        MensaMeetSession.getInstance().setUser(null);
        RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);

        Pair<String, StateInterface> it = new Pair<String, StateInterface>(null, RegisterViewModel.State.CREATE_ACCOUNT_SUCCESS);
        activity.processStateChange(it);

        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(UserActivity.class, shadowIntent.getIntentClass());
    }

    @Test
    public void loginSuccess_initializationFails_startBeginActivity() {

        MensaMeetSession.getInstance().setUser(null);
        RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);

        Pair<String, StateInterface> it = new Pair<String, StateInterface>(null, RegisterViewModel.State.ACCOUNT_CREATED_BUT_INITIALIZATION_FAILED);
        activity.processStateChange(it);

        assertEquals(null, MensaMeetSession.getInstance().getUser());

        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(BeginActivity.class, shadowIntent.getIntentClass());
    }

}
