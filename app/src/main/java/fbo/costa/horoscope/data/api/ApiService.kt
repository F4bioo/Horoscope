package fbo.costa.horoscope.data.api

import fbo.costa.horoscope.data.model.SignApiEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/signo/{signo}/dia")
    suspend fun getSign(
        @Path("signo") sign: String
    ): Response<SignApiEntity>
}
