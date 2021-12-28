package com.example.catlife

import android.content.res.Resources
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.catlife.view.CatFactActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test

class CatFactActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(CatFactActivity::class.java)

    val mockWebServer = MockWebServer()

    private val resources: Resources
        get() = activityRule.activity.resources

    val fact = "Wikipedia has a recording of a cat meowing, because why not?"

    val json = "[{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e008800aac31001185ed07\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"$fact\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-06T21:20:03.505Z\",\"deleted\":false,\"used\":false}]"

    @Test
    fun initialState(){
        onView(withId(R.id.loading_progress_bar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.err_msg)).check(matches(not(isDisplayed())))
        onView(withId(R.id.cat_info)).check(matches(withText(resources.getString(R.string.cat_info))))
        onView(withId(R.id.cat_get_btn)).check(matches(isEnabled()))
    }

//    @Test
//    fun catGetBtnPressed_factLoadedSuccessfully(){
//        // mock web response
//        val mockResponse = MockResponse()
//        mockResponse.setBody(json)
//        mockResponse.setResponseCode(200)
//        mockWebServer.enqueue(mockResponse)
//        mockWebServer.start(8500)
//        // Android doesn't communicate with localhost directly, so we should define in xml folder network_security_config file
//        onView(withId(R.id.loading_progress_bar)).check(matches(not(isDisplayed())))
//        onView(withId(R.id.err_msg)).check(matches(not(isDisplayed())))
//        onView(withId(R.id.cat_get_btn)).check(matches(withText(resources.getString(R.string.get_info))))
//        onView(withId(R.id.cat_get_btn)).check(matches(isEnabled()))
//        // preform click to a button
//        onView(withId(R.id.cat_get_btn)).perform(click())
//        // check if cat_info text has been changed to text from json
//        onView(withId(R.id.cat_info)).check(matches(withText(fact)))
//
//    }
}