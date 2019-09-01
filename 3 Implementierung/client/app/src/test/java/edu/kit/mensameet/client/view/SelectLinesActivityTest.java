package edu.kit.mensameet.client.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import edu.kit.mensameet.client.util.RequestUtil;

/**
 *
 */
@RunWith(RobolectricTestRunner.class)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" })
@PrepareForTest(RequestUtil.class)
public class SelectLinesActivityTest {

    @Test
    public void accessList() {

        SelectLinesActivity activity = Robolectric.setupActivity(SelectLinesActivity.class);
        activity.findViewById((int)R.string.list_recyclerview).performClick();

    }

}
