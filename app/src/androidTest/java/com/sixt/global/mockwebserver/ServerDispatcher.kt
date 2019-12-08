package com.sixt.global.mockwebserver

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class ServerDispatcher : Dispatcher() {

    private val responseMap = mutableMapOf<String, MutableList<MockResponse>>()

    fun addResponse(
        urlPath: String,
        response: MockResponse
    ) {
        val path = "/$urlPath"

        if (path !in responseMap) responseMap[path] = mutableListOf()

        responseMap[path]?.add(response)
    }

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path

        val responses = when {
            responseMap[path] != null -> responseMap[path]
            else -> return MockResponse().apply { setResponseCode(NOT_FOUND) }
        }

        if (responses?.size == 1) responseMap.remove(path)

        return responses?.removeAt(0) ?: MockResponse().apply { setResponseCode(NOT_FOUND) }
    }

    fun finish() {
        responseMap.clear()
    }

    companion object {
        private const val NOT_FOUND = 404
    }
}