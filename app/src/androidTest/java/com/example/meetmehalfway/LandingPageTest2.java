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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LandingPageTest2 {

    @Rule
    public ActivityTestRule<LandingPage> mActivityTestRule = new ActivityTestRule<>(LandingPage.class);

    @Test
    public void landingPageTest2() {
        ViewInteraction editText = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address1),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                2)),
                                1),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address1),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                2)),
                                1),
                        isDisplayed()));
        editText2.check(matches(withText("306 W University Dr, Denton, Tx 76201")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address2),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                3)),
                                1),
                        isDisplayed()));
        editText3.check(matches(withText("306 W University Dr, Denton, Tx 76201")));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address2),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                3)),
                                1),
                        isDisplayed()));
        editText4.check(matches(isDisplayed()));

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address2),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                3)),
                                1),
                        isDisplayed()));
        editText5.check(matches(withText("9125 Parson Dr, Lantana")));

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address1),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                2)),
                                1),
                        isDisplayed()));
        editText6.check(matches(withText("9125 Parson Dr, Lantana")));

        ViewInteraction editText7 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address1),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                2)),
                                1),
                        isDisplayed()));
        editText7.check(matches(withText("2625 north evergreen, pampas, tx")));

        ViewInteraction editText8 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address2),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                3)),
                                1),
                        isDisplayed()));
        editText8.check(matches(withText("2625 north evergreen, pampas, tax")));

        ViewInteraction editText9 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address1),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                2)),
                                1),
                        isDisplayed()));
        editText9.check(matches(withText("1500 N Corinth St, Corinth, TX 76208")));

        ViewInteraction editText10 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address2),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                3)),
                                1),
                        isDisplayed()));
        editText10.check(matches(withText("1500 N Corinth St, Corinth, TX 76208")));

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
        button2.check(doesNotExist());

        ViewInteraction editText11 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address1),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                2)),
                                1),
                        isDisplayed()));
        editText11.check(matches(withText("1989 colonial pkwy, Fort Worth, Tx 76110")));

        ViewInteraction editText12 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address2),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                3)),
                                1),
                        isDisplayed()));
        editText12.check(matches(withText("1989 colonial pkwy, Fort Worth, Tx 76110")));

        ViewInteraction editText13 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address2),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                3)),
                                1),
                        isDisplayed()));
        editText13.check(matches(withText("11761 bobcat dr keller tx 76244")));

        ViewInteraction editText14 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address1),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                2)),
                                1),
                        isDisplayed()));
        editText14.check(matches(withText("11761 bobcat dr keller tx 76244")));

        ViewInteraction editText15 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address1),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                2)),
                                1),
                        isDisplayed()));
        editText15.check(matches(withText("2021 Teton tr, Lewisville, tx")));

        ViewInteraction editText16 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address2),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                3)),
                                1),
                        isDisplayed()));
        editText16.check(matches(withText("2021 Teton tr, Lewisville, tx")));

        ViewInteraction editText17 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address2),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                3)),
                                1),
                        isDisplayed()));
        editText17.check(matches(withText("25402 Katy Mills Pkwy, Katy, TX 77494")));

        ViewInteraction editText18 = onView(
                allOf(withId(R.id.places_autocomplete_search_input), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_frag_address1),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                2)),
                                1),
                        isDisplayed()));
        editText18.check(matches(withText("25402 Katy Mills Pkwy, Katy, TX 77494")));
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
