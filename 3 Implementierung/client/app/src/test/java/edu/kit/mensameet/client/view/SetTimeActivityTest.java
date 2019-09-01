package edu.kit.mensameet.client.view;

import android.app.TimePickerDialog;
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
import org.robolectric.shadows.ShadowTimePickerDialog;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaData;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.model.Status;
import edu.kit.mensameet.client.model.User;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class SetTimeActivityTest {

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

        SetTimeActivity activity;
        ActivityController<SetTimeActivity> activityController;
        activityController = Robolectric.buildActivity(SetTimeActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(new User());

        activityController.create();
        activityController.start();
        activityController.resume();

        assertTrue(activity.isFinishing());

        activityController.destroy();
    }

    @Test
    public void start_chosenTimeExist_load() {

        SetTimeActivity activity;
        ActivityController<SetTimeActivity> activityController;
        activityController = Robolectric.buildActivity(SetTimeActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);

        // Create chosen Time
        MensaMeetTime time = new MensaMeetTime(MensaMeetTime.stringToTime("13:00"),
                MensaMeetTime.stringToTime("16:00"));

        MensaMeetSession.getInstance().setChosenTime(time);

        activityController.create();
        activityController.start();
        activityController.resume();

        TextView startTime = activity.findViewById(R.id.startTimeText);
        TextView endTime = activity.findViewById(R.id.endTimeText);

        assertEquals("13:00", startTime.getText().toString());
        assertEquals("16:00", endTime.getText().toString());

        activityController.destroy();

    }

    @Test
    public void chooseStartTime_afterEndTime_noEffect() {

        SetTimeActivity activity;
        ActivityController<SetTimeActivity> activityController;
        activityController = Robolectric.buildActivity(SetTimeActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);

        activityController.create();
        activityController.start();
        activityController.resume();
        activityController.visible();

        TextView startTime = activity.findViewById(R.id.startTimeText);
        startTime.setText("12:00");
        TextView endTime = activity.findViewById(R.id.endTimeText);
        endTime.setText("12:00");

        startTime.performClick();
        TimePickerDialog dialog = (TimePickerDialog) ShadowTimePickerDialog.getLatestDialog();
        dialog.updateTime(16,00);
        dialog.getButton(TimePickerDialog.BUTTON_POSITIVE).performClick();

        // End time is 12:00, so start time 16:00 should be ignored.

        assertEquals("12:00", startTime.getText().toString());

        activityController.destroy();

    }

    @Test
    public void clickNext_saveAndStartSelectGroupActivity() {

        SetTimeActivity activity;
        ActivityController<SetTimeActivity> activityController;
        activityController = Robolectric.buildActivity(SetTimeActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);
        MensaMeetSession.getInstance().setChosenTime(null);

        activityController.create();
        activityController.start();
        activityController.resume();
        activityController.visible();

        activity.findViewById(R.id.navigation_next).performClick();

        // Saved.
        assertNotNull(MensaMeetSession.getInstance().getChosenTime());

        // Redirect to SetTimeActivity
        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(SelectGroupActivity.class, shadowIntent.getIntentClass());

        activityController.destroy();
    }

    @Test
    public void clickHome_saveAndStartHomeActivity() {

        SetTimeActivity activity;
        ActivityController<SetTimeActivity> activityController;
        activityController = Robolectric.buildActivity(SetTimeActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);
        MensaMeetSession.getInstance().setChosenTime(null);

        activityController.create();
        activityController.start();
        activityController.resume();
        activityController.visible();

        activity.findViewById(R.id.navigation_home).performClick();

        // Saved.
        assertNotNull(MensaMeetSession.getInstance().getChosenTime());

        // Redirect to SetTimeActivity
        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(HomeActivity.class, shadowIntent.getIntentClass());

        activityController.destroy();
    }

    @Test
    public void clickBack_saveAndstartSelectLinesActivity() {

        SetTimeActivity activity;
        ActivityController<SetTimeActivity> activityController;
        activityController = Robolectric.buildActivity(SetTimeActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);
        MensaMeetSession.getInstance().setChosenTime(null);

        activityController.create();
        activityController.start();
        activityController.resume();
        activityController.visible();

        activity.findViewById(R.id.navigation_back).performClick();

        // Saved.
        assertNotNull(MensaMeetSession.getInstance().getChosenTime());

        // Redirect to SetTimeActivity
        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(SelectLinesActivity.class, shadowIntent.getIntentClass());

        activityController.destroy();
    }



}