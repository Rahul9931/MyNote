package com.rahul.mynotes.di

import com.rahul.mynotes.api.AuthInterceptor
import com.rahul.mynotes.api.NoteAPI
import com.rahul.mynotes.api.UserApi
import com.rahul.mynotes.utils.ApplicationConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(ApplicationConstant.BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi{
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNoteApi(retrofit: Retrofit): NoteAPI{
        return retrofit.create(NoteAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideOkhttpsClient(authInterceptor: AuthInterceptor, httpLoggingInterceptor: HttpLoggingInterceptor ): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor) // for token
            .addInterceptor(httpLoggingInterceptor)  // for log the api details
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor{
        var interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }
}