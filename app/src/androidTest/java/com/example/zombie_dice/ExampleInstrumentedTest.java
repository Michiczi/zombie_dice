package com.example.zombie_dice;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.zombie_dice.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href=\"http://d.android.com/tools/testing\">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.zombie_dice", appContext.getPackageName());
    }

    @Test
    public void testGameFlow() {
        // Start screen: click new game
        onView(withId(R.id.new_game_button)).perform(click());

        // Pre-game screen: enter player count and start
        onView(withId(R.id.number_of_players_edittext)).perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.start_game_button)).perform(click());

        // Game screen: check if player info is visible
        onView(withId(R.id.player_info_textview)).check(matches(isDisplayed()));

        // Roll dice
        onView(withId(R.id.roll_button)).perform(click());

        // Wait for animation
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // End turn
        onView(withId(R.id.end_turn_button)).perform(click());

        // Check for player 2
        onView(withId(R.id.player_info_textview)).check(matches(withText(containsString("Player 2"))));
    }

    @Test
    public void testRulesScreen() {
        onView(withId(R.id.rules_button)).perform(click());
        onView(withId(R.id.rules_text_view)).check(matches(isDisplayed()));
    }
}