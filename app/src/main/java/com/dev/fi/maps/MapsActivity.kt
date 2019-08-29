package com.dev.fi.maps

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.beust.klaxon.Klaxon
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle
import com.orhanobut.logger.Logger
import org.json.JSONArray
import org.json.JSONObject


class MapsActivity : AppCompatActivity(),
        OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener,
        MapsView {

    private val listLatLng = mutableListOf<LatLng>()
    private lateinit var geoJson : GeoJson

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

        geoJson = Klaxon().parse<GeoJson>(resources.openRawResource(R.raw.pamasuka_subs))!!
        Logger.d(geoJson)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val geoJson = GeoJsonLayer(googleMap, R.raw.pamasuka_subs, this)
        val polygonStyle = geoJson.defaultPolygonStyle
        polygonStyle.fillColor = Color.RED


        geoJson.addLayerToMap()

        geoJson.setOnFeatureClickListener{
            Logger.d(it)
        }
    }
}
