package com.grabieckacper.e_commerce.network

import com.grabieckacper.e_commerce.datastore.DataStoreKey
import com.grabieckacper.e_commerce.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(dataStoreRepository: DataStoreRepository): HttpClient {
        return HttpClient(engineFactory = CIO) {
            defaultRequest {
                contentType(type = ContentType.Application.Json)
                url(urlString = "http://10.0.2.2:8080/api/")
            }
            expectSuccess = true
            install(plugin = Auth) {
                bearer {
                    loadTokens {
                        val accessToken = dataStoreRepository.read(
                            key = DataStoreKey.accessToken,
                            defaultValue = ""
                        ).first()

                        if (accessToken.isBlank()) return@loadTokens null

                        BearerTokens(accessToken = accessToken, refreshToken = null)
                    }
                }
            }
            install(plugin = ContentNegotiation) {
                json()
            }
        }
    }
}
