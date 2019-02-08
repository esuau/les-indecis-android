package com.les_indecis.ing3.esipe.les_indecis_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

import com.les_indecis.ing3.esipe.les_indecis_android.R.layout.activity_main
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    private var output: TextView? = null
    private var client: OkHttpClient? = null

    private inner class EchoWebSocketListener(val token: String?) : WebSocketListener() {
        private val NORMAL_CLOSURE_STATUS = 1000
        override fun onOpen(webSocket: WebSocket, response: Response?) {
            webSocket.send("token:" + token)
        }

        override fun onMessage(webSocket: WebSocket?, text: String?) {
            output("Receiving : " + text!!)
        }

        override fun onMessage(webSocket: WebSocket?, bytes: ByteString) {
            output("Receiving bytes : " + bytes.hex())
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String?) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null)
            output("Closing : $code / $reason")
        }

        override fun onFailure(webSocket: WebSocket?, t: Throwable, response: Response?) {
            output("Error : " + t.message)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        output = findViewById(R.id.output) as TextView
        client = OkHttpClient()

        var result:String=""
        var URL:String="http://api.undefined.inside.esiag.info/add_msg"
        var body = mapOf("queue" to "test", "msg" to "message from android")

        doAsync {
            var response = khttp.post(
                    url = URL,
                    data = body)
            uiThread {
                val textView: TextView = findViewById(R.id.content) as TextView
                textView.setText(response.text)
                start()

            }
        }



    }

    private fun start() {

            val request = Request.Builder().url("ws://api.undefined.inside.esiag.info:9091").build()
            val token = intent.getStringExtra("token")
            val listener = EchoWebSocketListener("token:" + token)
            val ws = client!!.newWebSocket(request, listener)
            ws.request()

//        client!!.dispatcher().executorService().shutdown()
    }

    private fun output(txt: String) {
        runOnUiThread { output!!.text = output!!.text.toString() + "\n\n" + txt }
    }


    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            return intent
        }
    }


}