package com.jcsantos.br.com.jcsantos.poc.util

val sizeSchemaMap = mapOf(
    "type" to "number",
    "minimum" to 0
)

fun rectangleSchemaMap(refBase: String) = mapOf(
    "type" to "object",
    "properties" to mapOf(
        "a" to mapOf("${'$'}ref" to "$refBase/size"),
        "b" to mapOf("${'$'}ref" to "$refBase/size")
    )
)

val petIdSchema = mapOf(
    "type" to "string",
    "format" to "date",
    "description" to "The identifier of the pet to be accessed"
)

const val petUuid = "petUuid"