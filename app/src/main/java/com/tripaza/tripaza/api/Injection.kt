package com.tripaza.tripaza.api

import android.content.Context
import com.tripaza.tripaza.api.repositories.UserRepository

object Injection {
    
    fun provideUserRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        val userRepository = UserRepository()
        userRepository.setApiService(apiService)
        return userRepository
    }
    
}