package id.kotlin.networking

import com.google.gson.annotations.SerializedName

data class Response(val results: List<Result>)

data class Result(
  val title: String,
  val overview: String
)

data class MockResponse(
  @SerializedName("first_name") val firstName: String,
  @SerializedName("last_name") val lastName: String
)