package edu.kit.mensameet.client.view;

import android.os.Build;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaData;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.Status;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.viewmodel.GroupJoinedViewModel;
import edu.kit.mensameet.client.viewmodel.UserViewModel;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class GroupJoinedActivityTest {

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
    public void start_groupTokenNull_ActivityFinishes() {

        GroupJoinedActivity activity;
        ActivityController<GroupJoinedActivity> activityController;
        activityController = Robolectric.buildActivity(GroupJoinedActivity.class);
        activity = activityController.get();

        completeUser.setGroupToken(null);
        MensaMeetSession.getInstance().setUser(completeUser);

        activityController.create();
        activityController.start();
        activityController.resume();

        assertTrue(activity.isFinishing());

        activityController.destroy();
    }

    @Test
    public void start_groupLoadedFromServer() {

        GroupJoinedViewModel spyViewModel = spy(new GroupJoinedViewModel());
        doReturn(true).when(spyViewModel).setGroupByToken(any(String.class));
        Group returnGroup = new Group();
        returnGroup.setToken("groupToken");
        returnGroup.setName("New loaded group");
        doReturn(returnGroup).when(spyViewModel).getGroup();

        GroupJoinedActivity activity;
        ActivityController<GroupJoinedActivity> activityController;
        activityController = Robolectric.buildActivity(GroupJoinedActivity.class);
        activity = activityController.get();

        completeUser.setGroupToken("groupToken");
        MensaMeetSession.getInstance().setUser(completeUser);

        activityController.create();
        // Inject spyViewModel.
        activity.setViewModel(spyViewModel);
        activityController.start();
        activityController.resume();
        activityController.visible();

        // Verify if method to load group from server was called.
        verify(spyViewModel).setGroupByToken(any(String.class));

        activityController.destroy();
    }
}
