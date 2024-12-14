package com.example.mymovieapp.presentation.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mymovieapp.R
import com.example.mymovieapp.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MoviesMainFragmentTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun verifyUiComponentsAreDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)

        // Check that all key UI components are displayed
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.tab_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.movies_rv)).check(matches(isDisplayed()))
        onView(withId(R.id.swipe_refresh_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun verifySearchHidesTabLayout() {
        ActivityScenario.launch(MainActivity::class.java)

        // Type text into the search bar
        onView(withId(R.id.search_bar)).perform(typeText("venom"), closeSoftKeyboard())

        // Check that the tab layout is hidden and RecyclerView is visible
        onView(withId(R.id.tab_layout)).check(matches(not(isDisplayed())))
        onView(withId(R.id.movies_rv)).check(matches(isDisplayed()))
    }

}