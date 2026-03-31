package io.mmaltsev.vkeducation.data.appdetails

import io.mmaltsev.vkeducation.data.applist.AppSummaryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface AppApi {
    @GET("catalog")
    suspend fun getAppList(): List<AppSummaryDto>

    @GET("catalog/{id}")
    suspend fun getAppDetails(@Path("id") id: String): AppDetailsDto
}