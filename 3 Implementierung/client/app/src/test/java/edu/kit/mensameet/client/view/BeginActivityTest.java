package edu.kit.mensameet.client.view;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowIntent;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.RequestUtil;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)

public class BeginActivityTest {

    @Test
    public void onCreate_userNotNull_ActivityFinishes() {

        MensaMeetSession.getInstance().setUser(new User());

        BeginActivity activity = Robolectric.setupActivity(BeginActivity.class);

        assertTrue(activity.isFinishing());

    }

    @Test
    public void clickLogin_startLoginActivity() {

        MensaMeetSession.getInstance().setUser(null);

        BeginActivity activity = Robolectric.setupActivity(BeginActivity.class);
        activity.findViewById(R.id.login).performClick();

        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(LoginActivity.class, shadowIntent.getIntentClass());

    }

    @Test
    public void clickRegister_startRegisterActivity() {

        MensaMeetSession.getInstance().setUser(null);

        BeginActivity activity = Robolectric.setupActivity(BeginActivity.class);
        activity.findViewById(R.id.register).performClick();

        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(RegisterActivity.class, shadowIntent.getIntentClass());

    }
}
