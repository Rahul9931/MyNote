package com.rahul.mynotes.api

import com.rahul.mynotes.model.UserRequest
import com.rahul.mynotes.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST(ENDPOINT_SIGN_UP)
    suspend fun signUp(@Body userRequest: UserRequest) : Response<UserResponse>

    @POST(ENDPOINT_SIGN_IN)
    suspend fun signIn(@Body userRequest: UserRequest): Response<UserResponse>
}

//

const val ENDPOINT_SIGN_UP = "user/signup"
const val ENDPOINT_SIGN_IN = "user/signin"