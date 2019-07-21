package com.example.RecklessLabs.TaskManager;

import android.support.v7.widget.RecyclerView;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FrontEndTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test1(){
        //check if task is correctly added
        String taskName = "CSC 179 Project";
        String taskDesc = "Test description for create test case";
        Espresso.onView(withId(R.id.fab)).perform(click());
        Espresso.onView(withId(R.id.taskName_pu)).perform(typeText(taskName));
        Espresso.onView(withId(R.id.description_pu)).perform(typeText(taskDesc));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.submit_button_pu)).perform(click());
        Espresso.onView(withId(R.id.taskName_rv)).check(matches(withText(taskName)));
    }

    @Test
    public void test2() throws Exception{
        Espresso.onView(withId(R.id.start_button_rv)).perform(click());
        Thread.sleep(5000);
        Espresso.onView(withId(R.id.stop_button_rv)).perform(click());
        Espresso.onView(withId(R.id.taskTime_rv)).check(matches(withText("00:05")));
    }

    @Test
    public void test3(){
        String taskName = "New Name";
        String taskDesc = "New description for edit test case";
        Espresso.onView(withId(R.id.edit_button_rv)).perform(click());
        Espresso.onView(withId(R.id.taskName_pu)).perform(clearText());
        Espresso.onView(withId(R.id.description_pu)).perform(clearText());
        Espresso.onView(withId(R.id.taskName_pu)).perform(typeText(taskName));
        Espresso.onView(withId(R.id.description_pu)).perform(typeText(taskDesc));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.submit_button_pu)).perform(click());
        Espresso.onView(withId(R.id.taskName_rv)).check(matches(withText(taskName)));
    }

    @Test
    public void test4(){
        Espresso.onView(withId(R.id.info_button_rv)).perform(click());
    }

    @Test
    public void test5() throws Exception{
        Espresso.onView(withId(R.id.stats_button_rv)).perform(click());
        Thread.sleep(2000);
        Espresso.pressBack();
    }

    @Test
    public void test6(){
        Espresso.onView(withId(R.id.refresh_button_rv)).perform(click());
        Espresso.onView(withId(R.id.taskTime_rv)).check(matches(withText("00:00")));
    }

    @Test
    public void test7(){
        Espresso.onView(withId(R.id.cardView)).perform(swipeLeft());
    }

    @After
    public void tearDown() throws Exception {
    }
}