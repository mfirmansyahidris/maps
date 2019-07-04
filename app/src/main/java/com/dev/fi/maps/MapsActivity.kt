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
import com.orhanobut.logger.Logger
import org.json.JSONArray
import org.json.JSONObject


class MapsActivity : AppCompatActivity(),
        OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener,
        MapsView {

    private val listLatLng = mutableListOf<LatLng>()
    private lateinit var geoJson: GeoJson

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
/*        val coordinates = mutableListOf<LatLng>()
        var print = ""
        for (j in 0 until geoJson.features!![0]?.geometry?.coordinates?.get(0)!!.size){
            print += ".add(LatLng(${geoJson.features!![0]?.geometry?.coordinates?.get(0)?.get(j)?.get(0)!!}, ${geoJson.features!![0]?.geometry?.coordinates?.get(0)?.get(j)?.get(1)!!} )) "
            coordinates.add(LatLng(
                    geoJson.features!![0]?.geometry?.coordinates?.get(0)?.get(j)?.get(0)!!,
                    geoJson.features!![0]?.geometry?.coordinates?.get(0)?.get(j)?.get(1)!!))
        }

        Logger.d(print)*/

        googleMap.addPolygon(PolygonOptions()
                .add(LatLng(109.176212, 0.964821)).add(LatLng(109.130251, 1.01836)).add(LatLng(109.062745, 0.989571)).add(LatLng(108.978814, 0.989161)).add(LatLng(108.961953, 1.065242)).add(LatLng(108.90221, 1.16453)).add(LatLng(108.964909, 1.218305)).add(LatLng(109.026877, 1.316155)).add(LatLng(109.03694, 1.448689)).add(LatLng(109.060368, 1.525597)).add(LatLng(109.128865, 1.586536)).add(LatLng(109.220503, 1.632709)).add(LatLng(109.286066, 1.725692)).add(LatLng(109.293026, 1.78427)).add(LatLng(109.33651, 1.830244)).add(LatLng(109.338589, 1.941906)).add(LatLng(109.456122, 1.977683)).add(LatLng(109.56357, 1.97344)).add(LatLng(109.539825, 1.906177)).add(LatLng(109.586241, 1.791161)).add(LatLng(109.683001, 1.784846)).add(LatLng(109.660655, 1.619312)).add(LatLng(109.749077, 1.533559)).add(LatLng(109.717798, 1.475277)).add(LatLng(109.601381, 1.404734)).add(LatLng(109.62643, 1.331901)).add(LatLng(109.588975, 1.201994)).add(LatLng(109.537354, 1.193725)).add(LatLng(109.469212, 1.020334)).add(LatLng(109.422227, 1.051269)).add(LatLng(109.249993, 1.034683)).add(LatLng(109.176212, 0.964821))
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE))
        /*for (i in 0 until geoJson.features!!.size){
            val coordinates = mutableListOf<LatLng>()
            for (j in 0 until geoJson.features!![i]?.geometry?.coordinates?.get(0)!!.size){
                Logger.d(geoJson.features!![i]?.geometry?.coordinates?.get(0)?.get(j)?.get(0)!!)
                Logger.d(geoJson.features!![i]?.geometry?.coordinates?.get(0)?.get(j)?.get(1)!!)
                coordinates.add(LatLng(
                        geoJson.features!![i]?.geometry?.coordinates?.get(0)?.get(j)?.get(0)!!,
                        geoJson.features!![i]?.geometry?.coordinates?.get(0)?.get(j)?.get(1)!!))
            }

            googleMap.addPolygon(PolygonOptions()
                    .addAll(coordinates)
                    .strokeColor(Color.RED)
                    .fillColor(Color.BLUE))
        }*/

    }
}
