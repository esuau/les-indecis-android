package com.les_indecis.ing3.esipe.les_indecis_android

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.annotation.IdRes
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.osmdroid.api.IMapController
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.*
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import com.les_indecis.ing3.esipe.les_indecis_android.ParkingSpot
import com.les_indecis.ing3.esipe.les_indecis_android.R.drawable.position
import kotlinx.android.synthetic.main.activity_map.*
import org.json.JSONObject
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("ByteOrderMark")
class MapActivity : AppCompatActivity() {

    private val RECORD_REQUEST_CODE = 101
    private val mapview:MapView = MapView(this);

    override fun onCreate(savedInstanceState: Bundle?) {
        getParkingSpots()
        setupPermissions()
        if(isExternalStorageWritable()) {
            //adds the map on the display
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_map)
            val mapView = bind<MapView>(R.id.mapview)
            mapView.setBuiltInZoomControls(true)
            mapView.setMultiTouchControls(true)
            val imlp = GpsMyLocationProvider(this.baseContext)
            val mapController: IMapController = mapView.controller
            mapController.setZoom(12.1)
            val startPoint:GeoPoint = GeoPoint(48.8583,2.2944)
            mapController.setCenter(startPoint)
        }

    }

    /* Checks if external storage is available for read and write */
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /* Checks if external storage is available to at least read */
    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    fun <T : View> Activity.bind(@IdRes res : Int) : T {
        @Suppress("UNCHECKED_CAST")
        return findViewById<T>(res)
    }

    private fun getParkingSpots(){
        var result2:String=""
        var URL2:String="http://backend.undefined.inside.esiag.info/get_spots"
        var body2:Map<String,String> = emptyMap()
        //post(URL+"/add_msg", body)

        doAsync {
            var response2 = khttp.get(
                    url = URL2,
                    data = body2)
            uiThread {
                var i:Int = 0
                for (i in 0..response2.jsonArray.length()-1) {
                    val test: JSONObject = response2.jsonArray[i] as JSONObject
                    val spot:ParkingSpot = ParkingSpot(
                            test.getLong("id"),
                            test.getDouble("longitude"),
                            test.getDouble("latitude"),
                            test.getInt("capacity"),
                            test.getInt("occupancy"),
                            test.getString("designation"),
                            test.getString("city")
                    )

                    ParkingSpot.listSpots.add(spot)
                }
                setIconsOnMap()
            }
        }
    }

    private fun setIconsOnMap(){
        val icon:Drawable = resources.getDrawable( R.drawable.position )
        val iconList:ArrayList<OverlayItem> = ArrayList<OverlayItem>();

        for (spot:ParkingSpot in ParkingSpot.listSpots){
            val ovm:OverlayItem = OverlayItem(spot.designation, spot.city, GeoPoint(spot.latitude,spot.longitude))
            ovm.setMarker(icon)
            iconList.add(ovm)
        }
        val osmPinListener = object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem>{
            override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                Toast.makeText(this@MapActivity, "test test test", Toast.LENGTH_LONG)
                return true
            }

            override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                return true
            }
        }
        val markersOverlay:ItemizedOverlay<OverlayItem> = ItemizedIconOverlay<OverlayItem>(
                iconList,osmPinListener,this)
        this.mapview.overlays.add(markersOverlay)
        //mapView.overlays.add(markersOverlay)
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
                arrayOf((Manifest.permission.READ_EXTERNAL_STORAGE),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE),RECORD_REQUEST_CODE)
    }
}
