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

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaData;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.model.Status;
import edu.kit.mensameet.client.model.User;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class SelectGroupActivityTest {

    private User completeUser;

    public List<Group> getDataList() {
        // Mock data

        List<Group> dataList = new ArrayList<Group>();

        Group g1 = new Group();
        g1.setName("Mensaphilosophen");
        g1.setMotto("Du bist, was du isst!");
        g1.setMeetingDate(MensaMeetTime.stringToTime("12:00"));
        g1.setLine("LINE_ONE");
        g1.setMaxMembers(10);
        List<User> userList1 = new ArrayList<User>();
        User u1 = new User();
        u1.setName("Tim");
        u1.setMotto("isst gerne Fisch");
        u1.setProfilePictureId(2);
        userList1.add(u1);
        User u2 = new User();
        u2.setName("Alice");
        u2.setMotto("seeking for Bob");
        userList1.add(u2);
        g1.setUsers(userList1);
        dataList.add(g1);

        Group g2 = new Group();
        g2.setName("Neu am KIT");
        g2.setMotto("Hier könnt ihr neue Leute kennenlernen!");
        g2.setMeetingDate(MensaMeetTime.stringToTime("12:30"));
        g2.setLine("LINE_FOUR_FIVE");
        g2.setMaxMembers(15);
        List<User> userList2 = new ArrayList<User>();
        User u21 = new User();
        u21.setName("Petra");
        u21.setMotto("(^u^)");
        u21.setProfilePictureId(4);
        userList2.add(u21);
        g2.setUsers(userList2);
        dataList.add(g2);

        Group g3 = new Group();
        g3.setName("Dating");
        g3.setMotto("Liebe geht durch den Magen.");
        g3.setMeetingDate(MensaMeetTime.stringToTime("11:30"));
        g3.setLine("LINE_TWO");
        g3.setMaxMembers(8);
        List<User> userList3 = new ArrayList<User>();
        User u31 = new User();
        u31.setName("Theo");
        u31.setMotto("I <3 Linie 2");
        u31.setProfilePictureId(1);
        userList3.add(u31);
        User u32 = new User();
        u32.setName("Carsten");
        u32.setMotto("Wo sind hier die ganzen Frauen??");
        u32.setProfilePictureId(1);
        userList3.add(u32);
        g3.setUsers(userList3);
        dataList.add(g3);

        Group g4 = new Group();
        g4.setName("Spätes Frühstück");
        g4.setMotto("Guten Morgen");
        g4.setMeetingDate(MensaMeetTime.stringToTime("12:00"));
        g4.setLine("CAFETARIA");
        g4.setMaxMembers(10);
        List<User> userList4 = new ArrayList<User>();
        User u41 = new User();
        u41.setName("Lena");
        u41.setMotto("Schlaflose Nächte...");
        u41.setProfilePictureId(0);
        userList4.add(u41);
        g4.setUsers(userList4);
        dataList.add(g4);

        return dataList;
    }

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

        SelectGroupActivity activity;
        ActivityController<SelectGroupActivity> activityController;
        activityController = Robolectric.buildActivity(SelectGroupActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(new User());

        activityController.create();
        activityController.start();
        activityController.resume();

        assertTrue(activity.isFinishing());

        activityController.destroy();
    }

    @Test
    public void clickHome_startHomeActivity() {

        SelectGroupActivity activity;
        ActivityController<SelectGroupActivity> activityController;
        activityController = Robolectric.buildActivity(SelectGroupActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);

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

    @Test
    public void clickBack_startSetTimeActivity() {

        SelectGroupActivity activity;
        ActivityController<SelectGroupActivity> activityController;
        activityController = Robolectric.buildActivity(SelectGroupActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);

        activityController.create();
        activityController.start();
        activityController.resume();
        activityController.visible();

        activity.findViewById(R.id.navigation_back).performClick();

        // Redirect to SetTimeActivity
        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(SetTimeActivity.class, shadowIntent.getIntentClass());

        activityController.destroy();
    }

    @Test
    public void clickNewGroup_startCreateGroupActivity() {

        SelectGroupActivity activity;
        ActivityController<SelectGroupActivity> activityController;
        activityController = Robolectric.buildActivity(SelectGroupActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(completeUser);

        activityController.create();
        activityController.start();
        activityController.resume();
        activityController.visible();

        activity.findViewById(R.id.fab).performClick();

        // Redirect to SetTimeActivity
        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(CreateGroupActivityTest.class, shadowIntent.getIntentClass());

        activityController.destroy();
    }


    /*
    @Test
    public void clickJoin_call() {

        SelectGroupViewModel spyViewModel;
        spyViewModel = spy(new SelectGroupViewModel());
        doReturn(true).when(spyViewModel).loadGroups();
        doReturn(getDataList()).when(spyViewModel).getReceivedGroups();
        //doNothing().when(spyViewModel).saveUserAndNext();

        SelectGroupActivity activity;
        ActivityController<SelectGroupActivity> activityController;
        activityController = Robolectric.buildActivity(SelectGroupActivity.class);
        activity = activityController.get();

        MensaMeetSession.getInstance().setUser(new User());

        activityController.create();
        activityController.start();
        // Inject spyViewModel.
        activity.setViewModel(spyViewModel);
        activityController.resume();

        assertTrue(activity.isFinishing());

        activityController.destroy();
    }
    */

}