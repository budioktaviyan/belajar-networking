package id.kotlin.networking

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Datasource {

  @GET("/3/discover/movie")
  fun discoverMovie(
    @Query("api_key") apiKey: String
  ): Single<Response>
}

interface MockDatasource {

  @GET("/contacts")
  fun getContacts(): Single<List<MockResponse>>
}