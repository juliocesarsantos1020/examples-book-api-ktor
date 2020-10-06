package com.jcsantos.br.com.jcsantos.poc.config

import com.typesafe.config.ConfigFactory
import org.flywaydb.core.Flyway

object DatabaseMigration {
    
    fun migrate() {
        val dbType = ConfigFactory.load().getString("db_type")
        val config = ConfigFactory.load().getConfig(dbType)
        val flyway = Flyway.configure()
            .dataSource(config.getString("dataSource.url"),
            config.getString("dataSource.user"),
            config.getString("dataSource.password"))
            .schemas("public")
            .locations("db/migration")
            .load()
        flyway.migrate()
    }
}