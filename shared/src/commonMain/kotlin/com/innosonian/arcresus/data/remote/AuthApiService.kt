package com.innosonian.arcresus.data.remote

import com.innosonian.arcresus.data.remote.ApiEndpoints.LOGIN
import com.innosonian.arcresus.data.remote.model.LoginRequest
import com.innosonian.arcresus.data.remote.model.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApiService(private val client: HttpClient) {
    suspend fun login(request: LoginRequest): LoginResponse {
        return client.post(LOGIN) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }


}