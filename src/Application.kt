package com.jcsantos

import com.fasterxml.jackson.annotation.JsonInclude
import com.jcsantos.br.com.jcsantos.poc.api.book
import com.jcsantos.br.com.jcsantos.poc.config.DatabaseMigration
import com.jcsantos.br.com.jcsantos.poc.extension.JsonMapper.defaultMapper
import com.jcsantos.br.com.jcsantos.poc.repository.BookRepository
import com.jcsantos.br.com.jcsantos.poc.service.BookService
import com.jcsantos.br.com.jcsantos.poc.util.petIdSchema
import com.jcsantos.br.com.jcsantos.poc.util.petUuid
import com.jcsantos.br.com.jcsantos.poc.util.rectangleSchemaMap
import com.jcsantos.br.com.jcsantos.poc.util.sizeSchemaMap
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import de.nielsfalk.ktor.swagger.SwaggerSupport
import de.nielsfalk.ktor.swagger.SwaggerUiConfiguration
import de.nielsfalk.ktor.swagger.version.shared.Contact
import de.nielsfalk.ktor.swagger.version.shared.Information
import de.nielsfalk.ktor.swagger.version.v2.Swagger
import de.nielsfalk.ktor.swagger.version.v3.OpenApi
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.jackson.JacksonConverter
import io.ktor.jackson.jackson
import io.ktor.locations.Locations
import io.ktor.routing.Routing
import io.ktor.routing.routing
import org.jetbrains.exposed.sql.Database
import org.slf4j.event.Level
import java.util.Properties
import java.util.UUID

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun initConfig() {
    ConfigFactory.defaultApplication()
}

fun initDatabase(properties: Properties? = null) {
    val dbType = ConfigFactory.load().getString("db_type")
    val config = ConfigFactory.load().getConfig(dbType)
    
    val properties = Properties()
    config.entrySet().forEach { entry ->
        properties.setProperty(entry.key, config.getString(entry.key))
    }
    
    val hikariConfig = HikariConfig(properties)
    val dataSource = HikariDataSource(hikariConfig)
    Database.connect(dataSource)
}

fun migrateDatabase() {
    DatabaseMigration.migrate()
}

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    
    initConfig()
    initDatabase()
    migrateDatabase()
    
    install(ContentNegotiation) {
        jackson {
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
        }
        register(ContentType.Application.Json, JacksonConverter(defaultMapper))
    }
    
    install(Locations)
    install(SwaggerSupport) {
        swaggerInfo()
    }
    
    routing {
        book(BookService(BookRepository()))
    }
    
    
    install(CallLogging) {
        level = Level.TRACE
        mdc("executionID") {
            UUID.randomUUID().toString()
        }
    }
}

private fun SwaggerUiConfiguration.swaggerInfo() {
    forwardRoot = true
    val information = Information(
        version = "1.0",
        title = "API Book with Ktor",
        description = "This is a sample which combines [ktor](https://github.com/Kotlin/ktor) with [swaggerUi](https://swagger.io/). You find the sources on [github](https://github.com/nielsfalk/ktor-swagger)",
        contact = Contact(
            name = "Júlio Santos",
            email = "juliocesarsantos1020@gmail.com",
        )
    )
    swagger = Swagger().apply {
        info = information
        //definitions["size"] = sizeSchemaMap
        //definitions[petUuid] = petIdSchema
        //["Rectangle"] = rectangleSchemaMap("#/definitions")
    }
    openApi = OpenApi().apply {
        info = information
        //components.schemas["size"] = sizeSchemaMap
        //components.schemas[petUuid] = petIdSchema
        //components.schemas["Rectangle"] = rectangleSchemaMap("#/components/schemas")
    }
}