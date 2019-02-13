package com.dev.fi.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class MapsActivity : AppCompatActivity(),
        OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener,
        MapsView{

    override fun onProcess() {

    }

    override fun getResult(response: RoutesResponse) {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun getError(string: String?) {

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

        //TODO: get longlat from json response

        val polyline1 = googleMap.addPolyline(PolylineOptions()
                .clickable(true)
                .add(
                        LatLng(-35.016, 143.321),
                        LatLng(-34.747, 145.592),
                        LatLng(-34.364, 147.891),
                        LatLng(-33.501, 150.217),
                        LatLng(-32.306, 149.248),
                        LatLng(-32.491, 147.309)))

        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-23.684, 133.903), 4f))

        // Set listeners for click events.
        googleMap.setOnPolylineClickListener(this)
        googleMap.setOnPolygonClickListener(this)
    }
}
