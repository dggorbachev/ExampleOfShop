package com.dev.vapeshop.domain.model

data class Device(
    val battery: String = "",
    val capacity: Long = 0,
    val charger: String = "",
    val count: Long = 0,
    val dimensions: String = "",
    val display: String = "",
    val logo: String = "",
    val name: String = "",
    val power: String = "",
    val price: Long,
)
