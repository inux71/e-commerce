package com.grabieckacper.e_commerce.service

import com.grabieckacper.e_commerce.model.Token
import com.grabieckacper.e_commerce.network.request.LoginRequest
import com.grabieckacper.e_commerce.network.request.RegisterRequest

interface AuthService {
    suspend fun refresh(): Token
    suspend fun signIn(loginRequest: LoginRequest): Token
    suspend fun signUp(registerRequest: RegisterRequest)
}
