package com.les_indecis.ing3.esipe.les_indecis_android

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.annotation.IdRes
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import org.osmdroid.api.IMapController
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.*
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

@SuppressLint("ByteOrderMark")
class MapActivity : AppCompatActivity() {

    private val RECORD_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        setupPermissions()
        if(isExternalStorageWritable()) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_map)
            val mapView = bind<MapView>(R.id.mapview);
            mapView.setBuiltInZoomControls(true);
            mapView.setMultiTouchControls(true);
            val imlp = GpsMyLocationProvider(this.baseContext);
            val mapController: IMapController = mapView.controller
            mapController.setZoom(12.1)
            val startPoint:GeoPoint = GeoPoint(48.8583,2.2944)
            mapController.setCenter(startPoint)
        }

    }

    fun setUpItems():Boolean {
        val items: ArrayList<OverlayItem> = ArrayList<OverlayItem>()
        items.add(OverlayItem("A", "B", GeoPoint(2.34200, 48.85300)))
        return true;
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
        return findViewById(res) as T
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
