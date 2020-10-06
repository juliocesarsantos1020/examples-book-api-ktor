package com.jcsantos.br.com.jcsantos.poc.domain

import java.math.BigDecimal
import java.util.UUID

data class Book(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val amount: BigDecimal,
    @Transient
    val version: Int = 1
)