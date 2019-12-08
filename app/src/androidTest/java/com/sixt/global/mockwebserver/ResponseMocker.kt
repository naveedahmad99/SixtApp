package com.sixt.global.mockwebserver

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.MockResponse

class ResponseMocker(
    private val serverTestRule: ServerTestRule? = null,
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
) {

    fun mockResponse(
        urlPath: String,
        jsonResponse: String
    ) {
        val mockResponse = MockResponse().apply {
            setResponseCode(STATUS_CODE_SUCCESS)
            setBody(context.resolveJsonPath(jsonResponse))
        }

        serverTestRule?.addResponse(urlPath, mockResponse)
    }

    fun mockErrorResponse(
        urlPath: String
    ) {
        val mockResponse = MockResponse().apply {
            setResponseCode(STATUS_CODE_ERROR)
            setBody("{}")
        }
        serverTestRule?.addResponse(urlPath, mockResponse)
    }

    private fun Context.resolveJsonPath(path: String): String {
        val uri = assets.open(path)
        return String(uri.readBytes())
    }

    companion object {
        const val STATUS_CODE_SUCCESS = 200
        const val STATUS_CODE_ERROR = 500
    }
}