package edu.kit.mensameet.client.viewmodel;
import android.util.Pair;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;

/**
 *
 */
@RunWith(RobolectricTestRunner.class)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" })
public class SetTimeViewModelTest {
    @Rule
    public PowerMockRule rule = new PowerMockRule();

    SetTimeViewModel vm = new SetTimeViewModel();

    //todo:

    @Test
    public void saveTimeAndNextSuccess() throws Exception {

        // When
        vm.saveTimeAndNext();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>(null, SetTimeViewModel.State.TIME_SAVED_NEXT));

    }

    @Test
    public void saveTimeAndBackSuccess() throws Exception {
        // When
        vm.saveTimeAndBack();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>(null, SetTimeViewModel.State.TIME_SAVED_BACK));

    }
}
