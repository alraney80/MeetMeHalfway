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
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LandingPageTest10_Failure {

    @Rule
    public ActivityTestRule<LandingPage> mActivityTestRule = new ActivityTestRule<>(LandingPage.class);

    @Test
    public void landingPageTest10_Failure() {
        ViewInteraction imageButton = onView(
                allOf(withId(R.id.places_autocomplete_search_button), withContentDescription("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address1),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                2)),
                                0),
                        isDisplayed()));
        imageButton.check(doesNotExist());

        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.places_autocomplete_search_button), withContentDescription("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address2),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                3)),
                                0),
                        isDisplayed()));
        imageButton2.check(doesNotExist());
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
