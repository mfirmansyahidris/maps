package com.dev.fi.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.orhanobut.logger.Logger
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds




class MapsActivity : AppCompatActivity(),
        OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener,
        MapsView{

    val  listLatLng = mutableListOf<LatLng>()

    override fun onProcess() {
        //do process
    }

    override fun getResult(response: ResponseModel.RoutesResponse) {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        for (i in 0 ..(response.routes?.get(0)?.legs?.get(0)?.steps?.size!! - 1)){
            var lag = response.routes?.get(0)?.legs?.get(0)?.steps?.get(i)?.start_location?.lng!!
            var lat = response.routes?.get(0)?.legs?.get(0)?.steps?.get(i)?.start_location?.lat!!
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

        var hashmap = LinkedHashMap<String, String?>()
        hashmap["origin"] = "-5.102606,119.528874"
        hashmap["destination"] = "-5.171547,119.432462"
        hashmap["avoid"] = "highways"
        hashmap["mode"] = "driving"
        hashmap["key"] = "AIzaSyCB3G3WCb_gH-4gfXaPu7yKcAP1RbBbM0k"

        presenter.getRoutes(hashmap)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        /*mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        // Add polylines and polygons to the map. This section shows just
        // a single polyline. Read the rest of the tutorial to learn more.


        val polyline1 = googleMap.addPolyline(PolylineOptions()
                .clickable(true)
                .addAll(listLatLng))

        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.

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

        if (polyline?.points != null) {
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
        }else{
            null
        }
    }
}
