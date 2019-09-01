package edu.kit.mensameet.client.viewmodel;
import android.content.Context;
import android.util.Pair;


import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.Status;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.RequestUtil;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doNothing;

/**
 *
 */
@RunWith(RobolectricTestRunner.class)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" })
@PrepareForTest(RequestUtil.class)
public class HomeViewModelTest {
    @Rule
    public PowerMockRule rule = new PowerMockRule();
    HomeViewModel vm = new HomeViewModel();

    @Test
    public void logOutSuccess() {

        vm.logout(mock(Context.class));

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>(null, HomeViewModel.State.TO_BEGIN));

    }

    @Test
    public void goEatToUserPage() {

        MensaMeetSession.getInstance().setUser(new User());
        Assert.assertTrue(MensaMeetSession.getInstance().userDataIncomplete());
        // When
        vm.goEat();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                                new Pair<String, StateInterface>(null, HomeViewModel.State.TO_USER));

     }

    @Test
    public void goEatToSelectLinesPage() {

        User testUser = new User();
        testUser.setName("test");
        testUser.setStatus(Status.STUDENT);
        MensaMeetSession.getInstance().setUser(testUser);
        Assert.assertFalse(MensaMeetSession.getInstance().userDataIncomplete());
        // When
        vm.goEat();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>(null, HomeViewModel.State.TO_SELECT_LINES));

    }

    @Test
    public void goEatToJoinedGroupPage() {

        User testUser = new User();
        testUser.setName("test");
        testUser.setStatus(Status.STUDENT);
        testUser.setGroupToken("tsetGroup");
        MensaMeetSession.getInstance().setUser(testUser);
        Assert.assertFalse(MensaMeetSession.getInstance().userDataIncomplete());
        // When
        vm.goEat();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>(null, HomeViewModel.State.TO_GROUP_JOINED));

    }


}
