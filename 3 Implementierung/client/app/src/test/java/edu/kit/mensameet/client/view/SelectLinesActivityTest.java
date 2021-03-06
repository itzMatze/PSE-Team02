package edu.kit.mensameet.client.view;

import android.content.Intent;
import android.os.Build;

import androidx.recyclerview.widget.RecyclerView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.List;

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
public class SelectLinesActivityTest {


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

        SelectLinesActivity activity;
        ActivityController<SelectLinesActivity> activityController;
        activityController = Robolectric.buildActivity(SelectLinesActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(new User());

        activityController.create();
        activityController.start();
        activityController.resume();

        assertTrue(activity.isFinishing());

        activityController.destroy();
    }

    @Test
    public void start_chosenLinesExist_selectInList() {

        SelectLinesActivity activity;
        ActivityController<SelectLinesActivity> activityController;
        activityController = Robolectric.buildActivity(SelectLinesActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);

        // Create selected lines
        Line linie1 = new Line();
        linie1.setMealLine("LINE_ONE");
        List<Line> chosenLines = new ArrayList<>();
        chosenLines.add(linie1);

        MensaMeetSession.getInstance().setChosenLines(chosenLines);

        activityController.create();
        activityController.start();
        activityController.resume();

        RecyclerView recyclerView = activity.findViewById((int)R.string.list_recyclerview);

        MensaMeetListAdapter<Line> adapter = (MensaMeetListAdapter)recyclerView.getAdapter();
        // Get selected item in list
        List<Line> selectedList = adapter.getSelectedObjects();

        assertEquals(linie1, selectedList.get(0));

        activityController.destroy();

    }

    @Test
    public void noLineSelected_errorMessage() {

        SelectLinesActivity activity;
        ActivityController<SelectLinesActivity> activityController;
        activityController = Robolectric.buildActivity(SelectLinesActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);

        activityController.create();
        activityController.start();
        activityController.resume();
        activityController.visible();

        // No line selected.

        // Get list.
        RecyclerView recyclerView = activity.findViewById((int)R.string.list_recyclerview);

        recyclerView.getChildAt(0).performClick();

        activity.findViewById(R.id.navigation_next).performClick();

        assertEquals(activity.getResources().getString(R.string.selectALine),
                ShadowToast.getTextOfLatestToast());

        activityController.destroy();

    }

    @Test
    public void linesSelected_saveAndStartSetTimeActivity() {

        SelectLinesActivity activity;
        ActivityController<SelectLinesActivity> activityController;
        activityController = Robolectric.buildActivity(SelectLinesActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);
        MensaMeetSession.getInstance().setChosenLines(null);

        activityController.create();
        activityController.start();
        activityController.resume();
        activityController.visible();

        // Get list.
        RecyclerView recyclerView = activity.findViewById((int)R.string.list_recyclerview);

        recyclerView.getChildAt(0).performClick();

        activity.findViewById(R.id.navigation_next).performClick();

        // Saved.
        assertNotNull(MensaMeetSession.getInstance().getChosenLines());

        // Redirect to SetTimeActivity
        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(SetTimeActivity.class, shadowIntent.getIntentClass());

        activityController.destroy();
    }

    @After
    public void cleanUp() {

    }
}


/*

@RunWith(RobolectricTestRunner.class)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" })
@PrepareForTest(RequestUtil.class)
public class SelectLinesActivityTest {

    @Test
    public void accessList() {

        SelectLinesActivity activity = Robolectric.setupActivity(SelectLinesActivity.class);
        activity.findViewById((int)R.string.list_recyclerview).performClick();

    }

}
*/