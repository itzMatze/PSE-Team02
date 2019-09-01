package edu.kit.mensameet.client.view;

import android.os.Build;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.RequestUtil;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(RobolectricTestRunner.class)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*", "androidx.*" })
//@PrepareForTest(RequestUtil.class)

@Config(sdk = Build.VERSION_CODES.O_MR1)
public class UserActivityTest {

    /*@Rule
    public PowerMockRule rule = new PowerMockRule();*/

    @Test
    public void onCreate_userNull_ActivityFinishes() throws RequestUtil.RequestException {

        MensaMeetSession.getInstance().setUser(new User());
        /*PowerMockito.mockStatic(RequestUtil.class);
        when(RequestUtil.getUser(any(String.class), any(String.class))).thenThrow(new RequestUtil.RequestException(0, "createGroup failed"));
*/
        UserActivity activity = Robolectric.setupActivity(UserActivity.class);

        assertTrue(activity.isFinishing());

    }
}
