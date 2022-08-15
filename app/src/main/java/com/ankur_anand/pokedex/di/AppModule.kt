package com.ankur_anand.pokedex.di

import com.ankur_anand.pokedex.BuildConfig
import com.ankur_anand.pokedex.data.remote.ApiService
import com.ankur_anand.pokedex.utils.Constants.BASE_URL
import com.ankur_anand.pokedex.utils.Constants.REQUEST_TIMEOUT_DURATION
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideLogger(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }

    @Provides
    @Singleton
    fun provideClient(logging: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(logging)
            }
            connectTimeout(REQUEST_TIMEOUT_DURATION, TimeUnit.SECONDS)
            readTimeout(REQUEST_TIMEOUT_DURATION, TimeUnit.SECONDS)
            writeTimeout(REQUEST_TIMEOUT_DURATION, TimeUnit.SECONDS)
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}