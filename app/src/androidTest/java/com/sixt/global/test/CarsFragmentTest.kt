package com.sixt.global.test

import androidx.annotation.IdRes
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.sixt.global.cars.view.CarsFragment
import com.sixt.global.mockwebserver.ResponseMocker
import com.sixt.global.mockwebserver.ServerTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class CarsFragmentTest {

    @get:Rule
    val serverTestRule = ServerTestRule()

    private var idlingResource: IdlingResource? = null

    @Test
    fun whenCarsFragmentOpensShouldDisplayMapAndList() {
        setUpSuccessResponse()

        launchFragmentInContainer<CarsFragment>(themeResId = com.sixt.global.R.style.AppTheme)
            .setUpIdlingResources()
        viewWithText(com.sixt.global.R.id.tv_cars_quantity, "")
        viewIsVisible(com.sixt.global.R.id.iv_sixt_logo)

    }

    private fun viewIsVisible(@IdRes id: Int) {
        onView(withId(id)).check(matches(isDisplayed()))
    }

    private fun viewWithText(@IdRes id: Int, text: String) {
        onView(withId(id)).check(matches(withText(text)))
    }

    private fun setUpSuccessResponse() {
        val mocker = ResponseMocker(serverTestRule)
        mocker.mockResponse("cars", jsonResponse)
    }

    private fun setUpErrorResponse() {
        val mocker = ResponseMocker(serverTestRule)
        mocker.mockErrorResponse("cars")
    }

    private fun FragmentScenario<CarsFragment>.setUpIdlingResources() {
        onFragment { fragment ->
            idlingResource = fragment.getIdlingResource()
            IdlingRegistry.getInstance().register(idlingResource)
//            fragment.onMapReady(getFakeGoogleMap())
        }
    }

    companion object {
        const val jsonResponse = "mocks/success_response.json"
    }
}