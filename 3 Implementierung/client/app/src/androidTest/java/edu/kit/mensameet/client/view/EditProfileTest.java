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
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EditProfileTest {
    @Rule
    public ActivityTestRule<LoginActivity> beginActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void changeGenderTest() {
        onView(withId(R.id.usernameTextInput)).perform(typeText("matze@matze.com"));
        onView(withId(R.id.usernameTextInput)).check(matches(withText("matze@matze.com")));
        onView(withId(R.id.password)).perform(typeText("passwort"));
        onView(withId(R.id.password)).check(matches(withText("passwort")));
        onView(withId(R.id.login)).perform(click());
        // wait for server to authenticate user
        onView(isRoot()).perform(TestHelper.waitId(3000));
        // potentially fails when server answer comes to slow
        onView(withId(R.id.goEat)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.string.field_gender)).check(matches(isDisplayed()));
        onView(withId(R.string.field_status)).check(matches(isDisplayed()));
        onView(withId(R.string.field_subject)).check(matches(isDisplayed()));
        onView(withId(R.string.field_gender)).perform(click());
        onView(allOf(withText("männlich"))).perform(click());
        onView(withId(R.string.field_gender)).check(matches(withSpinnerText(containsString("männlich"))));
        onView(withId(R.id.navigation_next)).perform(click());
        onView(withId(R.id.goEat)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.string.field_gender)).check(matches(isDisplayed()));
        onView(withId(R.string.field_status)).check(matches(isDisplayed()));
        onView(withId(R.string.field_subject)).check(matches(isDisplayed()));
        onView(withId(R.string.field_gender)).check(matches(withSpinnerText(containsString("männlich"))));
        onView(withId(R.string.field_gender)).perform(click());
        onView(allOf(withText("weiblich"))).perform(click());
        onView(withId(R.string.field_gender)).check(matches(withSpinnerText(containsString("weiblich"))));
        onView(withId(R.id.navigation_next)).perform(click());
        onView(withId(R.id.goEat)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.string.field_gender)).check(matches(isDisplayed()));
        onView(withId(R.string.field_status)).check(matches(isDisplayed()));
        onView(withId(R.string.field_subject)).check(matches(isDisplayed()));
        onView(withId(R.string.field_gender)).check(matches(withSpinnerText(containsString("weiblich"))));
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.logout)).perform(click());
    }

    @Test
    public void changeSubjectTest() {
        onView(withId(R.id.usernameTextInput)).perform(typeText("matze@matze.com"));
        onView(withId(R.id.usernameTextInput)).check(matches(withText("matze@matze.com")));
        onView(withId(R.id.password)).perform(typeText("passwort"));
        onView(withId(R.id.password)).check(matches(withText("passwort")));
        onView(withId(R.id.login)).perform(click());
        // wait for server to authenticate user
        onView(isRoot()).perform(TestHelper.waitId(3000));
        // potentially fails when server answer comes to slow
        onView(withId(R.id.goEat)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.string.field_gender)).check(matches(isDisplayed()));
        onView(withId(R.string.field_status)).check(matches(isDisplayed()));
        onView(withId(R.string.field_subject)).check(matches(isDisplayed()));
        onView(withId(R.string.field_subject)).perform(click());
        onView(allOf(withText("Pädagogik"))).perform(click());
        onView(withId(R.string.field_subject)).check(matches(withSpinnerText(containsString("Pädagogik"))));
        onView(withId(R.id.navigation_next)).perform(click());
        onView(withId(R.id.goEat)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.string.field_gender)).check(matches(isDisplayed()));
        onView(withId(R.string.field_status)).check(matches(isDisplayed()));
        onView(withId(R.string.field_subject)).check(matches(isDisplayed()));
        onView(withId(R.string.field_subject)).check(matches(withSpinnerText(containsString("Pädagogik"))));
        onView(withId(R.string.field_subject)).perform(click());
        onView(allOf(withText("Physik"))).perform(click());
        onView(withId(R.string.field_subject)).check(matches(withSpinnerText(containsString("Physik"))));
        onView(isRoot()).perform(TestHelper.waitId(300));
        onView(withId(R.id.navigation_next)).perform(click());
        onView(withId(R.id.goEat)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.string.field_gender)).check(matches(isDisplayed()));
        onView(withId(R.string.field_status)).check(matches(isDisplayed()));
        onView(withId(R.string.field_subject)).check(matches(isDisplayed()));
        onView(withId(R.string.field_subject)).check(matches(withSpinnerText(containsString("Physik"))));
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.logout)).perform(click());
    }

    @Test
    public void changeStatusTest() {
        onView(withId(R.id.usernameTextInput)).perform(typeText("matze@matze.com"));
        onView(withId(R.id.usernameTextInput)).check(matches(withText("matze@matze.com")));
        onView(withId(R.id.password)).perform(typeText("passwort"));
        onView(withId(R.id.password)).check(matches(withText("passwort")));
        onView(withId(R.id.login)).perform(click());
        // wait for server to authenticate user
        onView(isRoot()).perform(TestHelper.waitId(3000));
        // potentially fails when server answer comes to slow
        onView(withId(R.id.goEat)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.string.field_gender)).check(matches(isDisplayed()));
        onView(withId(R.string.field_status)).check(matches(isDisplayed()));
        onView(withId(R.string.field_subject)).check(matches(isDisplayed()));
        onView(withId(R.string.field_status)).perform(click());
        onView(allOf(withText("Student*in"))).perform(click());
        onView(withId(R.string.field_status)).check(matches(withSpinnerText(containsString("Student*in"))));
        onView(withId(R.id.navigation_next)).perform(click());
        onView(withId(R.id.goEat)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.string.field_gender)).check(matches(isDisplayed()));
        onView(withId(R.string.field_status)).check(matches(isDisplayed()));
        onView(withId(R.string.field_subject)).check(matches(isDisplayed()));
        onView(withId(R.string.field_status)).check(matches(withSpinnerText(containsString("Student*in"))));
        onView(withId(R.string.field_status)).perform(click());
        onView(allOf(withText("anderer"))).perform(click());
        onView(withId(R.string.field_status)).check(matches(withSpinnerText(containsString("anderer"))));
        onView(isRoot()).perform(TestHelper.waitId(300));
        onView(withId(R.id.navigation_next)).perform(click());
        onView(withId(R.id.goEat)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.string.field_gender)).check(matches(isDisplayed()));
        onView(withId(R.string.field_status)).check(matches(isDisplayed()));
        onView(withId(R.string.field_subject)).check(matches(isDisplayed()));
        onView(withId(R.string.field_status)).check(matches(withSpinnerText(containsString("anderer"))));
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.logout)).perform(click());
    }
}
