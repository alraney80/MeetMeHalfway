package com.example.meetmehalfway;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LandingPageTest {

    @Rule
    public ActivityTestRule<LandingPage> mActivityTestRule = new ActivityTestRule<>(LandingPage.class);

    @Test
    public void landingPageTest() {
        ViewInteraction editText = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address1),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                2)),
                                1),
                        isDisplayed()));
        editText.check(matches(withText("8176 county road 2419 royse city tx 75189")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address1),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                2)),
                                1),
                        isDisplayed()));
        editText2.check(matches(withText("8176 county road 2419 royse city tx 75189")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address2),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                3)),
                                1),
                        isDisplayed()));
        editText3.check(matches(withText("455 Kinney dr keller tx ")));

        ViewInteraction button = onView(
                allOf(withId(R.id.enterButton),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.enterButton),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
