package com.grabieckacper.e_commerce.service

import com.grabieckacper.e_commerce.service.impl.AuthServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(httpClient: HttpClient): AuthService {
        return AuthServiceImpl(httpClient = httpClient)
    }
}
