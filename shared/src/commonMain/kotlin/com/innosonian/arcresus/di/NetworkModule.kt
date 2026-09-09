package com.innosonian.arcresus.di

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import com.innosonian.arcresus.data.remote.AuthApiService
import com.innosonian.arcresus.data.remote.NetworkConfig.BASE_URL
import com.innosonian.arcresus.data.repository.AuthRepository
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging

object NetworkModule {
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }

        install(DefaultRequest) {
            url(BASE_URL)
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println("KtorNetworkLog: $message")
                }
            }
            level = LogLevel.ALL
        }
    }

    val authApiService = AuthApiService(httpClient)
    val authRepository = AuthRepository(authApiService)
}