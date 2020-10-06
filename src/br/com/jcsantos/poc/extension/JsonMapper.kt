package com.jcsantos.br.com.jcsantos.poc.extension

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object JsonMapper {
    
    val defaultMapper: ObjectMapper = jacksonObjectMapper()
    
    init {
        defaultMapper.configure(SerializationFeature.INDENT_OUTPUT, true)
        defaultMapper.registerModule(JavaTimeModule())
    }
}

fun <T> T.objectToJson(): String =
    JsonMapper.defaultMapper.writeValueAsString(this)