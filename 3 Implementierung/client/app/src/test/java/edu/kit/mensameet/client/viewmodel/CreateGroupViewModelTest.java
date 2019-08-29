package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.RequestUtil;
import edu.kit.mensameet.client.view.BuildConfig;

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

        MensaMeetSession.getInstance().setCreatedGroup(null);

        vm.saveGroupAndNext();

        Assert.assertEquals(vm.getEventLiveData().getValue(), new Pair<String, StateInterface>(null, CreateGroupViewModel.State.ERROR_SAVING_GROUP));

    }

    @Test
    public void createGroupFails() {

        PowerMockito.mockStatic(RequestUtil.class);

        //MensaMeetSession.getInstance().setCreatedGroup(null);

        //vm.saveGroupAndNext();

        //Assert.assertEquals(vm.getEventLiveData().getValue(), new Pair<String, StateInterface>(null, CreateGroupViewModel.State.ERROR_SAVING_GROUP));

    }

    @Test
    public void  test2() {

    }

}

