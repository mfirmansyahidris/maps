package com.dev.fi.maps

/**
 ****************************************
created by -manca-
.::manca.fi@gmail.com ::.
 ****************************************
 */

interface MapsView{
    fun onProcess()
    fun getResult(response: ResponseModel.RoutesResponse)
    fun getError(string: String?)
}