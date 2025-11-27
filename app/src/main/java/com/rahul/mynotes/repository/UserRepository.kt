package com.rahul.mynotes.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.rahul.mynotes.api.UserApi
import com.rahul.mynotes.model.UserRequest
import com.rahul.mynotes.model.UserResponse
import com.rahul.mynotes.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLiveData

    suspend fun registerUser(userRequest: UserRequest){
        try {
            _userResponseLiveData.postValue(NetworkResult.Loading())
            var response = userApi.signUp(userRequest)
            Log.d("check_", "signup api res 1 -> ${response.body()}")
            if (response.isSuccessful && response.body() != null){
                _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
            }
            else if(response.errorBody() != null){
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
            }
            else{
                _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
            }
        }
        catch (e: Exception){
            _userResponseLiveData.postValue(NetworkResult.Error(e.localizedMessage))
        }

    }
}