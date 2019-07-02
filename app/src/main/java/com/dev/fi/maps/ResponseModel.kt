package com.dev.fi.maps

/**
 ****************************************
created by -manca-
.::manca.fi@gmail.com ::.
 ****************************************
 */

class ResponseModel{
    data class RoutesResponse(
            var geocoded_waypoints: List<Geocoded_Waypoint?>? = null,
            var routes: List<Route?>? = null,
            var status: String? = "")

    data class Geocoded_Waypoint(
            var geocoder_Status: String? = "",
            var place_Id: String? = "",
            var types: List<String?>? = null
    )

    data class Route(
            var bounds: Bounds,
            var copyrights: String? = "",
            var legs: List<Legs?>? = null,
            var overview_polyline: Polyline? = null,
            var summary: String? = ""

    )

    data class Bounds(
            var northeast: Northeast,
            var  southwest: Southwest )
    data class Northeast(
            var lat: Double?,
            var lng: Double? )
    data class Southwest(
            var lat: Double?,
            var lng: Double? )

    data class Legs(
            var distance: Distance? = null,
            var duration: Duration? = null,
            var end_address: String? = "",
            var end_location: End_Location? = null,
            var start_address: String? = "",
            var start_location: Start_Location? = null,
            var steps: List<Step?>? = null
    )
    data class Distance(
            var text: String? = "",
            var value: Int? = 0
    )
    data class Duration(
            var text: String? = "",
            var value: Int? = 0
    )
    data class End_Location(
            var lat: Double?,
            var lng: Double?
    )
    data class Start_Location(
            var lat: Double?,
            var lng: Double?
    )
    data class Polyline(var points: String? = "")
    data class Step(
            var distance: Distance? = null,
            var duration: Duration? = null,
            var end_location: End_Location? = null,
            var html_instructions: String? = "",
            var polyline: Polyline? = null,
            var start_location: Start_Location?,
            var travel_mode: String? = ""
    )



}