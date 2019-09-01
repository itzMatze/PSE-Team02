package edu.kit.mensameet.client.view;

import android.content.Intent;
import android.os.Build;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowIntent;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaData;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.Status;
import edu.kit.mensameet.client.model.User;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class ShowUserActivityTest {

    private User completeUser;

    @Before
    public void setUp() {

        // Setup complete user.
        completeUser = new User();
        completeUser.setName("Old username");
        completeUser.setStatus(Status.APPRENTICE);

        // Create mensa data
        Line linie1 = new Line();
        linie1.setMealLine("LINE_ONE");
        Line linie2 = new Line();
        linie2.setMealLine("LINE_TWO");

        List<Line> mensaLines = new ArrayList<>();
        mensaLines.add(linie1);
        mensaLines.add(linie2);

        MensaData mensaData = new MensaData();
        mensaData.setLines(mensaLines.toArray(new Line[0]));

        MensaMeetSession.getInstance().setMensaData(mensaData);
    }

    @Test
    public void start_noUserToShow_ActivityFinishes() {

        ShowUserActivity activity;
        ActivityController<ShowUserActivity> activityController;
        activityController = Robolectric.buildActivity(ShowUserActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);
        MensaMeetSession.getInstance().setUserToShow(null);

        activityController.create();
        activityController.start();
        activityController.resume();

        assertTrue(activity.isFinishing());

        activityController.destroy();
    }

    @Test
    public void start_userToShowLoaded() {

        ShowUserActivity activity;
        ActivityController<ShowUserActivity> activityController;
        activityController = Robolectric.buildActivity(ShowUserActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);

        // Create user to show
        User userToShow = new User();
        userToShow.setName("UserToShow");
        MensaMeetSession.getInstance().setUserToShow(userToShow);

        activityController.create();
        activityController.start();
        activityController.resume();
        activityController.visible();

        TextView userName = activity.findViewById((int) R.string.field_name);

        assertEquals("UserToShow", userName.getText().toString());

        activityController.destroy();
    }

    @Test
    public void clickHome_startHomeActivity() {

        ShowUserActivity activity;
        ActivityController<ShowUserActivity> activityController;
        activityController = Robolectric.buildActivity(ShowUserActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);
        MensaMeetSession.getInstance().setUserToShow(new User());

        activityController.create();
        activityController.start();
        activityController.resume();
        activityController.visible();

        activity.findViewById(R.id.navigation_home).performClick();

        // Redirect to SetTimeActivity
        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(HomeActivity.class, shadowIntent.getIntentClass());

        activityController.destroy();
    }
}
