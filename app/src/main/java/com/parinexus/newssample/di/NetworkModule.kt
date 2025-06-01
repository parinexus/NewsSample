package com.parinexus.newssample.di

import android.content.Context
import com.parinexus.data.BuildConfig
import com.parinexus.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://newsdata.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiKey():String{
        return BuildConfig.API_KEY
    }


    @Provides
    @Singleton
    fun provideContext(application: android.app.Application): Context {
        return application.applicationContext
    }
}