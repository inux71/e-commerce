package com.grabieckacper.e_commerce.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    @SerialName(value = "firstName")
    val firstname: String,
    @SerialName(value = "lastName")
    val lastname: String
)
