package com.sixt.global.mockwebserver

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class ServerTestRule : TestRule {

    private val mockWebServer: MockWebServer = MockWebServer()
    private val port = 8080

    private val dispatcher = ServerDispatcher()
        .apply { mockWebServer.dispatcher = this }

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                start()
                try {
                    base?.evaluate()
                } finally {
                    stop()
                }
            }
        }
    }

    private fun start() {
        mockWebServer.start(port)
    }

    private fun stop() {
        dispatcher.finish()
        mockWebServer.shutdown()
    }

    fun addResponse(
        urlPath: String,
        mockResponse: MockResponse
    ) {
        dispatcher.addResponse(urlPath, mockResponse)
    }
}