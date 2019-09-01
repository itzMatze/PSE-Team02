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
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
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
public class HomeActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> beginActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void goEatTest() {
        onView(withId(R.id.usernameTextInput)).perform(typeText("matze@matze.com"));
        onView(withId(R.id.usernameTextInput)).check(matches(withText("matze@matze.com")));
        onView(withId(R.id.password)).perform(typeText("passwort"));
        onView(withId(R.id.password)).check(matches(withText("passwort")));
        onView(withId(R.id.login)).perform(click());
        // wait for server to authenticate user
        onView(isRoot()).perform(TestHelper.waitId(3000));
        onView(withId(R.id.goEat)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
        onView(withId(R.id.goEat)).perform(click());
        onView(withId(R.id.goEat)).check((doesNotExist()));
        onView(withId(R.id.profile)).check(doesNotExist());
        onView(withId(R.id.logout)).check(doesNotExist());
        onView(withId(R.id.imageView2)).check(doesNotExist());
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.logout)).perform(click());
    }

    @Test
    public void profileTest() {
        onView(withId(R.id.usernameTextInput)).perform(typeText("matze@matze.com"));
        onView(withId(R.id.usernameTextInput)).check(matches(withText("matze@matze.com")));
        onView(withId(R.id.password)).perform(typeText("passwort"));
        onView(withId(R.id.password)).check(matches(withText("passwort")));
        onView(withId(R.id.login)).perform(click());
        // wait for server to authenticate user
        onView(isRoot()).perform(TestHelper.waitId(3000));
        onView(withId(R.id.goEat)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.id.goEat)).check((doesNotExist()));
        onView(withId(R.id.profile)).check(doesNotExist());
        onView(withId(R.id.logout)).check(doesNotExist());
        onView(withId(R.id.imageView2)).check(doesNotExist());
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.logout)).perform(click());
    }

    @Test
    public void logoutTest() {
        onView(withId(R.id.usernameTextInput)).perform(typeText("matze@matze.com"));
        onView(withId(R.id.usernameTextInput)).check(matches(withText("matze@matze.com")));
        onView(withId(R.id.password)).perform(typeText("passwort"));
        onView(withId(R.id.password)).check(matches(withText("passwort")));
        onView(withId(R.id.login)).perform(click());
        // wait for server to authenticate user
        onView(isRoot()).perform(TestHelper.waitId(3000));
        onView(withId(R.id.goEat)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).perform(click());
        onView(withId(R.id.register)).check(matches(isDisplayed()));
        onView(withId(R.id.login)).check(matches(isDisplayed()));
        onView(withId(R.id.textView)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
    }
}
