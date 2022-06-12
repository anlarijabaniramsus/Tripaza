package com.tripaza.tripaza.ui.navigation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripaza.tripaza.api.Injection
import com.tripaza.tripaza.databases.dataobject.User

class ProfileViewModel : ViewModel(){
    private var userRepository =  Injection.provideUserRepository()
    
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    
    fun getUserProfileData(token: String) = userRepository.getUserProfileData(token)
    
    fun setUser(user: User) {
        _user.value = user
    }
    
    fun updateUserProfileData(
        token: Int,
        full_name: String,
        birth_date: String,
        phone_number: String,
        email: String,
        password: String
    ) = userRepository.updateUserProfileData(
        token, full_name, birth_date, phone_number, email, password
    )
    
}