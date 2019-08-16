package id.kotlin.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkProvider {

  fun providesHttpAdapter(): Retrofit =
    Retrofit.Builder().apply {
      client(providesHttpClient())
      baseUrl(BuildConfig.URL)
      addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      addConverterFactory(GsonConverterFactory.create())
    }.build()

  fun providesMockAdapter(): Retrofit =
    Retrofit.Builder().apply {
      client(providesHttpClient())
      baseUrl(BuildConfig.MOCK)
      addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      addConverterFactory(GsonConverterFactory.create())
    }.build()

  private fun providesHttpClient(): OkHttpClient =
    OkHttpClient.Builder().apply {
      retryOnConnectionFailure(true)
      addInterceptor(providesHttpLoggingInterceptor())
    }.build()

  private fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
      level = when (BuildConfig.DEBUG) {
        true -> Level.BODY
        false -> Level.NONE
      }
    }
}