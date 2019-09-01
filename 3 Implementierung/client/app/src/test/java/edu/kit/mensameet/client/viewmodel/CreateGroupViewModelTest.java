package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.RequestUtil;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 *
 */
@RunWith(RobolectricTestRunner.class)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" })
@PrepareForTest(RequestUtil.class)

public class CreateGroupViewModelTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    CreateGroupViewModel vm = new CreateGroupViewModel();;

    @Test
    public void noCreatedGroup() {

        // Given
        MensaMeetSession.getInstance().setCreatedGroup(null);

        // When
        vm.saveGroupAndNext();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>(null, CreateGroupViewModel.State.ERROR_SAVING_GROUP));

    }

    @Test
    public void createGroupFails() throws RequestUtil.RequestException {

        PowerMockito.mockStatic(RequestUtil.class);
        MensaMeetSession.getInstance().setCreatedGroup(new Group());
        MensaMeetSession.getInstance().setUser(new User());

        // Given
        when(RequestUtil.createGroup(any(Group.class), any(String.class)))
            .thenThrow(new RequestUtil.RequestException(0, "createGroup failed"));

        // When
        vm.saveGroupAndNext();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>("createGroup failed", CreateGroupViewModel.State.ERROR_SAVING_GROUP));

    }

    @Test
    public void addUserToGroupFails() throws Exception {

        PowerMockito.mockStatic(RequestUtil.class);
        MensaMeetSession.getInstance().setCreatedGroup(new Group());
        MensaMeetSession.getInstance().setUser(new User());

        // Given
        when(RequestUtil.createGroup(any(Group.class), any(String.class)))
                .thenReturn(new Group());

        doThrow(new RequestUtil.RequestException(0, "addUserToGroup failed"))
                .when(RequestUtil.class, "addUserToGroup", any(String.class), any(String.class));

        // When
        vm.saveGroupAndNext();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>("addUserToGroup failed", CreateGroupViewModel.State.ERROR_SAVING_GROUP));

    }

    @Test
    public void getUserFails() throws Exception {

        PowerMockito.mockStatic(RequestUtil.class);
        MensaMeetSession.getInstance().setCreatedGroup(new Group());
        MensaMeetSession.getInstance().setUser(new User());

        // Given
        when(RequestUtil.createGroup(any(Group.class), any(String.class)))
                .thenReturn(new Group());

        doNothing()
                .when(RequestUtil.class, "addUserToGroup", any(String.class), any(String.class));

        doThrow(new RequestUtil.RequestException(0, "getting user failed"))
                .when(RequestUtil.class, "getUser", any(String.class), any(String.class));


        // When
        vm.saveGroupAndNext();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>("getting user failed", CreateGroupViewModel.State.RELOADING_USER_FAILED));

    }


}

