package com.innosonian.arcresus.data.repository

import com.innosonian.arcresus.data.remote.AuthApiService
import com.innosonian.arcresus.data.remote.model.LoginRequest
import com.innosonian.arcresus.data.remote.model.LoginResponse

class AuthRepository(private val apiService: AuthApiService) {
    suspend fun login(id: String, pw: String): Result<LoginResponse> {
        return try {
            val request = LoginRequest(login_id = id, password = pw)
            val response = apiService.login(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}