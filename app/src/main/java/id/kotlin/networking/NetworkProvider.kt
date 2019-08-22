package id.kotlin.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object NetworkProvider {

  @UnstableDefault
  fun providesHttpAdapter(): Retrofit =
    Retrofit.Builder().apply {
      client(providesHttpClient())
      baseUrl(BuildConfig.URL)
      addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      addConverterFactory(Json.nonstrict.asConverterFactory(MediaType.get("application/json")))
    }.build()

  @UnstableDefault
  fun providesMockAdapter(): Retrofit =
    Retrofit.Builder().apply {
      client(providesHttpClient())
      baseUrl(BuildConfig.MOCK)
      addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      addConverterFactory(Json.nonstrict.asConverterFactory(MediaType.get("application/json")))
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