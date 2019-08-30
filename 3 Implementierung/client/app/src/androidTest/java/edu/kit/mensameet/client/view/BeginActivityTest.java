package edu.kit.mensameet.client.view;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class BeginActivityTest {
    @Rule
    public ActivityTestRule<BeginActivity> beginActivityRule =
            new ActivityTestRule<>(BeginActivity.class);

    @Test
    public void gotoLoginTest() {
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.usernameTextInput)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    @Test
    public void gotoRegisterTest() {
        onView(withId(R.id.register)).perform(click());
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.confirmPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }
}
