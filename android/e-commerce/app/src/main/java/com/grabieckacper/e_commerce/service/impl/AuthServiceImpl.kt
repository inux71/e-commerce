package com.grabieckacper.e_commerce.service.impl

import com.grabieckacper.e_commerce.model.Token
import com.grabieckacper.e_commerce.network.Url
import com.grabieckacper.e_commerce.network.request.LoginRequest
import com.grabieckacper.e_commerce.network.request.RegisterRequest
import com.grabieckacper.e_commerce.service.AuthService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(private val httpClient: HttpClient) : AuthService {
    override suspend fun refresh(): Token {
        return httpClient.post(urlString = Url.REFRESH).body()
    }

    override suspend fun signIn(loginRequest: LoginRequest): Token {
        return httpClient.post(urlString = Url.SIGN_IN) {
            setBody(body = loginRequest)
        }.body()
    }

    override suspend fun signUp(registerRequest: RegisterRequest) {
        httpClient.post(urlString = Url.SIGN_UP) {
            setBody(body = registerRequest)
        }
    }
}
