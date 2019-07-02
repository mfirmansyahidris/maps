package com.dev.fi.maps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class MapsActivity : AppCompatActivity(),
        OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener,
        MapsView {

    private val listLatLng = mutableListOf<LatLng>()

    override fun onProcess() {
        //do process
    }

    override fun getResult(response: ResponseModel.RoutesResponse) {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        for (i in 0 until response.routes?.get(0)?.legs?.get(0)?.steps?.size!!) {
            val lag = response.routes?.get(0)?.legs?.get(0)?.steps?.get(i)?.start_location?.lng!!
            val lat = response.routes?.get(0)?.legs?.get(0)?.steps?.get(i)?.start_location?.lat!!
            listLatLng.add(LatLng(lat, lag))
        }

        mapFragment.getMapAsync(this)
    }

    override fun getError(string: String?) {
        // do error
    }

    private lateinit var presenter: MapsPresenter

    override fun onPolylineClick(p0: Polyline?) {}

    override fun onPolygonClick(p0: Polygon?) {}

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        presenter = MapsPresenter(this)

        val hashmap = LinkedHashMap<String, String?>()
        hashmap["origin"] = "-5.102606,119.528874"
        hashmap["destination"] = "-5.171547,119.432462"
        hashmap["avoid"] = "highways"
        hashmap["mode"] = "driving"
        hashmap["key"] = "AIzaSyCB3G3WCb_gH-4gfXaPu7yKcAP1RbBbM0k"

        presenter.getRoutes(hashmap)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val polyline1 = googleMap.addPolyline(PolylineOptions()
                .clickable(true)
                .addAll(listLatLng))

        val builder = setMapCamera(polyline1)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder?.build(), 48))

        // Set listeners for click events.
        googleMap.setOnPolylineClickListener(this)
        googleMap.setOnPolygonClickListener(this)
    }

    private fun setMapCamera(polyline: Polyline): LatLngBounds.Builder? {
        var hasPoints = false
        var maxLat: Double? = null
        var minLat: Double? = null
        var minLon: Double? = null
        var maxLon: Double? = null

        if (polyline.points != null) {
            val pts = polyline.points
            for (coordinate in pts) {
                // Find out the maximum and minimum latitudes & longitudes
                // Latitude
                maxLat = if (maxLat != null) Math.max(coordinate.latitude, maxLat) else coordinate.latitude
                minLat = if (minLat != null) Math.min(coordinate.latitude, minLat) else coordinate.latitude

                // Longitude
                maxLon = if (maxLon != null) Math.max(coordinate.longitude, maxLon) else coordinate.longitude
                minLon = if (minLon != null) Math.min(coordinate.longitude, minLon) else coordinate.longitude

                hasPoints = true
            }
        }

        return if (hasPoints) {
            val builder = LatLngBounds.Builder()
            builder.include(LatLng(maxLat!!, maxLon!!))
            builder.include(LatLng(minLat!!, minLon!!))
            builder
        } else {
            null
        }
    }
}
