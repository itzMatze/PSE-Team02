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
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterActivityTest {
    @Rule
    public ActivityTestRule<RegisterActivity> beginActivityRule =
            new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void alreadyRegisteredTest() {
        onView(withId(R.id.email)).perform(typeText("matze@matze.com"));
        onView(withId(R.id.email)).check(matches(withText("matze@matze.com")));
        onView(withId(R.id.password)).perform(typeText("passwort"));
        onView(withId(R.id.password)).check(matches(withText("passwort")));
        onView(withId(R.id.confirmPassword)).perform(typeText("passwort"));
        onView(withId(R.id.confirmPassword)).check(matches(withText("passwort")));
        onView(withId(R.id.login)).perform(click());
        // wait for server to decline, because email is already registered
        onView(isRoot()).perform(TestHelper.waitId(3000));
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.confirmPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    @Test
    public void wrongPasswordConfirmationTest() {
        onView(withId(R.id.email)).perform(typeText("matzetest@testmatze.com"));
        onView(withId(R.id.email)).check(matches(withText("matzetest@testmatze.com")));
        onView(withId(R.id.password)).perform(typeText("passwort"));
        onView(withId(R.id.password)).check(matches(withText("passwort")));
        onView(withId(R.id.confirmPassword)).perform(typeText("trowssap"));
        onView(withId(R.id.confirmPassword)).check(matches(withText("trowssap")));
        onView(withId(R.id.login)).perform(click());
        // wait for server to decline, because password and confirmation are not the same
        onView(isRoot()).perform(TestHelper.waitId(3000));
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.confirmPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    @Test
    public void wrongEmailFormatTest() {
        onView(withId(R.id.email)).perform(typeText("matze"));
        onView(withId(R.id.email)).check(matches(withText("matze")));
        onView(withId(R.id.password)).perform(typeText("passwort"));
        onView(withId(R.id.password)).check(matches(withText("passwort")));
        onView(withId(R.id.confirmPassword)).perform(typeText("passwort"));
        onView(withId(R.id.confirmPassword)).check(matches(withText("passwort")));
        onView(withId(R.id.login)).perform(click());
        // wait for server to decline, because email is not in the right format
        onView(isRoot()).perform(TestHelper.waitId(3000));
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.confirmPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }
}
