package ru.ds.weatherfirst.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.ds.weatherfirst.data.api.ApiConstants
import ru.ds.weatherfirst.data.api.WeatherApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WeatherApiModule {

    @Provides
    @Singleton
    fun provideApi(builder:Retrofit.Builder):WeatherApi{
        return builder
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
    }

}