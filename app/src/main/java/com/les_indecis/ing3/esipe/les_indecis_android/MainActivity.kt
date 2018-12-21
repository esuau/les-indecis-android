package com.les_indecis.ing3.esipe.les_indecis_android

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.les_indecis.ing3.esipe.les_indecis_android.R.id.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread
import java.net.URL
import khttp.*
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var result:String=""
        var URL:String="http://api.undefined.inside.esiag.info/add_msg"
        var body = mapOf("queue" to "test", "msg" to "message from android")
        //post(URL+"/add_msg", body)

        doAsync {
            var response = khttp.post(
            url = URL,
            data = body)
            uiThread {
                val textView: TextView = findViewById(R.id.content) as TextView
                textView.setText(response.text)
            }
        }

        val buttonParkingSpots: Button = findViewById(R.id.parking_spots_button) as Button
        buttonParkingSpots.setOnClickListener {
            val intent = Intent(this,MapActivity::class.java)
            startActivity(intent)
        }
        /*
        doAsync {
            result = URL(URL).readText()
            uiThread {
                Log.d("Request", result)
                longToast("Request performed")
                val textView: TextView = findViewById(R.id.content) as TextView
                textView.setText(result)
            }
        }
        */






    }

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            return intent
        }
    }



}

private operator fun Int.invoke(function: () -> Unit) {

}
