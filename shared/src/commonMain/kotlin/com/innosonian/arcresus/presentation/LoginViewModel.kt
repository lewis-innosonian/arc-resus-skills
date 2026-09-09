package com.innosonian.arcresus.presentation

import androidx.lifecycle.ViewModel
import com.innosonian.arcresus.data.remote.model.LoginResponse
import com.innosonian.arcresus.data.repository.AuthRepository

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    suspend fun login(id: String, pw: String): Result<LoginResponse> {
        return authRepository.login(id, pw)
    }
}