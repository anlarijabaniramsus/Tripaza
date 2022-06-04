package com.tripaza.tripaza.ui.registration

import androidx.lifecycle.ViewModel
import com.tripaza.tripaza.api.Injection

class MainActivityModel: ViewModel() {
    private var userRepository =  Injection.provideUserRepository()
    
    fun login(email: String, password: String) = userRepository.login(email, password)
}