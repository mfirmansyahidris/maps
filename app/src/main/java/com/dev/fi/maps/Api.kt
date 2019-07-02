package com.dev.fi.maps

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*
import java.util.*

/**
 ****************************************
created by -manca-
.::manca.fi@gmail.com ::.
 ****************************************
 */

interface Api {
    @GET("json")
    fun getRoutes(@QueryMap headers: LinkedHashMap<String, String?>): Observable<Response<ResponseModel.RoutesResponse>>
}