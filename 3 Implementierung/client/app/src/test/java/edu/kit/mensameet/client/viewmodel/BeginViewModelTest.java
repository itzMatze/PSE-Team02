package edu.kit.mensameet.client.viewmodel;
import android.util.Pair;


import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import edu.kit.mensameet.client.util.RequestUtil;


/**
 *
 */
@RunWith(RobolectricTestRunner.class)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" })
@PrepareForTest(RequestUtil.class)
public class BeginViewModelTest {
    @Rule
    public PowerMockRule rule = new PowerMockRule();

    BeginViewModel vm = new BeginViewModel();

    @Test
    public void registerSuccess() {

        // When
        vm.onRegisterClick(vm);

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>(null, BeginViewModel.State.REGISTER));

    }

    @Test
    public void loginSuccess() {

        // When
        vm.onLoginClick(vm);

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>(null, BeginViewModel.State.LOGIN));

    }

}
