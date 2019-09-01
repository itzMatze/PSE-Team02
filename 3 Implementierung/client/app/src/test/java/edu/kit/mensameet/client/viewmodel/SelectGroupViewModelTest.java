package edu.kit.mensameet.client.viewmodel;
import android.util.Pair;


import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;

import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.util.RequestUtil;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 */
@RunWith(RobolectricTestRunner.class)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" })
@PrepareForTest(RequestUtil.class)
public class SelectGroupViewModelTest {
    @Rule
    public PowerMockRule rule = new PowerMockRule();

    SelectGroupViewModel vm = new SelectGroupViewModel();

    @Test
    public void noTimeChosen() throws Exception {

        PowerMockito.mockStatic(RequestUtil.class);
        SelectGroupViewModel mock = mock(SelectGroupViewModel.class);
        // Given
        when(mock.getChosenTime())
                .thenReturn(null);

        // When
        vm.loadGroups();

        // Then
        Assert.assertEquals(vm.getEventLiveData().getValue(),
                new Pair<String, StateInterface>(null, SelectGroupViewModel.State.NO_TIME_CHOSEN));

    }

    @Test
    public void noLinesChosen() throws Exception {

        PowerMockito.mockStatic(RequestUtil.class);
        SelectGroupViewModel mock = Mockito.spy(SelectGroupViewModel.class);
        // Given
        when(mock.getChosenTime())
                .thenReturn(new MensaMeetTime(new Date(123456)));
        when(mock.getChosenLines())
                .thenReturn(null);

        // When
        mock.loadGroups();

        // Then
        Assert.assertNotNull(mock.getChosenTime());
        Assert.assertEquals(mock.getEventLiveData().getValue(),
                new Pair<String, StateInterface>(null, SelectGroupViewModel.State.NO_LINES_CHOSEN));

    }

    @Test
    public void loadingGroupFailed() throws Exception {

        PowerMockito.mockStatic(RequestUtil.class);
        SelectGroupViewModel mock = Mockito.spy(SelectGroupViewModel.class);

        // Given
        when(mock.getChosenTime())
                .thenReturn(new MensaMeetTime(new Date(123456)));
        PowerMockito.when(RequestUtil.getGroupByPrefferences(any(String.class), any(MensaMeetTime.class),any(String[].class)))
                .thenThrow(new RequestUtil.RequestException(0, "loading failed"));

        // When
        mock.loadGroups();

        // Then
        Assert.assertNotNull(mock.getChosenTime());
        //todo
        /*Assert.assertNotNull(mock.getChosenLines());
        Assert.assertEquals(mock.getEventLiveData().getValue(),
                new Pair<String, StateInterface>("loading failed", SelectGroupViewModel.State.LOADING_GROUPS_FAILED));
*/
    }

}
