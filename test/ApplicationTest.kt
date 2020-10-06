package com.jcsantos

import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.withTestApplication

/*class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }
}*/

fun testApp(callback: TestApplicationEngine.() -> Unit) {
    withTestApplication({ module(testing = true) }) { callback() }
}