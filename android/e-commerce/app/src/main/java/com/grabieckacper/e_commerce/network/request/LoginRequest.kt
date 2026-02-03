package com.grabieckacper.e_commerce.network.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val email: String, val password: String)
