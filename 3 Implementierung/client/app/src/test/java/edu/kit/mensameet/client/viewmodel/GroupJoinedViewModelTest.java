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

import java.security.SecureRandom;

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
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest(RequestUtil.class)

public class GroupJoinedViewModelTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    GroupJoinedViewModel vm = new GroupJoinedViewModel();

    @Test
    public void groupNotFound() throws RequestUtil.RequestException {

        PowerMockito.mockStatic(RequestUtil.class);
        MensaMeetSession.getInstance().setUser(new User());

        // Given
        when(RequestUtil.getGroup(any(String.class), any(String.class)))
                .thenThrow(new RequestUtil.RequestException(404, "Group not found"));

        // When
        vm.setGroupByToken("token");

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>("Group not found", GroupJoinedViewModel.State.ERROR_LOADING_GROUP_SO_USER_UPDATED));
    }

    @Test
    public void loadingGroupFailed() throws RequestUtil.RequestException {

        PowerMockito.mockStatic(RequestUtil.class);
        MensaMeetSession.getInstance().setUser(new User());

        // Given
        when(RequestUtil.getGroup(any(String.class), any(String.class)))
                .thenThrow(new RequestUtil.RequestException(0, "error loading Group"));

        // When
        vm.setGroupByToken("token");

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>("error loading Group", GroupJoinedViewModel.State.ERROR_LOADING_GROUP));
    }

    @Test
    public void leavingGroupFailed() throws Exception {


        PowerMockito.mockStatic(RequestUtil.class);
        MensaMeetSession.getInstance().setCreatedGroup(new Group());
        MensaMeetSession.getInstance().setUser(new User());
        vm.setGroup(new Group());

        // Given
        doThrow(new RequestUtil.RequestException(0, "leaving group failed"))
                .when(RequestUtil.class, "removeUserFromGroup", any(String.class), any(String.class));


        // When
        vm.leaveGroup();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>("leaving group failed", GroupJoinedViewModel.State.ERROR_LEAVING_GROUP));
    }

    @Test
    public void reloadingUserFailed() throws Exception {


        PowerMockito.mockStatic(RequestUtil.class);
        MensaMeetSession.getInstance().setCreatedGroup(new Group());
        MensaMeetSession.getInstance().setUser(new User());
        vm.setGroup(new Group());

        // Given
        doNothing()
                .when(RequestUtil.class, "removeUserFromGroup", any(String.class), any(String.class));
        doThrow(new RequestUtil.RequestException(0, "reloading user failed"))
                .when(RequestUtil.class, "getUser", any(String.class), any(String.class));


        // When
        vm.leaveGroup();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>("reloading user failed", GroupJoinedViewModel.State.RELOADING_USER_FAILED));
    }

    @Test
    public void leavingGroupSuccess() throws Exception {


        PowerMockito.mockStatic(RequestUtil.class);
        MensaMeetSession.getInstance().setCreatedGroup(new Group());
        MensaMeetSession.getInstance().setUser(new User());
        vm.setGroup(new Group());

        // Given
        doNothing()
                .when(RequestUtil.class, "removeUserFromGroup", any(String.class), any(String.class));
        when(RequestUtil.getUser(any(String.class), any(String.class)))
                .thenReturn(new User());


        // When
        vm.leaveGroup();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>(null, GroupJoinedViewModel.State.GROUP_LEFT));
    }
}
