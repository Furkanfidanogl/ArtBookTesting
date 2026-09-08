package com.furkanfidanoglu.artbooktesting.di

import com.furkanfidanoglu.artbooktesting.remote.ImageAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.furkanfidanoglu.artbooktesting.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.pexels.com/v1/"

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideImageApi(retrofit: Retrofit): ImageAPI {
        return retrofit.create(ImageAPI::class.java)
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", BuildConfig.PEXELS_API_KEY)
                    .build()

                chain.proceed(request)
            }
            .build()
    }
}