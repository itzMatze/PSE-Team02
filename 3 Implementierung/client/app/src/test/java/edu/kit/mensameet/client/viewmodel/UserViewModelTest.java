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
public class UserViewModelTest {
    @Rule
    public PowerMockRule rule = new PowerMockRule();

    UserViewModel vm = new UserViewModel();;

    @Test
    public void reloadingUserFailed() throws RequestUtil.RequestException {

        PowerMockito.mockStatic(RequestUtil.class);
        MensaMeetSession.getInstance().setCreatedGroup(new Group());
        MensaMeetSession.getInstance().setUser(new User());
        // Given
        when(RequestUtil.getUser(any(String.class), any(String.class)))
                .thenThrow(new RequestUtil.RequestException(0, "reloading user failed"));

        // When
        vm.loadUser();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>("reloading user failed", UserViewModel.State.RELOADING_USER_FAILED));

    }

    @Test
    public void saveUserFailed() throws Exception {

        PowerMockito.mockStatic(RequestUtil.class);
        MensaMeetSession.getInstance().setCreatedGroup(new Group());
        MensaMeetSession.getInstance().setUser(new User());
        vm.setUser(new User());
        // Given
        doThrow(new RequestUtil.RequestException(0, "save user failed"))
                .when(RequestUtil.class, "updateUser", any(User.class));

        // When
        vm.saveUserAndNext();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>("save user failed", UserViewModel.State.ERROR_SAVING_USER));

    }

    @Test
    public void saveUserSuccess() throws Exception {

        PowerMockito.mockStatic(RequestUtil.class);
        MensaMeetSession.getInstance().setCreatedGroup(new Group());
        MensaMeetSession.getInstance().setUser(new User());
        vm.setUser(new User());
        // Given
        doNothing()
                .when(RequestUtil.class, "updateUser", any(User.class));

        // When
        vm.saveUserAndNext();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>(null, UserViewModel.State.USER_SAVED_NEXT));

    }

    @Test
    public void saveUserIsNull() throws Exception {

        PowerMockito.mockStatic(RequestUtil.class);
        MensaMeetSession.getInstance().setCreatedGroup(new Group());
        MensaMeetSession.getInstance().setUser(new User());
        vm.setUser(null);

        // When
        vm.saveUserAndNext();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>(null, UserViewModel.State.ERROR_SAVING_USER));

    }
}
