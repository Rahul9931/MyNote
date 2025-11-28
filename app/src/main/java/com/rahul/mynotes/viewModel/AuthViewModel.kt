package com.rahul.mynotes.viewModel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.mynotes.model.UserRequest
import com.rahul.mynotes.model.UserResponse
import com.rahul.mynotes.repository.UserRepository
import com.rahul.mynotes.utils.Helper
import com.rahul.mynotes.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData

    fun registerUser(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }
    }

    fun loginUser(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }

    fun validateCredential(email: String, password:String, userName: String, isLogin: Boolean): Pair<Boolean, String>{
        var result = Pair(true, "")
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || (!isLogin && TextUtils.isEmpty(userName))){
            result = Pair(false, "Please provide the valid credentials")
        }
        else if(!Helper.isValidEmail(email)){
            result = Pair(false, "Please provide the valid Email Address")
        }
        else if(password.length <= 5 ){
            result = Pair(false, "Password length should be greater then 5")
        }

        return result
    }
}