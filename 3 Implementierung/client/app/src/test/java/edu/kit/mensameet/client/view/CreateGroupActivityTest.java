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
public class CreateGroupActivityTest {

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
    public void start_userIncomplete_ActivityFinishes() {

        CreateGroupActivity activity;
        ActivityController<CreateGroupActivity> activityController;
        activityController = Robolectric.buildActivity(CreateGroupActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(new User());

        activityController.create();
        activityController.start();
        activityController.resume();

        assertTrue(activity.isFinishing());

        activityController.destroy();
    }

    @Test
    public void start_createdGroupExists_load() {

        CreateGroupActivity activity;
        ActivityController<CreateGroupActivity> activityController;
        activityController = Robolectric.buildActivity(CreateGroupActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);

        // Create group template
        Group template = new Group();
        template.setName("Template");
        MensaMeetSession.getInstance().setCreatedGroup(template);

        activityController.create();
        activityController.start();
        activityController.resume();
        activityController.visible();

        TextView groupName = activity.findViewById((int)R.string.field_name);

        assertEquals("Template", groupName.getText().toString());

        activityController.destroy();
    }

    @Test
    public void clickHome_saveAndStartHomeActivity() {

        CreateGroupActivity activity;
        ActivityController<CreateGroupActivity> activityController;
        activityController = Robolectric.buildActivity(CreateGroupActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);
        MensaMeetSession.getInstance().setCreatedGroup(null);

        activityController.create();
        activityController.start();
        activityController.resume();
        activityController.visible();

        activity.findViewById(R.id.navigation_home).performClick();

        // Saved.
        assertNotNull(MensaMeetSession.getInstance().getCreatedGroup());

        // Redirect to SetTimeActivity
        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(HomeActivity.class, shadowIntent.getIntentClass());

        activityController.destroy();
    }

    @Test
    public void clickBack_saveAndStartSelectGroupActivity() {

        CreateGroupActivity activity;
        ActivityController<CreateGroupActivity> activityController;
        activityController = Robolectric.buildActivity(CreateGroupActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);
        MensaMeetSession.getInstance().setCreatedGroup(null);

        activityController.create();
        activityController.start();
        activityController.resume();
        activityController.visible();

        activity.findViewById(R.id.navigation_back).performClick();

        // Saved.
        assertNotNull(MensaMeetSession.getInstance().getCreatedGroup());

        // Redirect to SetTimeActivity
        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(SelectGroupActivity.class, shadowIntent.getIntentClass());

        activityController.destroy();
    }
}
