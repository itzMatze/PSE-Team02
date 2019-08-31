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
public class SelectLinesViewModelTest {
    @Rule
    public PowerMockRule rule = new PowerMockRule();

    SelectLinesViewModel vm = new SelectLinesViewModel();

    //todo:

    @Test
    public void noLinesSelected() throws Exception {

        // When
        vm.saveLinesAndNext();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>(null, SelectLinesViewModel.State.NO_LINES_SELECTED));

    }

    @Test
    public void saveLinesSuccess() throws Exception {
        // When
        vm.saveLinesAndBack();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>(null, SelectLinesViewModel.State.LINES_SAVED_BACK));

    }
}
