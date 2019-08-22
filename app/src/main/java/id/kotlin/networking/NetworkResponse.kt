package id.kotlin.networking

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(val results: List<Result>)

@Serializable
data class Result(
  val title: String,
  val overview: String,
  @SerialName("poster_path") val posterPath: String
)

@Serializable
data class MockResponse(
  @SerialName("first_name") val firstName: String,
  @SerialName("last_name") val lastName: String
)