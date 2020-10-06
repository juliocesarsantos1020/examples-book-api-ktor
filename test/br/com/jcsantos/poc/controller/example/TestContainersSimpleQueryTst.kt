package com.jcsantos.br.com.jcsantos.poc.controller.example

import org.junit.jupiter.api.Test
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.sql.DriverManager
import kotlin.test.assertEquals

@Testcontainers
class TestContainersSimpleQueryTst {

    @Container
    private val container = PostgreSQLContainer<Nothing>("postgres:11")
    
    @Test
    fun `should perform simple query`() {
        DriverManager.getConnection(container.jdbcUrl, container.username, container.password).use { conn ->
            conn.createStatement().use {
                stmt ->
                stmt.executeQuery("SELECT 1").use {
                    it.next()
                    
                    assertEquals(it.getInt(1), 1)
                }
            }
        }
    }

}