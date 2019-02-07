package com.les_indecis.ing3.esipe.les_indecis_android

import okhttp3.*;
import okio.ByteString
import android.system.Os.shutdown
import okhttp3.WebSocket




class NotifWSListener : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        /*webSocket.send("message")
        webSocket.send("Hello, it's SSaurel !")
        webSocket.send("What's up ?")
        webSocket.send(ByteString.decodeHex("deadbeef"))
        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !")*/
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        webSocket.send("Receiving on android : "+ text)
    }

/*    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        output("Receiving bytes : " + bytes.hex())
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        ("Closing : $code / $reason")
    }

    private fun start() {
        val request = Request.Builder().url("ws://echo.websocket.org").build()
        val listener = NotifWSListener()
        val ws = client.newWebSocket(request, listener)
        client.dispatcher().executorService().shutdown()
    }
*/

    companion object {
        private val NORMAL_CLOSURE_STATUS = 1000
    }
}